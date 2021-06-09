package com.cyh.dao;

import com.cyh.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> selectByUserId(Integer userId);

}
