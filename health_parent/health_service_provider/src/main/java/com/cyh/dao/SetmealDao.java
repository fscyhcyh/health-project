package com.cyh.dao;

import com.cyh.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    void save(Setmeal setmeal);
    void saveSetmealCheckGroup(Map map);
    List<Setmeal> selectByCondition(@Param("queryString") String queryString);
    List<Setmeal> selectAll();
    Setmeal selectById(Integer id);
    List<Map<String,Object>> getSetmealCount();

}
