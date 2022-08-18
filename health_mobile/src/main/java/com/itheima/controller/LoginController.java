package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.LoginService;
import com.itheima.service.MemberService;
import com.itheima.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-05 21:25
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Reference
    private LoginService loginService;

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {

        //  数据校验 放在controller层
        String telephone = (String) map.get("telephone");
        String codeFromRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        String codeFromInput = (String) map.get("validateCode");
        if (!StringUtils.isNotEmpty(codeFromRedis) || !codeFromRedis.equals(codeFromInput)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Member member = memberService.selectByTelephone(telephone);
        if (member == null) {
            Member m = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.insert(m);
        }

        //  将用户信息存入redis，时间半个小时
        String memberJson = JSON.toJSONString(member);
        jedisPool.getResource().setex(telephone, 60 * 30, memberJson);

        //写入Cookie，跟踪用户
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");//路径
        cookie.setMaxAge(60 * 60 * 24 * 30);//有效期30天
        response.addCookie(cookie);

        return new Result(true, MessageConstant.LOGIN_SUCCESS);

    }


}
