package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-13 14:21
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @RequestMapping("/getMenu")
    public Result getMenu(@RequestParam("username") String username) {
        List<Menu> menus;
        try {
            menus = menuService.getMenu(username);
        } catch (Exception e) {
            return new Result(true, e.getMessage());
        }
        return new Result(true, "查询菜单成功！", menus);
    }
}

