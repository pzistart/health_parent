package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.service.ValidateCodeService;
import com.itheima.utils.MsgUtils;
import com.itheima.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

/**
 * @author Pzi
 * @create 2022-08-08 0:13
 */
@Service(interfaceClass = ValidateCodeService.class)
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private HashMap<String, String> map = new HashMap<String, String>();

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void sendVerCode(String telephone) {

        //发送短信验证码
//        Integer code = ValidateCodeUtils.generateValidateCode(6);
//        MsgUtils.sendCode(String.valueOf(code),telephone, key, sign, msgType);
//        log.warn("code是"+code);
        String code = "123132";
        //后端生成验证码，以手机号为key,验证码为value，存/替换入map。
        map.put(telephone, code);

        //将这条数据插入数据库
//        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        msgDao.insert(new MsgRecord(phone, code, spf.format(new Date())));

//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        //向redis中存入用户ip
//        String ipAddr = IPUtils.getIpAddr(request);
//        ops.set("userip", IPUtils.getIpAddr(request), 60, TimeUnit.SECONDS);

        //若该k-v已存在redis中，则删除。并且将新的code存入redis中
        if (StringUtils.isNotEmpty(jedisPool.getResource().get(telephone))) {
            jedisPool.getResource().del(telephone);
            jedisPool.getResource().set(telephone + RedisMessageConstant.SENDTYPE_ORDER, code);
        } else {
            //存入redis中
            jedisPool.getResource().set(telephone + RedisMessageConstant.SENDTYPE_ORDER, code);
        }
    }

    @Override
    public void send4Login(String telephone) {

        //发送短信验证码
//        Integer code = ValidateCodeUtils.generateValidateCode(6);
//        MsgUtils.sendCode(String.valueOf(code), telephone, MsgUtils.VALIDATE_CODE);
//        log.warn("code是"+code);
        String code = "2";
        //后端生成验证码，以手机号为key,验证码为value，存/替换入map。
        map.put(telephone, code);

        //若该k-v已存在redis中，则删除。并且将新的code存入redis中
        if (StringUtils.isNotEmpty(jedisPool.getResource().get(telephone))) {
            jedisPool.getResource().del(telephone);
            jedisPool.getResource().set(telephone + RedisMessageConstant.SENDTYPE_LOGIN, code);
        } else {
            //存入redis中
            jedisPool.getResource().set(telephone + RedisMessageConstant.SENDTYPE_LOGIN, code);
        }
    }
}


