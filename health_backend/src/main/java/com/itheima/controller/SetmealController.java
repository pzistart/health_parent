package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * @author Pzi
 * @create 2022-07-29 10:26
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/findAllCheckGroupMsg")
    public Result findAllCheckGroupMsg() {
        List<CheckGroup> list = setmealService.findAllCheckGroupMsg();
        if (list!=null && list.size() > 0){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }else {
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //图片上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功，也即是执行上述代码不抛异常
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
            result.setData(fileName);
            //  将上传成功的图片放到redis中对应的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES.getRedisConstant(),fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/add")
    public Result insert(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES.getRedisConstant(),setmeal.getImg());
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.edit(setmeal,checkgroupIds);
        }catch (Exception e){
            return new Result(false,"修改套餐失败");
        }
        return new Result(true,"修改套餐成功");
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(
                queryPageBean.getCurrentPage()
                , queryPageBean.getPageSize()
                , queryPageBean.getQueryString());
    }

    @GetMapping("/findSetmealBySealmealId")
    public Result findSetmealBySealmealId(@RequestParam("id") Integer id){
        Setmeal setmeal = setmealService.findSetmealBySealmealId(id);
        if (setmeal!=null){
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @GetMapping("/findSetmealAssCheckgroupBySealmealId")
    public Result findSetmealAssCheckgroupBySealmealId(@RequestParam("id") Integer id){
        List<Integer> list = setmealService.findSetmealAssCheckgroupBySealmealId(id);
        if (list!=null && list.size() > 0){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @GetMapping("/delete")
    public Result deleteBySetmealId(@RequestParam("id") Integer id){
        try {
            setmealService.deleteBySetmealId(id);
        }catch (Exception e){
            return new Result(false,"删除失败，该套餐已和检查组关联！");
        }
        return new Result(true,"删除套餐成功");
    }

}
