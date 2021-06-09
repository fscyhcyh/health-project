package com.cyh.service;

import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    void save(CheckGroup checkGroup, Integer[] checkItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findItemsByGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    List<CheckGroup> findAll();
}
