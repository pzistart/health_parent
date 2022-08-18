package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-08 17:09
 */
public interface MemberDao {

    Member selectByIdCard(@Param("idCard") String idCard);

    void add(Member member);

    Member selectByIdCardAndTelephone(@Param("idCard") String idCard, @Param("telephone") String telephone);

    Member selectByTelephone(@Param("telephone") String telephone);

    Member selectById(@Param("id") Integer memberId);

    /**
     * 查询今日新增会员数
     * @param reportDate
     * @return
     */
    Integer selectTodayAddByDate(@Param("reportDate") String reportDate);

    List<Member> selectAll();

    Integer select1WeekAddCount(@Param("today") String today, @Param("todayBefore7") String todayBefore7);

    Integer select1MonthAddCount(@Param("year") int year, @Param("month") int month);
}
