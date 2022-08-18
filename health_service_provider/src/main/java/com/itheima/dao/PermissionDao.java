package com.itheima.dao;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-08 17:09
 */
public interface PermissionDao {

    Set<Permission> selectByRoleId(@Param("roleId") Integer roleId);

}
