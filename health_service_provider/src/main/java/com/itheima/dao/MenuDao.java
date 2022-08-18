package com.itheima.dao;

import com.itheima.pojo.Menu;
import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-08 17:09
 */
public interface MenuDao {
    Set<Menu> selectMenuByRoleId(@Param("roleId") Integer roleId);

    List<Menu> selectChildMenuByPId(@Param("id") Integer id);
}
