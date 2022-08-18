package com.itheima.service;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-02 20:01
 */
public interface OrderSettingService {
    /**
     * 这个没用到
     * @param file
     * @throws IOException
     */
    void upload(MultipartFile file) throws IOException;

    /**
     * 将list中的预约数据添加到数据库中
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    List<Map> getOrderSettingByMonth(String date);

    /**
     * 修改某天的可预约人数number
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);

    OrderSetting selectByDate(String orderDate);

    void addReservationsByDate(String orderDate);

}
