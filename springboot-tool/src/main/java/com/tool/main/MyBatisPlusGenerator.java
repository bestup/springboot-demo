package com.tool.main;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author halo.l
 * @desc: 通过表生成对应的实体类， 根据实际项目修改项目地址， 包结构
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) throws Exception {

        /**
         * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓主要信息配置，定义变量，方便修改↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
         */
        //数据库连接配置
        String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "root";

        //配置表
        //是否有表前缀，默认有(true)，如表名为tb_user，生成的实体就是TbUser
        boolean isTablePrefix = false;
        String tablePrefix = "tb_";

        String[] tables = new String[] {"tb_vehicle_score_log"};

        //获取项目路径
        String projectPath = System.getProperty("user.dir");
        String objPath = projectPath + "/src/main/java";

        //配置包路径
        String parentPackage = "com.tool";

        // 自定义Mapper.xml文件存放的路径
        String mapperXmlPath = "/src/main/resources/mapper/";

        MyBatisPlusGenerator.validTablePrefix(isTablePrefix, tablePrefix);
        /**
         * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑主要信息配置，定义变量，方便修改↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
         */

        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl(url);
        mpg.setDataSource(dsc);

        /*
         * 全局配置
         */
        GlobalConfig gc = new GlobalConfig();
        // 配置路径
        gc.setOutputDir(objPath);
        // 是否打开生成的文件夹
        gc.setOpen(true);
        // author
        gc.setAuthor("halo.l");
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        // 设置mapper接口后缀
        gc.setMapperName("%sMapper");
        // 设置Service接口后缀
        gc.setServiceName("%sService");
        // 设置Service实现类后缀
        gc.setServiceImplName("%sServiceImpl");
        // 设置controller类后缀
        gc.setControllerName("%sController");
        // 设置sql映射文件后缀
        gc.setXmlName("%sMapper");
        // 是否覆盖同名文件，默认是false
        gc.setFileOverride(false);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);

        mpg.setGlobalConfig(gc);

        /*
         * 策略配置
         */
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel)
                // 设置controller自动加RestController注解
                .setRestControllerStyle(true)
                //修改替换成你需要的表名，多个表名传数组,如果注释掉就生成库中所有的表
                .setInclude(tables).setEntityLombokModel(true);
        if(!isTablePrefix) {
            strategy.setTablePrefix(new String[] {tablePrefix}); // 此处可以修改为您的表前缀
        }
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage).setController("controller")
        /*
         * .setService("service") //服务接口 .setServiceImpl("service.impl") //服务实现
         * .setMapper("mapper") //dao层 .setXml("mapper") //dao层对应的xml文件
         * .setEntity("entity")
         */; // pojo对象
        mpg.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义xml的存放路径
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper.xml文件存放的路径
                return projectPath + mapperXmlPath + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置生成的资源模板
        TemplateConfig templateConfig = new TemplateConfig();
        //不生成controller
        templateConfig.setController(null);
        //不生成service
        templateConfig.setService(null);
        //不生成service实现
        templateConfig.setServiceImpl(null);
        // 关闭默认 xml 生成，调整生成 至 根目录
        templateConfig.setXml(null);

        mpg.setTemplate(templateConfig);

        // 执行生成
        mpg.execute();
    }

    /**
     * 校验表前缀
     * @param isTablePrefix
     * @param tablePrefix
     * @throws Exception
     */
    public static void validTablePrefix(boolean isTablePrefix, String tablePrefix) throws Exception {
        if(!isTablePrefix) {
            if(StringUtils.isBlank(tablePrefix)) {
                throw new Exception("表前缀不能为空");
            }
        }
        if(StringUtils.isNotBlank(tablePrefix)) {
            if(isTablePrefix) {
                throw new Exception("表前缀【tablePrefix】值是" + tablePrefix + "，请确认【isTablePrefix】属性值是否正确");
            }
        }
    }
}
