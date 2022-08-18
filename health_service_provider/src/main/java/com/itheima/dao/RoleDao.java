package com.itheima.dao;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-13 11:31
 */
public interface RoleDao {

    Set<Role> selectByUserName(@Param("userName") String userName);

}
