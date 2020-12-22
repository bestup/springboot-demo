package com.example.service;

import com.example.domain.UReportFile;

import java.util.List;

public interface UReportFileService {

    /**
     * 根据名称查报表
     * @return
     */
    UReportFile selectByName(String name);

    /**
     * 查所有报表
     * @return
     */
    List<UReportFile> selectAll();

    /**
     * 新增
     * @param reportFile
     * @return
     */
    Integer insert(UReportFile reportFile);

    /**
     * 更新
     * @param reportFile
     * @return
     */
    Integer updateById(UReportFile reportFile);

    /**
     * 根据报表名称删除
     * @param name
     * @return
     */
    Integer deleteByName(String name);

}
