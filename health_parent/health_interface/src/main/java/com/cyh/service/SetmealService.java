package com.cyh.service;


import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.pojo.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface SetmealService {

    void save(Setmeal setmeal, Integer[] checkgroupIds);
    PageResult findPage(QueryPageBean queryPageBean);
    List<Setmeal> findAll();
    Setmeal findById(Integer id);
    List<Map<String,Object>> getSetmealCount();

}
