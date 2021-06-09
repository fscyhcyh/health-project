package com.cyh.service;

import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.pojo.CheckItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CheckItemService {

    boolean save(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(int id);

    CheckItem findById(int id);

    void edit(CheckItem checkItem);

    List<CheckItem>  findAll();
}