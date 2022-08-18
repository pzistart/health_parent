package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.FacesRequestAttributes;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-29 10:26
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.findPage(
                queryPageBean.getCurrentPage()
                , queryPageBean.getPageSize()
                , queryPageBean.getQueryString());
    }

    @GetMapping("/findCheckItemMsg")
    public Result findCheckItemMsg() {
        List<CheckItem> checkItems;
        try {
            checkItems = checkGroupService.findCheckItemMsg();
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true, "查询所有检查项成功！", checkItems);
    }

    @PostMapping("/add")
    public Result insert(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findCheckGroupByGroupId")
    public Result findCheckGroupByGroupId(@RequestParam("id") Integer id){
        CheckGroup checkGroup = checkGroupService.findByGroupId(id);
        if (checkGroup!=null){
            return new Result(true, "获取检查组信息成功",checkGroup);
        }
        return new Result(true, "获取检查组信息失败");
    }

    @GetMapping("/findGroupIdAssItemId")
    public Result findGroupIdAssItemId(@RequestParam("id") Integer id){
        List<Integer> list = checkGroupService.findGroupIdAssItemId(id);
        if (list!=null){
            return new Result(true, "获取已选检查项信息成功",list);
        }
        return new Result(false, "获取已选检查项信息失败");
    }


}
