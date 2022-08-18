package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-01 16:35
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public List<CheckGroup> findAllCheckGroupMsg() {
        return setmealDao.findAllCheckGroupMsg();
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer id = setmeal.getId();
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds){
                setmealDao.assSetmealAndCheckGroup(id,checkgroupId);
            }
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.selectCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Setmeal findSetmealBySealmealId(Integer id) {
        return setmealDao.findSetmealBySealmealId(id);
    }

    @Override
    public List<Integer> findSetmealAssCheckgroupBySealmealId(Integer id) {
        return setmealDao.findSetmealAssCheckgroupBySealmealId(id);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.edit(setmeal);
        Integer id = setmeal.getId();
        setmealDao.deleteSetmealAssCheckgroupsBySetmealId(setmeal.getId());
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds){
                setmealDao.assSetmealAndCheckGroup(id,checkgroupId);
            }
        }
    }

    @Override
    public void deleteBySetmealId(Integer id) {
        setmealDao.deleteBySetmealId(id);
    }

    @Override
    public List<Setmeal> findAllSetmeal() {
        return setmealDao.findAllSetmeal();
    }

    @Override
    public Setmeal findSetmealById(Integer id) {
        return setmealDao.findSetmealById(id);
    }

    @Override
    public List<Map<String, Integer>> selectNameAndCount() {
        return setmealDao.selectNameAndCount();
    }


}
