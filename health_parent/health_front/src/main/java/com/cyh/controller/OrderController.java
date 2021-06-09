package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.constant.RedisMessageConstant;
import com.cyh.entity.Result;
import com.cyh.pojo.Order;
import com.cyh.service.OrderService;
import com.cyh.utils.SMSUtils;
import com.cyh.utils.ValidateCodeUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody HashMap map) {
        System.out.println(map);
        String telephone = (String) map.get("telephone");

        String key = telephone + "_" + RedisMessageConstant.SENDTYPE_ORDER;
        String codeFromWeb = map.get("validateCode").toString();
        String codeFromRedis = null;
        try {
            codeFromRedis = jedisPool.getResource().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(key + " " + codeFromRedis + " " + codeFromWeb);

        if (codeFromRedis == null || !codeFromRedis.equals(codeFromWeb)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.submitOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        if (result.isFlag()) {
            try {
                String orderDate = (String) map.get("orderDate");
//                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById4Detail(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_SUCCESS);
        }
    }

}
