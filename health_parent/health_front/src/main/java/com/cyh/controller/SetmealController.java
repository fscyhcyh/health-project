package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.entity.Result;
import com.cyh.pojo.Setmeal;
import com.cyh.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            System.out.println(id);
            Setmeal setmeal = setmealService.findById(id);
//            System.out.println();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
