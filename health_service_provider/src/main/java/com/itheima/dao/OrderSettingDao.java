package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.*;

/**
 * @author Pzi
 * @create 2022-07-29 10:28
 */
public interface OrderSettingDao {

    OrderSetting selectOrderSettingByDate(@Param("orderDate") Date orderDate);

    void updateOrderSettingByDate(OrderSetting orderSetting);

    void insert(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(@Param("map") Map dateMap);

    void editNumberByDate(OrderSetting orderSetting);

    OrderSetting selectByDate(@Param("orderDate") String orderDate);

    void addReservationsByDate(@Param("orderDate") String orderDate);
}
