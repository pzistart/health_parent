package com.itheima.service;

import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-13 15:39
 */
public interface MenuService {
    List<Menu> getMenu(String username) throws Exception;

}
