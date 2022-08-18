package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.service.ValidateCodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-05 21:25
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Reference
    private ValidateCodeService validateCodeService;

    @GetMapping("/send4Order")
    public Result send4Order(@RequestParam("telephone") String telephone){
        try {
            validateCodeService.sendVerCode(telephone);
        }catch (Exception e){
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @PostMapping("/send4Login")
    public Result send4Login(@RequestParam("telephone") String telephone){
        try {
            validateCodeService.send4Login(telephone);
        }catch (Exception e){
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
