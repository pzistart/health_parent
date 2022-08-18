package com.itheima.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author Pzi
 * @create 2022-08-13 21:04
 */
public interface ReportDao {
    Integer selectCountBycalendar(@Param("calendar") String calendar);
}
