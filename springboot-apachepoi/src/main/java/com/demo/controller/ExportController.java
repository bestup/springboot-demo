package com.demo.controller;

import com.demo.model.Student;
import com.demo.util.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author halo.l
 * @date 2020-01-21
 */
@RestController
public class ExportController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }


    @GetMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**获取导出数据，实际开发中这里一般是从数据库查询的数据，
         这里演示是定义了一个实体对象，然后初始化多个对象，并放进我们需要导出的集合里*/
        List<Student> list = new ArrayList<>();
        int sex = 1;
        for(int i = 0 ;i < 10 ;i++){
            if(i%2 == 0){
                sex = 2;
            }
            Student stu = new Student(i+1,"学生"+(i+1)+"号",sex,18+i,20190001+i,"1998年-"+(i+1)+"月",new Date());
            list.add(stu);
        }
        exportExcelBook(request,response,list);
        return;
    }

    /**
     * 导出数据生成EXCEL方法
     * @param request
     * @param response
     * @param list
     * @throws IOException
     */
    public void exportExcelBook(HttpServletRequest request, HttpServletResponse response, List<Student> list)
            throws IOException {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //文件名称，客户端传来的参数，防止中文文件名乱码参数编码因此这里需要解码
        String fileName = URLDecoder.decode(request.getParameter("fileName"),"UTF-8");
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 2500);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 6000);

        // 设置表头字体样式
        HSSFFont columnHeadFont = workbook.createFont();
        columnHeadFont.setFontName("宋体");
        columnHeadFont.setFontHeightInPoints((short) 10);
        //columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 列头的样式
        HSSFCellStyle columnHeadStyle = workbook.createCellStyle();
        columnHeadStyle.setFont(columnHeadFont);
        // 左右居中
        //columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 上下居中
        //columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        columnHeadStyle.setLocked(true);
        columnHeadStyle.setWrapText(true);
        // 左边框的颜色
        columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        // 边框的大小
        columnHeadStyle.setBorderLeft((short) 1);
        // 右边框的颜色
        columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);
        // 边框的大小
        columnHeadStyle.setBorderRight((short) 1);
        // 设置单元格的边框为粗体
        //columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置单元格的边框颜色
        columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
        columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        // 设置普通单元格字体样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);

        //创建Excel工作表第一行
        HSSFRow row0 = sheet.createRow(0);
        // 设置行高
        row0.setHeight((short) 750);
        HSSFCell cell = row0.createCell(0);
        //设置单元格内容
        cell.setCellValue(new HSSFRichTextString("学生id"));
        //设置单元格字体样式
        cell.setCellStyle(columnHeadStyle);
        cell = row0.createCell(1);
        cell.setCellValue(new HSSFRichTextString("姓名"));
        cell.setCellStyle(columnHeadStyle);
        cell = row0.createCell(2);
        cell.setCellValue(new HSSFRichTextString("性别"));
        cell.setCellStyle(columnHeadStyle);
        cell = row0.createCell(3);
        cell.setCellValue(new HSSFRichTextString("年龄"));
        cell.setCellStyle(columnHeadStyle);
        cell = row0.createCell(4);
        cell.setCellValue(new HSSFRichTextString("学号"));
        cell.setCellStyle(columnHeadStyle);
        cell = row0.createCell(5);
        cell.setCellValue(new HSSFRichTextString("出生年月"));
        cell.setCellStyle(columnHeadStyle);
        cell = row0.createCell(6);
        cell.setCellValue(new HSSFRichTextString("创建时间"));
        cell.setCellStyle(columnHeadStyle);

        // 循环写入数据
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(new HSSFRichTextString(String.valueOf(stu.getId())));
            cell.setCellStyle(columnHeadStyle);
            cell = row.createCell(1);
            cell.setCellValue(new HSSFRichTextString(stu.getName()));
            cell.setCellStyle(columnHeadStyle);
            cell = row.createCell(2);
            if(stu.getSex() == 1){
                cell.setCellValue(new HSSFRichTextString("男"));
            }else{
                cell.setCellValue(new HSSFRichTextString("女"));
            }
            cell.setCellStyle(columnHeadStyle);
            cell = row.createCell(3);
            cell.setCellValue(new HSSFRichTextString(String.valueOf(stu.getAge())));
            cell.setCellStyle(columnHeadStyle);
            cell = row.createCell(4);
            cell.setCellValue(new HSSFRichTextString(String.valueOf(stu.getStudent_no())));
            cell.setCellStyle(columnHeadStyle);
            cell = row.createCell(5);
            cell.setCellValue(new HSSFRichTextString(stu.getBirthday()));
            cell.setCellStyle(columnHeadStyle);
            cell = row.createCell(6);
            cell.setCellValue(new HSSFRichTextString());
            cell.setCellStyle(columnHeadStyle);
            cell.setCellValue(new HSSFRichTextString(DateUtils.dateToString(stu.getCreate_time())));
        }
        // 获取输出流
        OutputStream os = response.getOutputStream();
        // 重置输出流
        response.reset();
        // 设定输出文件头
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String(fileName.getBytes("GB2312"), "8859_1") + ".xls");
        // 定义输出类型
        response.setContentType("application/msexcel");
        workbook.write(os);
        os.close();
        return;
    }




}
