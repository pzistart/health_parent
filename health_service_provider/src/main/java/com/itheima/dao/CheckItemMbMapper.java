package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-08-17 15:53
 */
public interface CheckItemMbMapper extends BaseMapper<CheckItem> {

    IPage<CheckItem> selectItemPage(IPage<CheckItem> iPage,@Param("type") Integer type);

}
