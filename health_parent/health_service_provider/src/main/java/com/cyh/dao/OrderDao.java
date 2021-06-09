package com.cyh.dao;

import com.cyh.pojo.Order;
import com.cyh.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> selectByCondition(Order order);
    void add(Order order);
    Map selectById4Detail(Integer id);

    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotSetmeal();
}
