package com.cyh.service.impl;

import com.cyh.dao.MenuDao;
import com.cyh.pojo.Menu;
import com.cyh.service.MenuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public LinkedHashSet<Menu> findByRoleId(Integer roleId) {
        return menuDao.selectByRoleId(roleId);
    }


}
