package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.entity.Result;
import com.cyh.service.MemberService;
import com.cyh.service.ReportService;
import com.cyh.service.SetmealService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    private Map businessReportData;

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;


    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
//        System.out.println("Reports...");
        List<String> months = new ArrayList<>();

        for(int i=24; i>=12; i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-i);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String dateString = simpleDateFormat.format(calendar.getTime());
            months.add(dateString);
        }
        System.out.println(months);
        List<Integer> memberCount = new ArrayList<>();

        Map<String,List> map = new HashMap<>();
        map.put("months",months);
        try{
            memberCount =  memberService.getMemberCountByMonth(months);
            map.put("memberCount",memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try{
            List<Map<String,Object>> setmealReportList = setmealService.getSetmealCount();

            System.out.println(setmealReportList);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,setmealReportList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }


    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try{
            Map<String,Object> data = reportService.getBusinessReportData();
            this.businessReportData = data;
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data);
        }catch (Exception e){
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            String reportDate = (String) businessReportData.get("reportDate");
            Integer todayNewMember = (Integer) businessReportData.get("todayNewMember");
            Integer totalMember = (Integer) businessReportData.get("totalMember");
            Integer thisWeekNewMember = (Integer) businessReportData.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) businessReportData.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) businessReportData.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) businessReportData.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) businessReportData.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) businessReportData.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) businessReportData.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) businessReportData.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) businessReportData.get("hotSetmeal");
            
            String path = request.getSession().getServletContext().getRealPath("template")+File.separator+"report_template.xlsx";
            System.out.println(path);
            XSSFWorkbook workbook = new XSSFWorkbook(new File(path));
            Sheet sheet = workbook.getSheetAt(0);


            Row row = sheet.getRow(2);

            row.getCell(5).setCellValue(reportDate);

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            OutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            String fileName = new String("test.xlsx".getBytes(),"iso8859-1");
            response.setHeader("content-Disposition", "attachment;filename="+fileName);
//            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response){
        try {
            String jrxmlPath = "C:\\Users\\rochen\\Downloads\\Files\\JAVA\\itheima\\Projects\\health\\health_parent\\health_admin\\src\\main\\webapp\\template\\health_business3.jrxml";
            String jasperPath = "C:\\Users\\rochen\\Downloads\\Files\\JAVA\\itheima\\Projects\\health\\health_parent\\health_admin\\src\\main\\webapp\\template\\health_business3.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath,
                    businessReportData,
                    new JRBeanCollectionDataSource((List)businessReportData.get("hotSetmeal")));

//            String path = request.getSession().getServletContext().getRealPath("template")+File.separator+"report_template.pdf";

            response.setContentType("application/pdf");
            String fileName = new String("report.pdf".getBytes(),"iso8859-1");
            response.setHeader("content-Disposition", "attachment;filename="+fileName);
//            response.setHeader("content-Disposition", "attachment;filename=report.pdf");

            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);

            outputStream.flush();
            outputStream.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
//            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
            return null;
        }
    }
}
