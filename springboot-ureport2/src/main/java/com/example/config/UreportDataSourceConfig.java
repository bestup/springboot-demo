package com.example.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableAutoConfiguration
public class UreportDataSourceConfig implements BuildinDatasource {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private DataSource dataSource;

    @Override
    public String name() {
        return "system";
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
