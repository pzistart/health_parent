package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-29 10:27
 */
public interface ValidateCodeService {

    void sendVerCode(String telephone);

    void send4Login(String telephone);

}
