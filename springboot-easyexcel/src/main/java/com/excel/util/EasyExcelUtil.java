package com.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author halo.l
 * @date 2020-01-10
 */
public class EasyExcelUtil {

    /**
     * 出Excel(07版.xlsx)到指定路径下
     * @param path          路径
     * @param excelName     excel名称
     * @param sheetName     sheet页名称
     * @param clazz
     * @param data
     */
    public static void export2File(String path, String excelName, String sheetName, Class clazz, List data) {
        String fileName = path.concat(excelName).concat(ExcelTypeEnum.XLSX.getValue());
        EasyExcel.write(fileName, clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 出Excel(07版.xlsx)到web
     * @param response      响应体
     * @param excelName     Excel名称
     * @param sheetName     sheet页名称
     * @param clazz         Excel要转换的类型
     * @param data          要导出的数据
     * @throws IOException
     */
    public static void export2Web(HttpServletResponse response, String excelName, String sheetName, Class clazz, List data) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        excelName = URLEncoder.encode(excelName, "utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 出Excel(07版.xlsx)到web  添加表头及内容样式
     * @param response      响应体
     * @param excelName     Excel名称
     * @param sheetName     sheet页名称
     * @param clazz         Excel要转换的类型
     * @param data          要导出的数据
     * @throws IOException
     */
    public static void export2WebAddHead(HttpServletResponse response, String headName, String excelName, String sheetName, Class clazz, List data) throws IOException {

        //头策略
        WriteCellStyle headCellStyle = new WriteCellStyle();
        //表头居中对齐
        headCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 背景色
        headCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        //表头字体设置
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headCellStyle.setWriteFont(headWriteFont);

        //内容策略
        WriteCellStyle contentCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 背景绿色
        /*contentCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());*/

        // 字体大小
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentCellStyle.setWriteFont(contentWriteFont);

        //设置 自动换行
        contentCellStyle.setWrapped(true);
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置边框样式
        contentCellStyle.setBorderLeft(BorderStyle.THIN);
        contentCellStyle.setBorderTop(BorderStyle.THIN);
        contentCellStyle.setBorderRight(BorderStyle.THIN);
        contentCellStyle.setBorderBottom(BorderStyle.THIN);

        //这个策略是,头是头的样式,内容是内容的样式,其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headCellStyle, contentCellStyle);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        excelName = URLEncoder.encode(excelName, "utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        EasyExcel.write(response.getOutputStream(), clazz)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(sheetName)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(data);
    }


    /**
     * 多个sheet导出
     * @param response
     * @param excelName
     * @param clazz
     * @param data
     * @throws IOException
     */
    public static void export2WebMoreSheet(HttpServletResponse response, String excelName,  Class clazz, List data) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        excelName = URLEncoder.encode(excelName, "utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        //文件输出到流
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(0, "用户信息").head(clazz).build();
        WriteSheet sheet2 = EasyExcel.writerSheet(1, "客户信息").head(clazz).build();
        excelWriter.write(data, sheet1);
        excelWriter.write(data, sheet2);
        excelWriter.finish();
    }
}
