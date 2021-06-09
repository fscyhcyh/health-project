package com.cyh.controller;

import com.aliyuncs.exceptions.ClientException;
import com.cyh.constant.MessageConstant;
import com.cyh.constant.RedisMessageConstant;
import com.cyh.entity.Result;
import com.cyh.service.MemberService;
import com.cyh.utils.SMSUtils;
import com.cyh.utils.ValidateCodeUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.json.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            System.out.println(telephone + " " + code);
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            jedisPool.getResource().setex(telephone + "_" + RedisMessageConstant.SENDTYPE_ORDER, 10 * 60, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);

        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            System.out.println(telephone + " " + code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telephone + "_" + RedisMessageConstant.SENDTYPE_LOGIN,
                60 * 30,
                code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }
}
