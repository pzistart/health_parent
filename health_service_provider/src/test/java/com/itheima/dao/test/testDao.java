package com.itheima.dao.test;

import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dao.*;
import com.itheima.pojo.*;
import com.itheima.service.MemberService;
import com.itheima.service.impl.MemberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pzi
 * @create 2022-08-06 15:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring-dao.xml"})
public class testDao {
    @Value("${access.key}")
    private String key;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private CheckItemMbMapper checkItemMbMapper;

/*    @Autowired
    private CheckGroupDao checkGroupDao;

    @Autowired
    private SetmealDao setmealDao;*/

/*    @Test
    public void testCheckGroupDao() {
        List<CheckGroup> checkGroup = checkGroupDao.findCheckGroupWithItemBySetmealId(12);
        for (CheckGroup c : checkGroup){
            System.out.println(c);
        }
    }

    @Test
    public void testSetmealDao (){
        Setmeal setmeal = setmealDao.findSetmealById(12);
        System.out.println(setmeal);
    }*/

    @Test
    public void testValue (){
        System.out.println(key);
    }

    @Test
    public void testOrder (){
        String orderDate = "2019-04-28";
        Date date = DateUtil.parse(orderDate);
        Order orderCondition = new Order(84,date,null,null,12);
        Order order = orderDao.selectByCondition(orderCondition);
        System.out.println(order);
    }

    @Test
    public void testMemberAdd (){
        String name = "abaa";
        String sex = "1ba1";
        String telephone = "1231231123";
        String idCard = "1231333";
        Member member1 = new Member(null,null,name,sex,idCard,telephone,new Date(),null,null,null,null);
        memberDao.add(member1);
        System.out.println(member1.getId());
    }

    @Test
    public void testUserDao (){
        User user = userDao.selectByName("admin");
        System.out.println(user);
    }

    @Test
    public void testPerDao (){
        Set<Permission> permissions = permissionDao.selectByRoleId(1);
        System.out.println(permissions);
    }

    @Test
    public void testReportDao (){
        System.out.println(reportDao.selectCountBycalendar("2022-08"));
    }

    @Test
    public void testDate(){
        String reportDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Calendar calendar = Calendar.getInstance();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        calendar.add(Calendar.DATE,-6);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        System.out.println(calendar.get(Calendar.YEAR));
//        System.out.println(reportDate);
    }

    @Test
    public void testselect4DetailsById (){
        Map map = orderDao.select4DetailsById(17);
        System.out.println(map);
    }

    @Test
    public void testC (){
        List<CheckItem> checkItems = checkItemMbMapper.selectList(null);
        System.out.println(checkItems);
//        IPage<CheckItem> iPage = new Page<>(1,5);
//        Integer type = 1;
//        IPage<CheckItem> page = checkItemMbMapper.selectItemPage(iPage,type);
//        System.out.println(page.getTotal());
//        System.out.println(page.getRecords());
    }

}
