package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

/**
 * @author Pzi
 * @create 2022-07-20 17:42
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference//去zookeeper中找该对象
    private CheckItemService checkItemService;

    //新增
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            //  Dubbo服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.findPage(
                queryPageBean.getCurrentPage()
                , queryPageBean.getPageSize()
                , queryPageBean.getQueryString());

    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @GetMapping("/delete")
    public Result deleteCheckItemById(@RequestParam("id") String id) {
        try {
            checkItemService.deleteCheckItemById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @GetMapping("/checkItemFormData")
    public Result selectCheckItemById(@RequestParam("id") String id) {
        return checkItemService.selectCheckItemById(id);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.updateCheckItem(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


}
