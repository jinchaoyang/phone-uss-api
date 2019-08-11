package com.renhe.service;

import com.renhe.configuration.CustomerProperties;
import com.renhe.utils.ExcelUtil;
import com.renhe.utils.StringUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jinchaoyang on 2018/12/18.
 */
@Service
public class FileUploadService {


    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    CustomerProperties properties;

    public File filtePhone(File sourceFile,String areaCode) throws Exception {
        File destDir = new File(properties.getDestPath());
        File targetFile = null;
        if(!destDir.exists()){
            destDir.mkdirs();
        }
        String fileName = sourceFile.getName().toLowerCase();
        if(fileName.endsWith("xls")){
            targetFile = readXlsx(sourceFile,areaCode);
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
                    data.clear();
                    if(phones.contains(prefix)){
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

    public File readXlsx(File sourceFile, String areaCode) throws Exception {

        String destName = sourceFile.getName();
        destName = destName.substring(0,destName.lastIndexOf("."))+".csv";
        String filePath = properties.getDestPath()+"/"+destName;
        File  outFile = new File(filePath);
        if(outFile.exists()){
            outFile.delete();
       }
        List<String> phones = loadPrefix(areaCode);
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator("\n");
        FileWriter fileWriter = new FileWriter(outFile);
        CSVPrinter printer = new CSVPrinter(fileWriter,formator);

        OPCPackage pkg = OPCPackage.open(sourceFile);
        XSSFReader reader = new XSSFReader(pkg);
        SharedStringsTable sst = reader.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst,phones,printer);
        InputStream sheet = reader.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        sheet.close();
        printer.flush();

//        FileInputStream fis = new FileInputStream(sourceFile);
//        XSSFWorkbook wb = new XSSFWorkbook(fis);
//        XSSFSheet sheet = wb.getSheetAt(0);
//        int rowNum = sheet.getLastRowNum();
//        String number = null;
//        String prefix = null;
//        Workbook outWb = null;
//        List<String> data = new ArrayList<>();
//        for(int i=0;i < rowNum; i++) {
//            XSSFRow row = sheet.getRow(i);
//            if(null!=row) {
//                Cell cell = row.getCell(0);
//                if (null != cell) {
//                    number = getCellValue(row.getCell(0));
//                    if(number.length()==11) {
//                        prefix = number.substring(0, 7);
//                    }
//                    if(phones.contains(prefix)){
//                        data.clear();
//                        data.add(number);
//                        if(null==outWb){
//                            outWb = ExcelUtil.createXLSX(data);
//                        }else{
//                            outWb = ExcelUtil.appendXLSX(outWb,data);
//                        }
//                    }
//
//                }
//            }
//        }
//        String destName = sourceFile.getName();
//        destName = destName.substring(0,destName.lastIndexOf("."))+".xlsx";
//        String filePath = properties.getDestPath()+"/"+destName;
//        result = new File(filePath);
//        if(result.exists()){
//            result.delete();
//        }
//        if(null!=outWb){
//            FileOutputStream fos = new FileOutputStream(result);
//            outWb.write(fos);
//        }

        return outFile;
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

                String[] areas = areaCode.split(",");
                for(String area : areas){
                    if(StringUtil.isPresent(area)) {
                        String basePath = properties.getPhonePath();
                        File file = new File((basePath + "/" + area + ".txt"));
                        if (file.exists()) {
                            FileReader reader = new FileReader(file);
                            BufferedReader buf = new BufferedReader(reader);
                            String line = buf.readLine();
                            if (StringUtil.isPresent(line)) {
                                result.addAll(Arrays.asList(line.split(",")));
                            }
                        } else {
                            result.addAll(generateFile(area));
                        }
                    }
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


    public static XMLReader fetchSheetParser(SharedStringsTable sst,List<String> phones,CSVPrinter csvPrinter) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst,phones,csvPrinter);
        parser.setContentHandler(handler);
        return parser;
    }


    private static class SheetHandler extends DefaultHandler {

        private SharedStringsTable sst;
        //上一次的内容
        private String lastContents;
        private boolean nextIsString;
        private int sheetIndex = -1;
        private List<String> rowlist = new ArrayList<String>();
        //当前行
        private int curRow = 0;      //当前列
        private int curCol = 0;
        //日期标志
        private boolean dateFlag;
        private boolean numberFlag;
        //前一个单元格的xy
        private String preXy = "";
        // 当前单元格的xy
        private String currXy = "";
        //前一个单元格的x
        private String preX = "";
        //当前单元格的x
        private String currX = "";
        //是否跳过了单元格
        private boolean isSkipCeil = false;
        private boolean isTElement;
        //两个不为空的单元格之间隔了多少个空的单元格
        private int flag = 0;

        private List<String> phones;

        private CSVPrinter csvPrinter;


        private SheetHandler(SharedStringsTable sst,List<String> phones,CSVPrinter csvPrinter) {
            this.sst = sst;
            this.phones = phones;
            this.csvPrinter = csvPrinter;

        }


        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
            // c => 单元格
            if ("c".equals(name)) {
                // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
                String cellType = attributes.getValue("t");
                if ("s".equals(cellType)) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }            //日期格式
                String cellDateType = attributes.getValue("s");
                if ("1".equals(cellDateType)) {
                    dateFlag = true;
                } else {
                    dateFlag = false;
                }
                String cellNumberType = attributes.getValue("s");
                if ("2".equals(cellNumberType)) {
                    numberFlag = true;
                } else {
                    numberFlag = false;
                }            //与判断空单元格有关
                isSkipCeil = false;
                String cellXy = attributes.getValue("r");
                if ("".equals(preXy)) {
                    preXy = cellXy;
                }
                currXy = cellXy;
                preX = preXy.replaceAll("\\d", "").trim();
                currX = currXy.replaceAll("\\d", "").trim();
                char pre;
                char curr;
                if (preX.length() == 2) {
                    pre = preX.charAt(1);
                } else {
                    pre = preX.charAt(0);
                }
                if (currX.length() == 2) {
                    curr = currX.charAt(1);
                } else {
                    curr = currX.charAt(0);
                }
                flag = curr - pre;
                if (flag != 0 && flag != 1 && flag > 0) {
                    isSkipCeil = true;
                }
                preXy = cellXy;
            }
            //当元素为t时
            if ("t".equals(name)) {
                isTElement = true;
            } else {
                isTElement = false;
            }                    // 置空
            lastContents = "";
        }        //读取单元格的内容

        public void endElement(String uri, String localName, String name) throws SAXException {
            // 根据SST的索引值的到单元格的真正要存储的字符串
            // 这时characters()方法可能会被调用多次
            if (nextIsString) {
                try {
                    int idx = Integer.parseInt(lastContents);
                    lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                } catch (Exception e) {
                }
            }
            //t元素也包含字符串
            if (isTElement) {
                String value = lastContents.trim();
                rowlist.add(curCol, value);
                curCol++;
                isTElement = false;
                // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
                // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            } else if ("v".equals(name)) {
                String value = lastContents.trim();
                value = value.equals("") ? " " : value;

                //数字类型处理
                if (numberFlag) {

                    //BigDecimal bd = new BigDecimal(value);
                    //value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
                }
                //当某个单元格的数据为空时，其后边连续的单元格也可能为空
                if (isSkipCeil == true) {
                    for (int i = 0; i < (flag - 1); i++) {
                        rowlist.add(curCol + i, "");
                    }
                    curCol += (flag - 1);
                }
                rowlist.add(curCol, value);
                curCol++;
            } else {              //如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
                if (name.equals("row")) {
                    try {
                        String value = rowlist.get(0);
                        String prefix= null;
                        if(value.length()==11) {
                            prefix = value.substring(0, 7);
                        }
                        if(phones.contains(prefix)){

                        csvPrinter.printRecord(rowlist);
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    rowlist.clear();
                    curRow++;
                    curCol = 0;
                }
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException {          //得到单元格内容的值
            lastContents += new String(ch, start, length);
        }


    }



}
