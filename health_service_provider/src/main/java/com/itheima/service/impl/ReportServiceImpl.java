package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import com.itheima.vo.SetmealVo;
import org.apache.xmlbeans.impl.jam.mutable.MMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pzi
 * @create 2022-08-13 20:33
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public List getMemberReportEvMonth(ArrayList<String> list) throws Exception {
        ArrayList<Integer> countList = new ArrayList<>();
        for (String calendar : list) {
            Integer count = reportDao.selectCountBycalendar(calendar);
            countList.add(count);
        }
        if (countList == null || !(countList.size() > 0)) {
            throw new Exception("查询数据失败");
        }
        return countList;
    }

    @Override
    public Map getSetmealReport() {
        //  setmealNames setmealCount
        HashMap<String, List> map = new HashMap<>();
        //  查询所有套餐名字
        List<Setmeal> setmealList = setmealService.findAllSetmeal();
        ArrayList<String> setmealNameList = new ArrayList<>();
        ArrayList<SetmealVo> setmealVos = new ArrayList<>();
        for (Setmeal s : setmealList) {
            setmealNameList.add(s.getName());
            Integer count = orderService.selectCountBySetmealId(s.getId());
            //  查询所有的套餐名字和它对应的预约数量
            SetmealVo setmealVo = new SetmealVo(count, s.getName());
            setmealVos.add(setmealVo);
        }
        map.put("setmealNames", setmealNameList);
        map.put("setmealCount", setmealVos);

        return map;
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        HashMap<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        map.put("reportDate", today);

        //  查询今日新增会员数
        Integer todayNewMember = memberDao.selectTodayAddByDate(today);
        map.put("todayNewMember", todayNewMember);

        //  查询总会员数
        List<Member> memberList = memberDao.selectAll();
        map.put("totalMember", memberList.size());

        //  查询本周新增会员数
        calendar.add(Calendar.DATE, -6);
        String todayBefore7 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        Integer weekAddCount = memberDao.select1WeekAddCount(today, todayBefore7);
        map.put("thisWeekNewMember", weekAddCount);

        //  查询本月新增会员数
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        Integer monthAddCount = memberDao.select1MonthAddCount(year, month);
        map.put("thisMonthNewMember", monthAddCount);

        //  查询今日预约数todayOrderNumber
        Integer todayOrderNumber = orderDao.selectTodayOrderCount(today);
        map.put("todayOrderNumber", todayOrderNumber);

        //  查询今日到诊数
        Integer todayVisitNumber = orderDao.selectTodayVisitCount(today);
        map.put("todayVisitsNumber", todayVisitNumber);

        //  查询本周到诊数
        Integer thisWeekVisitsNumber = orderDao.selectWeekVisitCount(todayBefore7,today);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);

        //  查询本周预约数
        Integer thisWeekOrderNumber = orderDao.select1WeekOrderCount(todayBefore7, today);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);

        //  查询本月预约数
        Integer thisMonthOrderNumber = orderDao.select1MonthOrderCount(year, month);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);

        //  查询本月到诊数
        Integer thisMonthVisitsNumber = orderDao.select1MonthVisttCount(year, month);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);

        //  查询热门套餐hotSetmeal
        // <td>{{s.name}}</td>
//         <td>{{s.setmeal_count}}</td>
//         <td>{{s.proportion}}</td>
        ArrayList<Map<String, Object>> hotList = setmealDao.selecthotSetmeal();
        map.put("hotSetmeal", hotList);

        return map;

    }

}
