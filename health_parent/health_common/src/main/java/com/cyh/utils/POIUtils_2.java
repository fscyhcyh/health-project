package com.cyh.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class POIUtils_2 {
    private static String xlx = "xlx";
    private static String xlsx = "xlsx";
    private static String DATE_FORMAT = "yyyy/MM/dd";

    public static List<String[]> readExcel(MultipartFile file) throws IOException {
        checkFile(file);
        Workbook workbook = getWorkBook(file);
        List<String[]> list = new ArrayList<>();

        if(workbook!=null){
            int numberOfSheets = workbook.getNumberOfSheets();
            for(int numberOfSheet = 0; numberOfSheet < numberOfSheets; numberOfSheet++){
                Sheet sheet = workbook.getSheetAt(numberOfSheet);
                System.out.println("sheet: " + numberOfSheet);
                if(sheet==null){
                    continue;
                }
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                System.out.println("how many rows of this file: "+ lastRowNum);

                for(int rowIndex = firstRowNum+1;rowIndex <=lastRowNum; rowIndex++){
                    Row row = sheet.getRow(rowIndex);
                    System.out.println("row: " + rowIndex);

                    if(row == null){
                        continue;
                    }
                    String[] values = new String[row.getPhysicalNumberOfCells()];
//                    System.out.println("how many cells of this row: "+ row.getPhysicalNumberOfCells());
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    System.out.println("first: "+ firstCellNum + "; last: "+ lastCellNum);
                    for(int cellIndex =firstCellNum; cellIndex<lastCellNum; cellIndex++){
                        Cell cell = row.getCell(cellIndex);
                        values[cellIndex] = getCellValue(cell);
                        System.out.println("cellIndex: " + cellIndex+", " + getCellValue(cell));

//                        System.out.println("cellIndex: " + cellIndex+", " + row.getCell(cellIndex));
                    }
                    System.out.println(values[0]+", "+values[1]);
                    list.add(values);
                }
            }
            workbook.close();

        }

        return list;
    }


    public static void checkFile(MultipartFile file) throws IOException {
        if(file==null){
            throw new IOException("No file found");
        }
        String filename = file.getOriginalFilename();
        if(!(filename.endsWith(xlx) || filename.endsWith(xlsx))){
            throw new IOException("Not an excel file");
        }
    }

    public static Workbook getWorkBook(MultipartFile file)  {
        String filename = file.getOriginalFilename();
        Workbook workbook = null;

        try{
            InputStream inputStream = file.getInputStream();
            if(filename.endsWith(xlx)){
                workbook = new HSSFWorkbook(inputStream);
            }else if( filename.endsWith(xlsx)){
                workbook = new XSSFWorkbook(inputStream);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return workbook;
    }

    public static String getCellValue(Cell cell){
        String value = "";
        if(cell==null){
            System.out.println("Empty");
            return value;
        }

        if(cell.getCellStyle().getDataFormatString().equals("m/d/yy")){
            Date dateCellValue = cell.getDateCellValue();
            value = new SimpleDateFormat("yyyy/MM/dd").format(dateCellValue);
            return value;
        }

//        if(DateUtil.isCellDateFormatted(cell)){
//            Date dateCellValue = cell.getDateCellValue();
//            value = new SimpleDateFormat(DATE_FORMAT).format(dateCellValue);
//            return value;
//        }

        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == CellType.NUMERIC){
//            System.out.println("before...: " + cell.getNumericCellValue());
            cell.setCellType(CellType.STRING);
//            System.out.println("after...: " + cell.getStringCellValue());
        }


//        CellType cellType = cell.getCellType();
//        cell.getDateCellValue()

        System.out.println(cell.getCellType().toString());
        switch (cell.getCellType()){
            case NUMERIC:
                value=String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                value=String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                value=String.valueOf(cell.getCellFormula());
                break;
            case STRING:
                value=String.valueOf(cell.getStringCellValue());
                break;
            case BLANK:
                System.out.println("Blank");
                value="";
                break;
            case ERROR:
                value="Wrong Input";
                break;
            default:
                value="Unknown";
                break;
        }
        System.out.println("getting: "+value);
        return value;
    }




    public static ByteArrayOutputStream generateTemplate() throws IOException {
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

//        File file = new File("test.xlsx");
//        OutputStream outputStream = new FileOutputStream(file);

        //output to controller
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        workbook.write(byteArrayOutputStream);
        workbook.close();
        return byteArrayOutputStream;
    }
}
