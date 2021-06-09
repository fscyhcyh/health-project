package com.cyh.service.impl;

import com.cyh.dao.MenuDao;
import com.cyh.dao.PermissionDao;
import com.cyh.dao.RoleDao;
import com.cyh.dao.UserDao;
import com.cyh.pojo.Menu;
import com.cyh.pojo.Permission;
import com.cyh.pojo.Role;
import com.cyh.pojo.User;
import com.cyh.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public User findByUsername(String username) {
        User user =  userDao.selectByUsername(username);

        if(user!=null){
            Set<Role> roleSet = roleDao.selectByUserId(user.getId());
            if(roleSet!=null && roleSet.size()>0){
                for(Role role:roleSet){
                    Set<Permission> permissionSet = permissionDao.selectByRoleId(role.getId());
                    LinkedHashSet<Menu> menuSet = menuDao.selectByRoleId(role.getId());
                    role.setPermissions(permissionSet);
                    role.setMenus(menuSet);
                }
            }
            user.setRoles(roleSet);
        }
        return user;
    }

    @Override
    public User findById(Integer id) {
        return userDao.selectById(id);
    }
}
