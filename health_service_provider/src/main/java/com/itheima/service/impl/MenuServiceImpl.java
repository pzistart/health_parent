package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Pzi
 * @create 2022-08-13 15:39
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getMenu(String username) throws Exception {
        User user = userService.selectByName(username);
        Set<Role> roles = user.getRoles();
        LinkedHashSet<Menu> set = new LinkedHashSet<>();

        //  遍历用户所属的角色，去获取每个角色对应的菜单。使用set保存菜单，是为了去重
        for (Role role : roles) {
            //该用户对应的菜单
            LinkedHashSet<Menu> menus = role.getMenus();
            for (Menu menu : menus) {
                //  只有当该菜单为父菜单时，才会加入到list中，返回给前端
                if (menu.getParentMenuId() != null) {
                    continue;
                }
                //  用来封装一个Menu对象
                Menu m = new Menu();
                BeanUtils.copyProperties(menu, m);

                List<Menu> menuList = menuDao.selectChildMenuByPId(menu.getId());
                if (menuList != null && menuList.size() > 0) {
                    m.setChildren(menuList);
                }
                set.add(m);
            }
        }
        if (!(set.size() > 0)) {
            throw new Exception("没有数据");
        }

        ArrayList<Menu> menus = new ArrayList<>();

        for (Menu menu : set){
            menus.add(menu);
        }

        return menus;
    }
}
