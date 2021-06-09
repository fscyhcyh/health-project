package com.cyh.dao;

import com.cyh.pojo.Menu;
import com.cyh.pojo.Role;

import java.util.LinkedHashSet;
import java.util.Set;

public interface MenuDao {
    LinkedHashSet<Menu> selectByRoleId(Integer roleId);
    Set<Menu> selectByParentId(Integer parentId);

}
