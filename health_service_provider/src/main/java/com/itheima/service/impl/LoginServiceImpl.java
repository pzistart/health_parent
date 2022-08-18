package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.LoginService;
import com.qiniu.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-10 14:53
 */
@Service(interfaceClass = LoginService.class)
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MemberDao memberDao;



}
