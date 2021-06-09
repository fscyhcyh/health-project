package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.entity.Result;
import com.cyh.pojo.OrderSetting;
import com.cyh.service.OrderSettingService;
import com.cyh.utils.POIUtils;
import com.cyh.utils.POIUtils_2;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> listValues = POIUtils_2.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for(String[] values: listValues){
                System.out.println("date: " + values[0]);
                System.out.println(values[1]);
                Date orderDate = new Date(values[0]);
                int number = Integer.parseInt(values[1]);
                OrderSetting orderSetting = new OrderSetting(orderDate,number);
                orderSettingList.add(orderSetting);
            }
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/getordersettingbymonth")
    public Result getOrderSettingByMonth(String firstDayStr, String lastDayStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date firstDay = simpleDateFormat.parse(firstDayStr);
            Date lastDay = simpleDateFormat.parse(lastDayStr);
//            System.out.println(firstDay);
//            System.out.println(lastDay);
//            System.out.println(firstDayStr);
//            System.out.println(lastDayStr);
            List<Map> mapList = orderSettingService.getOrderSettingByMonth(firstDayStr,lastDayStr);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,mapList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editnumberbydate")
    public Result edit(@RequestBody OrderSetting orderSetting){
        System.out.println(orderSetting.getOrderDate());
        System.out.println(orderSetting.getNumber());
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }


    @RequestMapping("/download")
    public void download( HttpServletResponse response) throws IOException {
//        try {
        System.out.println("stream download ");
        response.setContentType("application/vnd.ms-excel");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String("test.xlsx".getBytes(),"iso8859-1");
        response.addHeader("Content-Disposition","attachment;fileName="+fileName);

            ByteArrayOutputStream byteArrayOutputStream = POIUtils_2.generateTemplate();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            byteArrayOutputStream.writeTo(servletOutputStream);
            servletOutputStream.flush();
            byteArrayOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
