package com.cyh.dao;

import com.cyh.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void save(OrderSetting orderSetting);
    void updateByOrderDate(OrderSetting orderSetting);
    int countOrderSettingByOrderDate(Date orderDate);
    List<OrderSetting> selectOrderSettingByMonth(@Param("firstDay") String firstDayStr,@Param("lastDay") String lastDayStr );
//    void editNumber(Map map);

    OrderSetting selectByOrderDate(Date orderDate);
    void editReservationByOrderDate(OrderSetting newOrderSetting);
}
