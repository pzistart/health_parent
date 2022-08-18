package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Integer submitOrder(Map map) throws Exception;

    Map selectById(Integer id);

    /**
     * 根据setmealId查询order
     * @param id
     * @return
     */
    Integer selectCountBySetmealId(Integer id);

}
