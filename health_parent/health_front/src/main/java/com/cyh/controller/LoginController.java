package com.cyh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyh.constant.MessageConstant;
import com.cyh.constant.RedisMessageConstant;
import com.cyh.entity.Result;
import com.cyh.pojo.Member;
import com.cyh.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result checkLogin(@RequestBody Map map, HttpServletResponse response){
        String telephone = (String) map.get("telephone");
        String name = (String) map.get("name");
        String validateCode = (String) map.get("validateCode");
        String codeFromRedis = jedisPool.getResource().get(telephone+"_"+ RedisMessageConstant.SENDTYPE_LOGIN);
        System.out.println("code: "+validateCode+" redis: "+codeFromRedis);
        if(codeFromRedis== null || validateCode == null ||  !validateCode.equals(codeFromRedis)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        try{
            Member  member = memberService.selectByTelephoneAndName(telephone,name);
            if(member==null){
                member = new Member();
                member.setPhoneNumber((String) map.get("telephone"));
                member.setName(name);
                memberService.add(member);
            }
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);

            String memberJson = JSON.toJSON(member).toString();
            jedisPool.getResource().set(telephone,memberJson);

            Result result =  new Result(true,MessageConstant.LOGIN_SUCCESS);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Some issue...Please try again");
        }
    }
}
