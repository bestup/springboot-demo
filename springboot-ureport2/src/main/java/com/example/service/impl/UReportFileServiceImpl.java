package com.example.service.impl;

import com.example.domain.UReportFile;
import com.example.mapper.UReportFileMapper;
import com.example.service.UReportFileService;
import com.example.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UReportFileServiceImpl implements UReportFileService {

    @Autowired
    private UReportFileMapper reportFileMapper;

    @Override
    public UReportFile selectByName(String name) {
        return reportFileMapper.selectByName(name);
    }

    @Override
    public List<UReportFile> selectAll() {
        return reportFileMapper.selectAll();
    }

    @Override
    public Integer insert(UReportFile reportFile) {
        if(StringUtils.isBlank(reportFile.getId())) {
            reportFile.setId(StringUtil.getUUID());
        }
        return reportFileMapper.insert(reportFile);
    }

    @Override
    public Integer updateById(UReportFile reportFile) {
        return reportFileMapper.updateById(reportFile);
    }

    @Override
    public Integer deleteByName(String name) {
        return reportFileMapper.deleteByName(name);
    }

}
