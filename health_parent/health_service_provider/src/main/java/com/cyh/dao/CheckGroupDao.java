package com.cyh.dao;

import com.cyh.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void save(CheckGroup checkGroup);
    void saveCheckGroupCheckItem(Map map);
    List<CheckGroup> selectByCondition(@Param("queryString") String queryString);
    CheckGroup selectById(Integer id);
    List<Integer> selectItemsByGroupId(Integer id);

    void update(CheckGroup checkGroup);
    void deleteCheckGroupCheckItemsByGroupId(Integer checkGroupId);
    List<CheckGroup> selectAll();

}
