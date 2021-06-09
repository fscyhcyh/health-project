package com.cyh.service;

import com.cyh.entity.Result;

import java.util.Map;

public interface OrderService {

    Result submitOrder(Map map) throws Exception;

    Map findById4Detail(Integer id);
}
