package com.cyh.service;

import com.cyh.pojo.Permission;
import com.cyh.pojo.Role;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    Set<Permission> findByRoleId(Integer roleId);

}
