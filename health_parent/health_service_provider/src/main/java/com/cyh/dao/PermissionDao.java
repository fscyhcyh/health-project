package com.cyh.dao;

import com.cyh.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> selectByRoleId(Integer roleId);

}
