package com.cyh.service.impl;

import com.cyh.dao.RoleDao;
import com.cyh.pojo.Role;
import com.cyh.service.RoleService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<Role> findByUserId(Integer userId) {
        return roleDao.selectByUserId(userId);
    }
}
