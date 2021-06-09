package com.cyh.service;

import com.cyh.entity.Result;
import com.cyh.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

    void add(Member member);
    Member selectByTelephoneAndName(String phoneNumber, String name);
    List<Integer> getMemberCountByMonth(List<String> months);

}
