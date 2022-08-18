package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-29 10:28
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<CheckItem> findCheckItemMsg() {
        List<CheckItem> checkItems = checkItemDao.findAll();
        if (checkItems == null) {
            throw new RuntimeException("查询检查项无数据！请重试！");
        }
        return checkItems;
    }

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer id = checkGroup.getId();
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer itemID : checkitemIds) {
                checkGroupDao.AssgroupWithItem(id, itemID);
            }
        }
    }

    @Override
    public CheckGroup findByGroupId(Integer id) {
        return checkGroupDao.findByGroupId(id);
    }

    @Override
    public List<Integer> findGroupIdAssItemId(Integer id) {
        return checkGroupDao.findGroupIdAssItemId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.updateCheckGroup(checkGroup);
        Integer id = checkGroup.getId();
        //  先将原有的group 和 item 关联的所有项删除
        checkGroupDao.deleteAssWithGroupId(id);
        //  再将传进来的groupId 和 itemId相关联
        for (Integer itemID : checkitemIds) {
            checkGroupDao.AssgroupWithItem(id, itemID);
        }
    }

}
