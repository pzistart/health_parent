package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pzi
 * @create 2022-08-02 20:02
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void upload(MultipartFile file) throws IOException {
        List<String[]> list = POIUtils.readExcel(file);

        //  将list中的数据经过转换，insert到数据库
        ArrayList<OrderSetting> orderSettingsList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            OrderSetting orderSetting = new OrderSetting();
            for (String[] s : list) {
                orderSetting.setOrderDate(new Date(s[0]));
                orderSetting.setNumber(Integer.parseInt(s[1]));
                orderSettingsList.add(orderSetting);
            }
        }

        //
        for (OrderSetting orderSetting : orderSettingsList) {
            //  查看该日期是否已存在。存在则更新，否则就insert
            OrderSetting o = orderSettingDao.selectOrderSettingByDate(orderSetting.getOrderDate());
            if (o != null) {
                orderSettingDao.updateOrderSettingByDate(orderSetting);
            } else {
                orderSettingDao.insert(orderSetting);
            }
        }

    }

    @Override
//批量添加
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                OrderSetting o = orderSettingDao.selectOrderSettingByDate(orderSetting.getOrderDate());
                if (o != null) {
                    orderSettingDao.updateOrderSettingByDate(orderSetting);
                } else {
                    orderSettingDao.insert(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        // date 2022-9
//        String beginDate = date+"-1";
//        String endDate = date+"-31";
        String year = date.substring(0, date.indexOf("-"));
        String month = date.substring(date.indexOf("-") + 1);
        HashMap<String, String> dateMap = new HashMap<>();
        dateMap.put("year", year);
        dateMap.put("month", month);
//        dateMap.put("beginDate",beginDate);
//        dateMap.put("endDate",endDate);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(dateMap);

        ArrayList<Map> orderSettingsList = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            orderSettingsList.add(map);
        }
        return orderSettingsList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting o = orderSettingDao.selectOrderSettingByDate(orderSetting.getOrderDate());
        if (o != null) {
            orderSettingDao.editNumberByDate(orderSetting);
        } else {
            orderSettingDao.insert(orderSetting);
        }
    }

    @Override
    public OrderSetting selectByDate(String orderDate) {
        return orderSettingDao.selectByDate(orderDate);
    }

    @Override
    public void addReservationsByDate(String orderDate) {
        orderSettingDao.addReservationsByDate(orderDate);
    }

}
