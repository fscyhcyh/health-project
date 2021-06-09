package com.cyh.service.impl;

import com.cyh.dao.MemberDao;
import com.cyh.dao.OrderDao;
import com.cyh.dao.ReportDao;
import com.cyh.service.ReportService;
import com.cyh.utils.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //报表日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获得本月第一天日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        //本日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        //总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);

        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);

        //热门套餐查询
        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);
        return result;
    }

//    @Override
//    public List<Integer> getMemberCountByMonth(List<String> months) {
//        List<Integer> memberCounts = new ArrayList<>();
//        if(months!=null && months.size()>0){
//            for(String month: months){
//                Map<String,String> map = new HashMap<>();
//                map.put("monthBegin",month+"-01");
//                map.put("monthEnd",month+"-31");
//                memberCounts.add(reportDao.getMemberCountByMonth(map));
//            }
//        }
//        return memberCounts;
//    }

//    @Override
//    public List<Map<String, Object>> getSetmealCount() {
//        return reportDao.getSetmealCount();
//    }
}
