package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-08 20:08
 */
public interface MemberService {
    Member selectByIdCard(String idCard);

    void insert(Member member);

    Member selectByIdCardAndTelephone(String idCard, String telephone);

    Member selectByTelephone(String telephone);

    Member selectById(Integer memberId);

}
