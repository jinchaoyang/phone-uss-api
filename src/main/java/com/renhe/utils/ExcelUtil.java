package com.renhe.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * Created by jinchaoyang on 2018/12/18.
 */
public class ExcelUtil {


    /**
     * 创建XLXS文件
     * @param dataList
     * @return
     */
    public static Workbook createXLSX(List<String> dataList){
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("号码");
        Row row=null;
        for(int i=0;i<dataList.size();i++){
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(dataList.get(i));
        }
        return wb;
    }


    public static Workbook appendXLSX(Workbook wb,List<String> dataList){
        Sheet sheet  = wb.getSheetAt(0);
        int rowsLen = sheet.getLastRowNum()+1;
        Row row = null;
        for(int i=0;i<dataList.size();i++){
            row = sheet.createRow(rowsLen);
            row.createCell(0).setCellValue(dataList.get(i));
            rowsLen = rowsLen+1;
        }
        return wb;
    }
}
