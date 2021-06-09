package com.cyh.service.impl;

import com.cyh.constant.MessageConstant;
import com.cyh.dao.MemberDao;
import com.cyh.dao.OrderDao;
import com.cyh.dao.OrderSettingDao;
import com.cyh.entity.Result;
import com.cyh.pojo.Member;
import com.cyh.pojo.Order;
import com.cyh.pojo.OrderSetting;
import com.cyh.service.OrderService;
import com.cyh.utils.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result submitOrder(Map map) {
        //if there is orderSetting in that date
        String orderDateString = (String) map.get("orderDate");
        Date orderDate = null;
        try{
            orderDate = DateUtils.parseString2Date(orderDateString);
        }catch (Exception e){
            e.printStackTrace();
        }
        OrderSetting orderSetting = orderSettingDao.selectByOrderDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //if that date is fully booked
        if (orderSetting!= null && orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //if the person with this telephone is a member
        Member conditionMember = new Member();
        conditionMember.setPhoneNumber((String) map.get("telephone"));
        conditionMember.setIdCard((String) map.get("idCard"));
        conditionMember.setName((String) map.get("name"));
        Integer memberId = memberDao.selectIdByCondition(conditionMember);

        if (memberId != null) {
            //if the order is duplicated
            Order conditionOrder = new Order(memberId, orderDate, Integer.parseInt((String) map.get("setmealId")));
            List<Order> orderList = orderDao.selectByCondition(conditionOrder);
            if (orderList != null && orderList.size()>0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            //add a new member
            memberDao.add(conditionMember);
            memberId = conditionMember.getId();
        }

        //Add the order
        Order newOrder = new Order();
        newOrder.setMemberId(memberId);
        newOrder.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        newOrder.setOrderDate(orderDate);
        newOrder.setOrderType(Order.ORDERTYPE_WEIXIN);
        newOrder.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(newOrder);

        //update ordersetting
        OrderSetting newOrderSetting = new OrderSetting(orderDate,orderSetting.getReservations()+1);
        orderSettingDao.editReservationByOrderDate(newOrderSetting);

        return new Result(true,MessageConstant.ORDER_SUCCESS, newOrder.getId());
    }

    @Override
    public Map findById4Detail(Integer id) {
        Map map = orderDao.selectById4Detail(id);
        System.out.println(map);
        return map;
    }
}
