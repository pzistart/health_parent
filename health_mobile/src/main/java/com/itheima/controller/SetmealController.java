package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-08-05 21:25
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> list;
        try {
            list = setmealService.findAllSetmeal();
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
    }

    @GetMapping("/findSetmealById")
    public Result findSetmealById(@RequestParam("id") Integer id){
        Setmeal setmeal = setmealService.findSetmealById(id);
        if (setmeal!=null){
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }

}
