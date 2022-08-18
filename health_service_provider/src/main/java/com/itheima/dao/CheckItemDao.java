package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-20 21:09
 */
public interface CheckItemDao {
    public void add(CheckItem checkItem);

    Page<CheckItem> findPage(@Param("queryString") String queryString);

    void deleteCheckItemById(@Param("id") String id);

    Integer selectCheckGroupIdByCheckItemId(@Param("id") String id);

    CheckItem selectCheckItemById(String id);

    void updateCheckItem(CheckItem checkItem);

    List<CheckItem> findAll();


    /**
     *  根据检查组id，查询该组中所有的检查项
     * @param groupId
     * @return
     */
    List<CheckItem> findCheckItemListByGroupId(@Param("groupId") Integer groupId);

}
