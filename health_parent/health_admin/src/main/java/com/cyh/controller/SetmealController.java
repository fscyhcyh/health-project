package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.constant.RedisConstant;
import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.entity.Result;
import com.cyh.pojo.CheckGroup;
import com.cyh.pojo.Setmeal;
import com.cyh.service.SetmealService;
import com.cyh.utils.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        try {
            byte[] bytes = imgFile.getBytes();
            String uuid = UUID.randomUUID().toString();
            String originalFileName = imgFile.getOriginalFilename();
            String fileType = originalFileName.substring(originalFileName.lastIndexOf(".")-1);
            String newFileName = uuid+fileType;
            System.out.println(originalFileName+",  "+newFileName);
            QiniuUtils.upload2Qiniu(bytes,newFileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkGroupIds){
        try{
            setmealService.save(setmeal,checkGroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        return setmealService.findPage(queryPageBean);

    }



}
