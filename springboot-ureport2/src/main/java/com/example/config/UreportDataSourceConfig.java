package com.example.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class UreportDataSourceConfig implements BuildinDatasource {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    @Override
    public String name() {
        return "内置数据源";
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        }
        return null;
    }
}
