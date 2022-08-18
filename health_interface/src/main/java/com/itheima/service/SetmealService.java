package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-01 16:35
 */
public interface SetmealService {
    List<CheckGroup> findAllCheckGroupMsg();

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Setmeal findSetmealBySealmealId(Integer id);

    List<Integer> findSetmealAssCheckgroupBySealmealId(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteBySetmealId(Integer id);

    List<Setmeal> findAllSetmeal();

    Setmeal findSetmealById(Integer id);

    /**
     * 查询订单order中每个套餐对应的数量
     * @return
     */
    //  返回类型为map，框架会自动把查询到的每组数据封装成key-val的形式。再放到list中
    List<Map<String, Integer>> selectNameAndCount();

}
