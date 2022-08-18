package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-05 21:25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @PostMapping("submitOrder")
    public Result submitOrder(@RequestBody Map map) {
        Integer orderId;
        try {
            orderId = orderService.submitOrder(map);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true, MessageConstant.ORDER_SUCCESS, orderId);
    }

    @GetMapping("findById")
    public Result findById(@RequestParam("id") Integer id) {
        Map order = orderService.selectById(id);
        if (order != null) {
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,order);
        }else {
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }


}
