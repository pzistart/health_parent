package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pzi
 * @create 2022-07-20 17:45
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
//        checkItemDao.insert(checkItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.findPage(queryString);
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();
        //  返回总页数，分页数据list给前端
        return new PageResult(total, result);
    }

    @Override
    public void deleteCheckItemById(String id) {
        Integer integer = checkItemDao.selectCheckGroupIdByCheckItemId(id);
        if (integer > 0) {
            throw new RuntimeException("删除失败！该检查项和检查组已关联！");
        } else {
            checkItemDao.deleteCheckItemById(id);
        }
    }

    @Override
    public Result selectCheckItemById(String id) {
        CheckItem checkItem = checkItemDao.selectCheckItemById(id);
        if (checkItem != null) {
            return new Result(true, "success", checkItem);
        }else {
            return new Result(false,"查询失败，请刷新页面重试");
        }
    }

    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkItemDao.updateCheckItem(checkItem);
    }

}
