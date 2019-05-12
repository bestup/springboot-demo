package com;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        
        /*
         * 配置路径
         */
        String projectPath = System.getProperty("user.dir"); //获取项目路径
        String objPath = projectPath + "/src/main/java";	 //获取java目录
        String parentPackage = "com";						 //配置包路径
        
        /*
         * 全局配置
         */
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir			(objPath);			//配置路径
        gc.setOpen				(false);			//是否打开生成的文件夹
        gc.setAuthor			("author");			//author		
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setMapperName		("%sMapper");		//设置mapper接口后缀
        gc.setServiceName		("%sService");		//设置Service接口后缀
        gc.setServiceImplName	("%sServiceImpl");	//设置Service实现类后缀
        gc.setControllerName	("%sController");	//设置controller类后缀
        gc.setXmlName			("%sMapper");		//设置sql映射文件后缀
        gc.setFileOverride(true);					// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(false);					// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);					// XML 二级缓存
        gc.setBaseResultMap(true);					// XML ResultMap
        gc.setBaseColumnList(false);				// XML columList
        mpg.setGlobalConfig(gc);

        /*
         * 数据源配置
         */
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true")
                .setUsername("root")
                .setPassword("root");
        mpg.setDataSource(dsc);
         
        /*
         * 策略配置
         */
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)	// 表名生成策略
        		.setRestControllerStyle(true)					//设置controller自动加RestController注解
        		//.setInclude(new String[] {"user"});			//修改替换成你需要的表名，多个表名传数组,如果注释掉就生成库中所有的表
                //.setTablePrefix(new String[] { "t_" })		// 此处可以修改为您的表前缀
        		;		
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage)
               .setController("controller")
               /*.setService("service")			//服务接口
                .setServiceImpl("service.impl")		//服务实现
                .setMapper("mapper")				//dao层
                .setXml("mapper")					//dao层对应的xml文件
                .setEntity("entity")*/;				//pojo对象
        mpg.setPackageInfo(packageConfig);
        
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        
        //自定义xml的存放路径
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper.xml文件存放的路径
                return projectPath + "/src/main/resources/mappers/"
                        + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        
        // 关闭默认 xml 生成，调整生成 至 根目录
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 执行生成
        mpg.execute();
    }
}
