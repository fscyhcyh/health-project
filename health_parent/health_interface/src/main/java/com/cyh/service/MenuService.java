package com.cyh.service;

import com.cyh.pojo.Menu;

import java.util.LinkedHashSet;

public interface MenuService {

    LinkedHashSet<Menu> findByRoleId(Integer roleId);
}
