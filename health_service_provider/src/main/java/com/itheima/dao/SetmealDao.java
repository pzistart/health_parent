package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-07-29 10:28
 */
public interface SetmealDao {

    List<CheckGroup> findAllCheckGroupMsg();

    void add(Setmeal setmeal);

    void assSetmealAndCheckGroup(@Param("id") Integer id, @Param("checkgroupId") Integer checkgroupId);

    Page<Setmeal> selectCondition(@Param("queryString") String queryString);

    Setmeal findSetmealBySealmealId(@Param("id") Integer id);

    List<Integer> findSetmealAssCheckgroupBySealmealId(@Param("id") Integer id);

    void edit(Setmeal setmeal);

    void deleteSetmealAssCheckgroupsBySetmealId(@Param("id") Integer id);

    void deleteBySetmealId(@Param("id") Integer id);

    List<Setmeal> findAllSetmeal();

    Setmeal findSetmealById(@Param("setmealId")Integer setmealId);

    List<Map<String, Integer>> selectNameAndCount();

    ArrayList<Map<String, Object>> selecthotSetmeal();

}
