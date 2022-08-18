package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-29 10:28
 */
public interface CheckGroupDao {
    Page<CheckGroup> findPage(@Param("queryString") String queryString);

    void add(CheckGroup checkGroup);

    void AssgroupWithItem(@Param("groupId") int groupId,@Param("itemId") int itemId);

    CheckGroup findByGroupId(@Param("id") Integer id);

    List<Integer> findGroupIdAssItemId(@Param("id") Integer id);

    void updateCheckGroup(CheckGroup checkGroup);

    void deleteAssWithGroupId(@Param("id") Integer id);

    //  一个checkGroup中有List<checkItem>，如何在查询一个checkGroup的时候，将它里面的所有checkItem查出来呢
    List<CheckGroup> findCheckGroupWithItemBySetmealId(@Param("setmealId") Integer setmealId);
}
