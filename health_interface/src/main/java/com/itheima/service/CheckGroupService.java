package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-29 10:27
 */
public interface CheckGroupService {
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    List<CheckItem> findCheckItemMsg();

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    CheckGroup findByGroupId(Integer id);

    List<Integer> findGroupIdAssItemId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

}
