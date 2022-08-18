package com.ithema.testDemo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pzi
 * @create 2022-08-05 16:23
 */

public class TestD {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test() {
        String date = "2022-9";
        String year = date.substring(0, date.indexOf("-"));
        String month = date.substring(date.indexOf("-") + 1);
    }

    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();
        //  获取本月之前的12个月
        calendar.add(Calendar.MONDAY, -2);
        ArrayList<String> list = new ArrayList<>();
        //  调用servcie查询每个月的member数量
        System.out.println(calendar.getTime());
//        System.out.println(calendar.DATE);
        //  获取当前月份的最后一天的日期
        System.out.println(calendar.getActualMaximum(calendar.DATE));
       /* for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            System.out.println(calendar);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }*/

    }

    @Test
    public void test2() {
        Calendar calendar = Calendar.getInstance();
        //  获取本月之前的12个月
        calendar.add(Calendar.MONTH, -12);
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, List> map = new HashMap<>();
        //  months membercounts
        //  调用servcie查询每个月的member数量
        for (int i = 0; i < 12; i++) {
            System.out.println(calendar);
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()) + "." + calendar.getActualMaximum(calendar.DATE));
        }
        System.out.println(list);
    }

    @Test
    public void testPwd (){

//        HashSet<String> sets = new HashSet<>();
//        sets.add("1");
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);
        boolean matches = passwordEncoder.matches("123", encode);
        boolean matches2 = passwordEncoder.matches("1233", encode);
        System.out.println(matches+","+matches2);
    }

}
