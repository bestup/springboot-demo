package com.export.excel.hssf.controller;

import com.alibaba.fastjson.JSON;
import com.export.excel.hssf.model.User;
import com.export.excel.hssf.service.HssfExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/hssf/excel")
public class HssfExcelController {

    @Autowired
    private HssfExcelService hssfExcelService;

    /**
     * hssf导出单个sheet
     * 参考： https://www.cnblogs.com/minixiong/p/11149281.html
     * @return
     */
    @GetMapping("/exportOneSheet")
    public ResponseEntity exportOneSheet() throws IOException {
        List<User> userList = hssfExcelService.findAll();

        //创建excel文档对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成单元格字体样式
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        // 字体格式
        HSSFFont font = workbook.createFont();
        // 字体大小
        font.setFontHeightInPoints((short) 50);
        headerCellStyle.setFont(font);
        headerCellStyle.setFillForegroundColor((short) 13);// 设置背景色

        //excel表单
        HSSFSheet sheet = workbook.createSheet("导出用户报表");
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 6000);

        //excel的行
        HSSFRow headerRow = sheet.createRow(0);
        String[] header = { "序号", "编号","姓名", "手机号", "地址", "年龄", "生日", "身高", "余额", "是否是会员"};
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }

        int rowNumber = 0;
        DecimalFormat df2 = new java.text.DecimalFormat("#0.00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < userList.size(); i++) {
            rowNumber++;
            User user = userList.get(i);
            //设置行数据
            HSSFRow row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(user.getId());
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getPhone());
            row.createCell(4).setCellValue(user.getAddress());
            row.createCell(5).setCellValue(user.getAge());
            row.createCell(6).setCellValue(simpleDateFormat.format(user.getBirthday()));
            row.createCell(7).setCellValue(user.getHeight());
            row.createCell(8).setCellValue(df2.format(user.getAmount()));
            if(1 == user.getIsAdmin()) {
                row.createCell(9).setCellValue("是");
            }else {
                row.createCell(9).setCellValue("否");
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", new String("销售运单导出表.xls".getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);

        return new ResponseEntity<byte[]>(output.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * hssf导出多个sheet
     * 参考： https://www.cnblogs.com/minixiong/p/11149281.html
     * @return
     */
    @GetMapping("/exportTwoSheet")
    public ResponseEntity exportTwoSheet() throws IOException {
        List<User> userList = hssfExcelService.findAll();

        //创建excel文档对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成单元格字体样式
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        // 字体格式
        HSSFFont font = workbook.createFont();
        // 字体大小
        font.setFontHeightInPoints((short) 50);
        headerCellStyle.setFont(font);
        headerCellStyle.setFillForegroundColor((short) 13);// 设置背景色

        //excel表单  用户报表sheet
        HSSFSheet sheet = workbook.createSheet("报表");
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 6000);

        //excel的行
        HSSFRow headerRow = sheet.createRow(0);
        String[] header = { "序号", "编号","姓名", "手机号", "地址", "年龄", "生日", "身高", "余额", "是否是会员"};
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }

        int rowNumber = 0;
        DecimalFormat df2 = new java.text.DecimalFormat("#0.00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < userList.size(); i++) {
            rowNumber++;
            User user = userList.get(i);
            //设置行数据
            HSSFRow row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(user.getId());
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getPhone());
            row.createCell(4).setCellValue(user.getAddress());
            row.createCell(5).setCellValue(user.getAge());
            row.createCell(6).setCellValue(simpleDateFormat.format(user.getBirthday()));
            row.createCell(7).setCellValue(user.getHeight());
            row.createCell(8).setCellValue(df2.format(user.getAmount()));
            if(1 == user.getIsAdmin()) {
                row.createCell(9).setCellValue("是");
            }else {
                row.createCell(9).setCellValue("否");
            }
        }

        // 用户信息sheet
        HSSFSheet sheet1 = workbook.createSheet("报表1");
        sheet1.setColumnWidth(0, 2000);
        sheet1.setColumnWidth(1, 4000);
        sheet1.setColumnWidth(2, 3000);
        sheet1.setColumnWidth(3, 4000);
        sheet1.setColumnWidth(4, 3000);
        sheet1.setColumnWidth(5, 3000);
        sheet1.setColumnWidth(6, 6000);
        sheet1.setColumnWidth(7, 3000);
        sheet1.setColumnWidth(8, 3000);
        sheet1.setColumnWidth(9, 6000);

        //excel的行
        HSSFRow headerRow1 = sheet1.createRow(0);
        String[] header1 = { "序号", "编号","姓名", "手机号", "地址", "年龄", "生日", "身高", "余额", "是否是会员"};
        for (int i = 0; i < header1.length; i++) {
            HSSFCell cell = headerRow1.createCell(i);
            cell.setCellValue(header[i]);
        }

        int rowNumber1 = 0;

        for (int i = 0; i < userList.size(); i++) {
            rowNumber1++;
            User user = userList.get(i);
            //设置行数据
            HSSFRow row = sheet1.createRow(rowNumber1);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(user.getId());
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getPhone());
            row.createCell(4).setCellValue(user.getAddress());
            row.createCell(5).setCellValue(user.getAge());
            row.createCell(6).setCellValue(simpleDateFormat.format(user.getBirthday()));
            row.createCell(7).setCellValue(user.getHeight());
            row.createCell(8).setCellValue(df2.format(user.getAmount()));
            if(1 == user.getIsAdmin()) {
                row.createCell(9).setCellValue("是");
            }else {
                row.createCell(9).setCellValue("否");
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", new String("导出用户报表.xls".getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);

        return new ResponseEntity<byte[]>(output.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 导入excel， 为了方便测试，直接将上面导出的excel再进行导入
     * @param file
     * @return
     */
    @PostMapping("/importOneSheet")
    public String importOneSheet(MultipartFile file) throws IOException, ParseException {
        Workbook wb = null;
        Sheet sheet = null;

        String fileName = file.getOriginalFilename();
        if (fileName.endsWith(".xlsx")) {
            // Excel 2007
            wb = new XSSFWorkbook(file.getInputStream());
        } else {
            // Excel 2003
            wb = new HSSFWorkbook(file.getInputStream());
        }

        //判断有几个sheet
        int sheetNumber = wb.getNumberOfSheets();
        if (sheetNumber > 1) {
            return "sheet_number_error";
        }

        //获取sheet判断列数
        sheet = (Sheet) wb.getSheetAt(0);
        int columnNumber = 0;
        // 校验表格的列数是否正确
        if (sheet != null) {
            columnNumber = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        if (columnNumber != 10) {
            return "表格的列数不一致";
        }

        // 获取sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        // 获取sheet的结束行
        int lastRowNum = sheet.getLastRowNum();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<User> userList = new ArrayList<>();
        User user = null;
        for(int i = firstRowNum + 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            user = new User();
            user.setId(row.getCell(1).getStringCellValue());
            user.setName(row.getCell(2).getStringCellValue());
            user.setPhone(row.getCell(3).getStringCellValue());
            user.setAddress(row.getCell(4).getStringCellValue());

            Double ageStr = row.getCell(5).getNumericCellValue();
            user.setAge(ageStr.intValue());

            String dateStr = row.getCell(6).getStringCellValue();
            user.setBirthday(simpleDateFormat.parse(dateStr));
            user.setHeight(row.getCell(7).getNumericCellValue());
            user.setAmount(new BigDecimal(row.getCell(8).getStringCellValue()));
            String admin = row.getCell(9).getStringCellValue();
            if("是".equals(admin)) {
                user.setIsAdmin(1);
            }else{
                user.setIsAdmin(0);
            }
            userList.add(user);
        }

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(JSON.toJSONString(userList.get(i)));
        }
        return "success";
    }

}
