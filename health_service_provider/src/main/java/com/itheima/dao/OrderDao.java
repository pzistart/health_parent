package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-08 17:09
 */
public interface OrderDao {
    Order selectById(@Param("id") Integer id);

    void insert(@Param("map") Map map);

    Order selectByDateAndMemberId(@Param("memberId") Integer memberId, @Param("orderDate") String orderDate);

    Order selectByCondition(Order orderCondition);

    void add(Order o);


    /**
     * 根据订单id，查询成员、套餐、订单信息
     *
     * @param id
     * @return
     */
//    @MapKey("member")
    Map select4DetailsById(@Param("id") Integer id);


    Integer selectOrderBySetmealId(@Param("id") Integer id);

    Integer selectTodayOrderCount(@Param("today") String today);

    Integer selectTodayVisitCount(@Param("today") String today);


    Integer select1WeekOrderCount(@Param("todayBefore7") String todayBefore7, @Param("today") String today);

    Integer select1MonthOrderCount(@Param("year") int year, @Param("month") int month);

    Integer select1MonthVisttCount(@Param("year") int year, @Param("month") int month);

    Integer selectWeekVisitCount(@Param("todayBefore7") String todayBefore7, @Param("today") String today);
}
