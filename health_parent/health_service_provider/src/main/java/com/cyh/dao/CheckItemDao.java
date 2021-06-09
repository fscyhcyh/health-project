package com.cyh.dao;

import com.cyh.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemDao {

    boolean save(CheckItem checkItem);

//    Page<CheckItem> selectByCondition(String queryString);
    List<CheckItem> selectByCondition(@Param("queryString") String queryString);
    void deleteById(int id);
    long countByCheckItemId(int checkItemId);
    CheckItem selectById(int id);
    void update(CheckItem checkItem);
    List<CheckItem>  selectAll();

}
