package com.cyh.service.impl;

import com.cyh.dao.PermissionDao;
import com.cyh.pojo.Permission;
import com.cyh.service.PermissionService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Set<Permission> findByRoleId(Integer roleId) {
        return permissionDao.selectByRoleId(roleId);
    }
}
