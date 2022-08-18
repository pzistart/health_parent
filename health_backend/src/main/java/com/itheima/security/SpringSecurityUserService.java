package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-13 10:41
 */
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);
        User user = userService.selectByName(username);
        //  根据用户名（分步查询）找到该用户对应的所有角色， 在select每个角色的同时，分步查询 找到每个角色对应的所有权限信息

        if (user == null) {
            return null;
        }
        Set<Role> roles = user.getRoles();

        List<GrantedAuthority> list = new ArrayList<>();
        //  查询该用户所属的所有角色==>权限
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);

        return userDetails;
    }
}

