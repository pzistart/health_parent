package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

/**
 * @author Pzi
 * @create 2022-07-20 17:43
 */
public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void deleteCheckItemById(String id);

    Result selectCheckItemById(String id);

    void updateCheckItem(CheckItem checkItem);

}
