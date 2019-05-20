package com.renhe.service;

import com.renhe.configuration.CustomerProperties;
import com.renhe.utils.ExcelUtil;
import com.renhe.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jinchaoyang on 2018/12/18.
 */
@Service
public class FileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    CustomerProperties properties;

    public File filtePhone(File sourceFile,String areaCode) throws IOException {
        File destDir = new File(properties.getDestPath());
        File targetFile = null;
        if(!destDir.exists()){
            destDir.mkdirs();
        }
        String fileName = sourceFile.getName().toLowerCase();
        if(fileName.endsWith("xls")){
            targetFile = readXls(sourceFile,areaCode);
        }else if(fileName.endsWith("xlsx")){
            targetFile = readXlsx(sourceFile,areaCode);
        }
        return targetFile;
    }


    public File readXls(File sourceFile, String areaCode) throws IOException {
        File result = null;
        List<String> phones = loadPrefix(areaCode);
        FileInputStream fis = new FileInputStream(sourceFile);
        Workbook wb = new HSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        String number = null;
        String prefix = null;
        Workbook outWb = null;
        List<String> data = new ArrayList<>();
        for(int i=0;i < rowNum; i++){
            Row row = sheet.getRow(i);
            if(null!=row) {
                Cell cell = row.getCell(0);
                if (null != cell) {
                    number = getCellValue(row.getCell(0));
                    prefix = number.substring(0,7);
                    if(phones.contains(prefix)){
                        data.clear();
                        data.add(number);
                       if(null==outWb){
                           outWb = ExcelUtil.createXLSX(data);
                       }else{
                           outWb = ExcelUtil.appendXLSX(outWb,data);
                       }
                    }
                }
            }
        }
        if(null!=outWb){
            String destName = sourceFile.getName();
            destName = destName.substring(0,destName.lastIndexOf("."))+".xlsx";
            String filePath = properties.getDestPath()+"/"+destName;
            result = new File(filePath);
            FileOutputStream fos = new FileOutputStream(result);
            outWb.write(fos);
            fos.close();
        }
        return result;

    }

    public File readXlsx(File sourceFile, String areaCode) throws IOException {

        File result = null;
        List<String> phones = loadPrefix(areaCode);
        FileInputStream fis = new FileInputStream(sourceFile);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        String number = null;
        String prefix = null;
        Workbook outWb = null;
        List<String> data = new ArrayList<>();
        for(int i=0;i < rowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            if(null!=row) {
                Cell cell = row.getCell(0);
                if (null != cell) {
                    number = getCellValue(row.getCell(0));
                    if(number.length()==11) {
                        prefix = number.substring(0, 7);
                    }
                    if(phones.contains(prefix)){
                        data.clear();
                        data.add(number);
                        if(null==outWb){
                            outWb = ExcelUtil.createXLSX(data);
                        }else{
                            outWb = ExcelUtil.appendXLSX(outWb,data);
                        }
                    }

                }
            }
        }
        String destName = sourceFile.getName();
        destName = destName.substring(0,destName.lastIndexOf("."))+".xlsx";
        String filePath = properties.getDestPath()+"/"+destName;
        result = new File(filePath);
        if(result.exists()){
            result.delete();
        }
        if(null!=outWb){
            FileOutputStream fos = new FileOutputStream(result);
            outWb.write(fos);
        }

        return result;
    }


    private static String getCellValue(Cell cell){
        String result = null;
        int cellType = cell.getCellType();
        if(cellType == Cell.CELL_TYPE_NUMERIC){
            result = String.format("%.0f",cell.getNumericCellValue());
        }else{
            result = cell.getStringCellValue();
        }
        return result;
    }


    public List<String> loadPrefix(String areaCode){
        List<String> result = new ArrayList<>();
        try {
            String basePath = properties.getPhonePath();
            File file = new File((basePath+"/"+areaCode+".txt"));
            if(file.exists()){
                FileReader reader = new FileReader(file);
                BufferedReader buf = new BufferedReader(reader);
                String line = buf.readLine();
                if(StringUtil.isPresent(line)) {
                    result = Arrays.asList(line.split(","));
                }
            }else{
                result = generateFile(areaCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public List<String> generateFile(String areaCode) throws IOException {
        List<String> lines = new ArrayList<>();
        FileReader reader = new FileReader(properties.getPhonePrefix());
        File destDir = new File(properties.getPhonePath());
        if(!destDir.exists()){
            destDir.mkdirs();
        }
        FileWriter writer = new FileWriter(properties.getPhonePath()+"/"+areaCode+".txt");
        BufferedReader buf = new BufferedReader(reader);
        String line = null;
        while((line=buf.readLine())!=null){
            line = line.replace("|",",");
            String arr[]  = line.split(",");
            if(areaCode.equals(arr[1])){
                lines.add(StringUtil.trim(arr[0]));
            }
        }
        if(!lines.isEmpty()){
            writer.write(String.join(",",lines));
            if(null!=writer) {
                writer.close();
            }
        }
        return lines;
    }



    public byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }



}
