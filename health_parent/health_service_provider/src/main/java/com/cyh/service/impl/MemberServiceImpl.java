package com.cyh.service.impl;

import com.cyh.constant.MessageConstant;
import com.cyh.constant.RedisMessageConstant;
import com.cyh.dao.MemberDao;
import com.cyh.entity.Result;
import com.cyh.pojo.Member;
import com.cyh.service.MemberService;
import com.cyh.utils.MD5Utils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public void add(Member member) {
        if(member!=null && member.getPassword() != null ){
            String password = member.getPassword();
            member.setPassword( MD5Utils.md5(password));
        }
        memberDao.add(member);
    }



    @Override
    public Member selectByTelephoneAndName(String phoneNumber, String name) {
        return memberDao.selectByTelephoneAndName(phoneNumber,name);
    }


    @Override
    public List<Integer> getMemberCountByMonth(List<String> months) {
        List<Integer> memberCounts = new ArrayList<>();
        if(months!=null && months.size()>0){
            for(String month: months){
                Map<String,String> map = new HashMap<>();
                map.put("monthBegin",month+"-01");
                map.put("monthEnd",month+"-31");
                memberCounts.add(memberDao.getMemberCountByMonth(map));
            }
        }
        return memberCounts;
    }
}
