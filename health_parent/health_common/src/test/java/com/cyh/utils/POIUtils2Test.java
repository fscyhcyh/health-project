package com.cyh.utils;


import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class POIUtils2Test {


    @Test
    public void generateTemplateTest() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        //title row
        Row title = sheet.createRow(0);
        Cell titleCell_0_0 = title.createCell(0);
        titleCell_0_0.setCellValue("Date");
        Cell titleCell_0_1 = title.createCell(1);
        titleCell_0_1.setCellValue("Available");
        //
        CellStyle title_style = workbook.createCellStyle();
        title_style.setAlignment(HorizontalAlignment.CENTER);
        title_style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        title_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        title_style.setFont(titleFont);
        titleCell_0_0.setCellStyle(title_style);
        titleCell_0_1.setCellStyle(title_style);

        //
        CellStyle cs_field = workbook.createCellStyle();
//        cs_field.setAlignment(HorizontalAlignment.CENTER);
        cs_field.setBorderTop(BorderStyle.THIN);
        cs_field.setBorderBottom(BorderStyle.THIN);
        cs_field.setBorderLeft(BorderStyle.THIN);
        cs_field.setBorderRight(BorderStyle.THIN);

        for(int row_index=1; row_index <= 50; row_index++){
            Row row_i = sheet.createRow(row_index);
            for(int column_index = 0; column_index<2; column_index++){
                Cell cell_i_j = row_i.createCell(column_index);
                cell_i_j.setCellStyle(cs_field);
            }
        }

        File file = new File("test.xlsx");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();


    }
}
