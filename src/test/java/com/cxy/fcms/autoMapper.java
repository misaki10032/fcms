package com.cxy.fcms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class autoMapper {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String property = System.getProperty("user.dir");
        gc.setOutputDir(property+"/src/main/java");
        //设置作者信息
        gc.setAuthor("陈新予");
        gc.setOpen(false);
        gc.setFileOverride(false);
        gc.setServiceName("%sService");
        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);
        //设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/fcms?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("6800");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        //设置包
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("fcms");
        pc.setParent("com.cxy");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        //配置策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(
               "com_type"
        );
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //可选项
//        strategy.setSuperControllerClass(AbstractController.class.getName());
//        strategy.setSuperServiceClass(AbstractService.class.getName());
//        strategy.setSuperServiceImplClass(AbstractServiceImpl.class.getName());
//        strategy.setSuperMapperClass(AbstractMapper.class.getName());
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
