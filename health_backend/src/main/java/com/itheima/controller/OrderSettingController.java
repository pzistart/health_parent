package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderSettingService;
import com.itheima.service.SetmealService;
import com.itheima.utils.POIUtils;
import com.itheima.utils.QiniuUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;

/**
 * @author Pzi
 * @create 2022-07-29 10:26
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /*@PostMapping("/upload2")
    public Result upload2(@RequestParam("excelFile") MultipartFile file){
        try {
            orderSettingService.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
        return new Result(true,MessageConstant.UPLOAD_SUCCESS);
    }*/

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            if(list != null && list.size() > 0){
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting =
                            new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @GetMapping("getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("date") String date) {
        List<Map> list;
        try {
            list = orderSettingService.getOrderSettingByMonth(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询预约信息失败");
        }
        return new Result(true, "查询预约信息成功", list);
    }

    @PostMapping("editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改预约数量失败");
        }
        return new Result(true,"修改预约数量成功");
    }

}
