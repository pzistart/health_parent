package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @author Pzi
 * @create 2022-08-13 10:42
 */
public interface UserService {
    /**
     * 根据用户名，找到该用户所属的所有用户、所有权限
     * @param username
     * @return
     */
    User selectByName(String username);

}
