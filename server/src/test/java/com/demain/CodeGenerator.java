//package com.demain;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Scanner;
//
//public class CodeGenerator {
//
//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotBlank(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//    public static void main(String[] args) {
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setAuthor("demain_lee");
//        gc.setOpen(false);      //生成后是否打开资源管理器
//        gc.setFileOverride(false); //重新生成时文件是否覆盖
//
//        //UserServie
//        gc.setServiceName("%sService");	//去掉Service接口的首字母I
//
//        gc.setIdType(IdType.ASSIGN_UUID); //主键策略
////        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
//
//        gc.setSwagger2(true);  // 实体属性 Swagger2 注解
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/platform-boot?serverTimezone=GMT%2B8");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("admin");
//        pc.setParent("com.demain.platform");
//        mpg.setPackageInfo(pc);
//
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
////        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
////        strategy.setSuperEntityClass("BaseEntity");
//        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//        // 公共父类
////        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        // 写于父类中的公共字段
////        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new VelocityTemplateEngine());
//        mpg.execute();
//    }
//}
