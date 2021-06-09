package com.cyh.service.impl;

import com.cyh.dao.OrderSettingDao;
import com.cyh.pojo.OrderSetting;
import com.cyh.service.OrderSettingService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if(orderSettingList!=null && orderSettingList.size()>0){
            for(OrderSetting orderSetting:orderSettingList){
                int countByDate = orderSettingDao.countOrderSettingByOrderDate(orderSetting.getOrderDate());
                if(countByDate>0){
                    orderSettingDao.updateByOrderDate(orderSetting);
                }else{
                    orderSettingDao.save(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String firstDayStr, String lastDayStr) {
        List<OrderSetting> orderSettingList = orderSettingDao.selectOrderSettingByMonth(firstDayStr, lastDayStr);
        List<Map> mapList = new ArrayList<>();
        for(OrderSetting orderSetting:orderSettingList){
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            mapList.add(map);
        }
        System.out.println(orderSettingList);
        return mapList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count = orderSettingDao.countOrderSettingByOrderDate(orderSetting.getOrderDate());
        if(count>0){
            orderSettingDao.updateByOrderDate(orderSetting);
        }else{
            orderSettingDao.save(orderSetting);
        }
    }
}
