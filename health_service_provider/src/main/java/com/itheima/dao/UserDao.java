package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author Pzi
 * @create 2022-08-13 10:46
 */
public interface UserDao {
    User selectByName(@Param("username") String username);
}
