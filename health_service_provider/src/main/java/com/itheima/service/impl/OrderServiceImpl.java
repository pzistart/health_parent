package com.itheima.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderDao;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.service.MemberService;
import com.itheima.service.OrderService;
import com.itheima.service.OrderSettingService;
import com.itheima.service.SetmealService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-08 17:00
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingService orderSettingService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public Integer submitOrder(Map map) throws Exception {
        //  校验验证码是否正确
        String codeFromRedis = jedisPool.getResource().get((String) map.get("telephone"));
        String codeFromInput = (String) map.get("validateCode");
        if (codeFromRedis == null || !codeFromRedis.equals(codeFromInput)) {
            throw new Exception("验证码已失效或错误");
        }

        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        map.put("orderStatus", Order.ORDERSTATUS_NO);

        //  检查该日期（已设置预约人数/可预约人数满）是否可以预约，不可预约则抛异常
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingService.selectByDate(orderDate);
        if (orderSetting == null) {
            throw new Exception("所选日期不能预约！");
        }
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            throw new Exception("所选日期预约人数已满！");
        }

        //  检查该用户是否是会员
        String idCard = (String) map.get("idCard");
        //  根据手机号能查到member，那么就说明该会员存在
        Member member = memberService.selectByTelephone((String) map.get("telephone"));
        //  不是会员，就将它创建为会员
        if (member == null) {
            String name = (String) map.get("name");
            String sex = (String) map.get("sex");
            String telephone = (String) map.get("telephone");
            Member member1 = new Member(null, null, name, sex, idCard, telephone, new Date(), null, null, null, null);
            memberService.insert(member1);
            member = memberService.selectByIdCard(idCard);
        }
        map.put("memberId", member.getId());


        //  检查该用户在当天是否已预约，不可以重复预约
        Integer memberId = member.getId();
        Date date = DateUtil.parse(orderDate);
        String setmealId = (String) map.get("setmealId");
        Order orderCondition = new Order(memberId, date, null, null, Integer.parseInt(setmealId));
        Order order = orderDao.selectByCondition(orderCondition);
//        Order order = orderDao.selectByDateAndMemberId(memberId, orderDate);
        if (order != null) {
            throw new Exception("不能重复预约！");
        }

        //  如果可以预约，修改该日期的已预约人数+1
        orderSettingService.addReservationsByDate(orderDate);

        orderDao.insert(map);

        Order o = new Order(memberId, date, (String) map.get("orderType"), (String) map.get("orderStatus"), Integer.parseInt(setmealId));
        orderDao.add(o);
        Integer orderId = (Integer) map.get("id");
        return o.getId();

    }

    @Override
    public Map selectById(Integer id) {

        return orderDao.select4DetailsById(id);
/*
        Order order = orderDao.selectById(id);
        Integer memberId = order.getMemberId();
        Member member = memberService.selectById(memberId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("member", member.getName());
        Integer setmealId = order.getSetmealId();
        Setmeal setmeal = setmealService.findSetmealById(setmealId);
        map.put("setmeal", setmeal.getName());
        map.put("orderDate", order.getOrderDate());
        map.put("orderType", order.getOrderType());
        return map;*/
    }

    @Override
    public Integer selectCountBySetmealId(Integer id) {
        return orderDao.selectOrderBySetmealId(id);
    }

}
