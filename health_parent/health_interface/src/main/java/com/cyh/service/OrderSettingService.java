package com.cyh.service;

import com.cyh.pojo.OrderSetting;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);
    List<Map> getOrderSettingByMonth (String firstDayStr, String lastDayStr);
    void editNumberByDate(OrderSetting orderSetting);
}
