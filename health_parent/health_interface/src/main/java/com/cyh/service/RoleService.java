package com.cyh.service;

import com.cyh.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> findByUserId(Integer userId);
}
