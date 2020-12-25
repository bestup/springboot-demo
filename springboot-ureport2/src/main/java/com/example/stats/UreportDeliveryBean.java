package com.example.stats;

import com.alibaba.fastjson.JSON;
import com.example.domain.Delivery;
import com.example.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UreportDeliveryBean {

    private Logger log = LoggerFactory.getLogger(UreportDeliveryBean.class);

    /*public List<Map<String,Object>> loadReportData(String dsName,String datasetName,Map<String,Object> parameters){
        return null;
    }
    public List<Map<String,Object>> buildReport(String dsName,String datasetName,Map<String,Object> parameters){
        return null;
    }*/

    @Autowired
    private DeliveryMapper deliveryMapper;

    public List<Delivery> loadData(String dsName, String datasetName, Map<String,Object> parameters){
        log.info("dsName:" + dsName);
        log.info("datasetName:" + datasetName);
        log.info("parameters:" + JSON.toJSONString(parameters));
        return deliveryMapper.selectByPage(new Delivery());
    }

}
