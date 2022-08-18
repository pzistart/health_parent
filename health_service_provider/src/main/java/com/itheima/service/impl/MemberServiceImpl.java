package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-08 20:09
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member selectByIdCard(String idCard) {
        return memberDao.selectByIdCard(idCard);
    }

    @Override
    public void insert(Member member) {
        memberDao.add(member);
    }

    @Override
    public Member selectByIdCardAndTelephone(String idCard, String telephone) {
        return memberDao.selectByIdCardAndTelephone(idCard,telephone);
    }

    @Override
    public Member selectByTelephone(String telephone) {
        return memberDao.selectByTelephone(telephone);
    }

    @Override
    public Member selectById(Integer memberId) {
        return memberDao.selectById(memberId);
    }
}
