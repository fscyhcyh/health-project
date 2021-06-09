package com.cyh.dao;

import com.cyh.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberDao {

    Integer selectIdByCondition(Member member);
    void add(Member member);
    Member selectByTelephoneAndName(@Param("phoneNumber") String phoneNumber, @Param("name") String name);
    Integer getMemberCountByMonth(Map month);

    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();
}
