package com.example.service.impl;

import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.example.domain.UReportFile;
import com.example.service.UReportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义报表存储器(存数据库中)
 */

@Component
public class MyReportProvider implements ReportProvider {

    /**
     * 存储器名称
     */
    private static final String NAME = "mysql-provider";
    /**
     * 报表文件名前缀
     */
    private static String prefix = "mysql:";

    /**
     * 是否禁用
     */
    private static boolean disabled = false;

    @Autowired
    private UReportFileService reportFileService;

    /**
     * 根据报表名加载报表文件
     *
     * @param file 报表名称
     * @return 返回的InputStream
     */
    @Override
    public InputStream loadReport(String file) {
        UReportFile reportFile = reportFileService.selectByName(getCorrectName(file));
        ByteArrayInputStream stream = null;
        if (null != reportFile) {
            try {
                stream = new ByteArrayInputStream(reportFile.getContent());
            } catch (Exception e) {
                throw new ReportException(e);
            }
        }
        return stream;
    }

    /**
     * 根据报表名，删除指定的报表文件
     *
     * @param file 报表名称
     */
    @Override
    public void deleteReport(String file) {
        reportFileService.deleteByName(getCorrectName(file));
    }

    /**
     * 获取所有的报表文件
     *
     * @return 返回报表文件列表
     */
    @Override
    public List<ReportFile> getReportFiles() {
        List<UReportFile> list = reportFileService.selectAll();
        List<ReportFile> reportList = new ArrayList<>();
        if(!list.isEmpty()) {
            for (UReportFile file : list) {
                reportList.add(new ReportFile(file.getName(), file.getUpdateTime()));
            }
        }
        return reportList;
    }

    /**
     * 保存报表文件
     *
     * @param file 报表名称
     * @param content 报表的XML内容
     */
    @Override
    public void saveReport(String file, String content) {
        file = getCorrectName(file);
        UReportFile reportFile = reportFileService.selectByName(file);
        if(null == reportFile){
            reportFile = new UReportFile();
            reportFile.setName(file);
            reportFile.setContent(content.getBytes());
            reportFileService.insert(reportFile);
        }else{
            reportFile.setContent(content.getBytes());
            reportFileService.updateById(reportFile);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean disabled() {
        return disabled;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    /**
     * 获取没有前缀的文件名
     *
     * @param name 报表名称
     */
    private String getCorrectName(String name){
        if(name.startsWith(prefix)){
            name = name.substring(prefix.length());
        }
        return name;
    }
}
