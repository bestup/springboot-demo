package com.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author halo.l
 * @date 2020-01-21
 */
@Slf4j
public class ExcelUtil {

    /**
     * @param flag       true 第一列数据为数字序号，false 第一列为正常数据显示
     * @param workbook   HSSFWorkbook对象
     * @param fileName   下载后文件名称
     * @param sheetName  工作簿sheet表名称 null为默认名称“sheet0”
     * @param sheetCount 表列数总合
     * @param headers    表第一列 标题名称数组
     * @param list       表需要填入数据对象
     * @param tableNames 根据标题名称一一对应数据库映射字段（为了从map.get(key)取值）
     * @param response
     * @return
     */
    public static void downLoadExcel(boolean flag, HSSFWorkbook workbook, String fileName, String sheetName, int sheetCount, String headers[], List<Map> list, String tableNames[], HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //第一列为数字序号
        if (flag) {
            if (sheetCount != headers.length || sheetCount - 1 != tableNames.length) {
                log.info("第一列为序号时，sheetCount参数和headers数组长度一致，且比tableNames数组长度大1，请核对代码");
                log.info("请核对sheet长度和headers，tableNames");
            }
        }
        //第一列为正常数据，不为数字
        if (flag == false) {
            if (sheetCount != headers.length || sheetCount != tableNames.length) {
                log.info("sheetCount参数和headers数组tableNames数组长度应一致，请核对代码");
                log.info("请核对sheet长度，headers，tableNames");
            }
        }
        //默认文件名
        if (StringUtils.isEmpty(fileName)) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
            fileName = dateFormat.format(date);
        }
        int rowNum = 1;
        HSSFSheet sheet = null;
        //默认名称 “sheet0”
        if (StringUtils.isEmpty(sheetName)) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(sheetName);
        }
        for (int i = 0; i < sheetCount; i++) {
            sheet.setColumnWidth(i, 3766);
        }

        //设置字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("宋体");
//        titleFont.setFontHeightInPoints((short) 12);//设置字体大小
        titleFont.setBold(true);//粗体显示
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 12);//设置字体大小

        //title单元格样式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setFont(titleFont);//设置字体
        //设置这些样式
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        // 背景色
        titleCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
        //设置边框
        titleCellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        titleCellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        titleCellStyle.setBorderTop(BorderStyle.THIN);//上边框
        titleCellStyle.setBorderRight(BorderStyle.THIN);//右边框

        //列表单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);//设置字体
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        //设置第一行所有标题字段
        HSSFRow row = sheet.createRow(0);
        //表格每列所有字段名称赋值
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(new HSSFRichTextString(headers[i]));
            row.getCell(i).setCellStyle(titleCellStyle);
        }
        for (Map map : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            if (flag) {
                for (int i = 0; i < headers.length; i++) {
                    if (i == 0) {
                        row1.createCell(i).setCellValue(rowNum);
                        row1.getCell(i).setCellStyle(cellStyle);
                        continue;
                    }
                    row1.createCell(i).setCellValue(Objects.isNull(map.get(tableNames[i - 1])) ? "" : map.get(tableNames[i - 1]).toString());
                    row1.getCell(i).setCellStyle(cellStyle);
                }
            } else {
                for (int i = 0; i < headers.length; i++) {
                    row1.createCell(i).setCellValue(Objects.isNull(map.get(tableNames[i])) ? "" : map.get(tableNames[i]).toString());
                    row1.getCell(i).setCellStyle(cellStyle);
                }
            }
            rowNum++;
        }
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.flushBuffer();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }


    /**
     * 通过实体类获取字段列表和中文备注
     *
     * @param o           实体类
     * @param excludeList 实体类排除字段列表
     * @return
     */
    public static List<String[]> getFiledName(Object o, List<String> excludeList) {
        ArrayList<String[]> list = new ArrayList<>();
        // Field类的属性信息
        Field[] declaredFields = o.getClass().getDeclaredFields();
        //去掉序列号
        excludeList.add("serialVersionUID");
        Object[] fields = Arrays.stream(declaredFields).filter(q -> !excludeList.contains(q.getName())).toArray();
        //字段名
        String[] fieldNames = new String[fields.length];
        //中文列名
        String[] fieldTitles = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field obj = (Field) fields[i];
            fieldNames[i] = obj.getName();
            //fieldTitles[i] = obj.getAnnotation(ApiModelProperty.class).value();
        }
        list.add(fieldTitles);
        list.add(fieldNames);
        return list;
    }


}
