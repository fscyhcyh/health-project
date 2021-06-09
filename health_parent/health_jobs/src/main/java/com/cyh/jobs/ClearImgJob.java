package com.cyh.jobs;

import com.cyh.constant.RedisConstant;
import com.cyh.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        Jedis jedis = jedisPool.getResource();
        Set<String> interSet = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(interSet!=null && interSet.size()>0){
            for(String file : interSet){
                QiniuUtils.deleteFileFromQiniu(file);
                jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES,file);
                System.out.println("Cleaning..");
            }
        }

    }
}
