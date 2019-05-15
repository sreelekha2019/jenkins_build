package war.generation.utils;

import war.common.exception.CustomException;
import war.common.utils.DateUtils;
import war.generation.entity.ColumnEntity;
import war.generation.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Auther: Aaron
 * @Date: 2018/7/19 14:28
 * @Description: 代码生成器工具类
 */
public class GenUtils {

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        templates.add("template/DTO.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        return templates;
    }

    /**
     *
     * 功能描述: 生成代码逻辑部分(打包zip方式)
     *
     * @param:  * @param null
     * @return:
     * @auther: Aaron
     * @date: 2018/7/19 14:28
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip,Map<String, String> configMap)  {
        //获取配置信息generator.properties配置
        Configuration config = getConfig();
        //根据请求替换
        if (configMap !=null && configMap.size()>0) {
            if(configMap.get("mainPath") !=null)
                config.setProperty("mainPath",configMap.get("mainPath"));
            if(configMap.get("packageName") !=null)
                config.setProperty("packageName",configMap.get("packageName"));
            if(configMap.get("moduleName") !=null)
                config.setProperty("moduleName",configMap.get("moduleName"));
        }

        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName" ));
        tableEntity.setComments(table.get("tableComment" ));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix" ));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName" ));
            columnEntity.setDataType(column.get("dataType" ));
            columnEntity.setComments(column.get("columnComment" ));
            columnEntity.setExtra(column.get("extra" ));
            columnEntity.setIsNullable(column.get("isNullable" ));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String packageName = configMap.get("package")!=null?configMap.get("package"):config.getString("package" );
        String mainPath = configMap.get("mainPath")!=null?configMap.get("mainPath"):config.getString("mainPath" );
        String moduleName = configMap.get("moduleName")!=null?configMap.get("moduleName"):config.getString("moduleName" );
        mainPath = StringUtils.isBlank(mainPath) ? "io.moria" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassName().toLowerCase());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", packageName);
        map.put("moduleName", moduleName);
        map.put("author", config.getString("author" ));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            //打包成zip方式
            try {
                //添加到zip

//                String coreName = configMap.get("moduleName")!=null?configMap.get("moduleName"):config.getString("moduleName" );
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), packageName, moduleName)));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new CustomException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * mybatis-plus方式生成
     */
    /**
     *
     * 功能描述: mybatis-plus方式生成代码，且不打包zip
     *
     * @param:  * @param null
     * @return:
     * @auther: Aaron
     * @date: 2018/7/19 15:26
     */
//    public static void plusGeneratorCode(){
//
//        try {
//
//            //获取包根目录
//            URL url = ResourceUtils.getURL("generation");
//            String packagePath = url.getPath().replace("/generation","");
//
//            //获取xml目录
//            url = ResourceUtils.getURL("resources");
//
//            String outputDir = packagePath+"/src/main/java";//输出目录
//            final String xmlOutputDir = url.getPath()+"/mapper/goods";
//            String author = "Aaron";//作者
//            String[] tables ={"goods"};
//
//
//            Map<String,String> jdbcMap = new HashMap<String,String>();
//            jdbcMap.put("driver","com.mysql.jdbc.Driver");
//            jdbcMap.put("username","root");
//            jdbcMap.put("password","root");
//            jdbcMap.put("url","jdbc:mysql://127.0.0.1:3306/moria?characterEncoding=utf8");
//
//            // 自定义需要填充的字段
//            List<TableFill> tableFillList = new ArrayList<>();
//            tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
//
//            // 代码生成器
//            String finalXmlOutputDir = xmlOutputDir;
//            com.baomidou.mybatisplus.generator.AutoGenerator mpg = new com.baomidou.mybatisplus.generator.AutoGenerator().setGlobalConfig(
//                    // 全局配置
//                    new GlobalConfig()
//                            .setOutputDir(outputDir)//输出目录
//                            .setFileOverride(true)// 是否覆盖文件
//                            .setActiveRecord(true)// 开启 activeRecord 模式
//                            .setEnableCache(false)// XML 二级缓存
//                            .setBaseResultMap(true)// XML ResultMap
//                            .setBaseColumnList(true)// XML columList
//                            .setAuthor(author)
//            ).setDataSource(
//                    // 数据源配置
//                    new DataSourceConfig()
//                            .setDbType(DbType.MYSQL)// 数据库类型
//                            .setTypeConvert(new MySqlTypeConvert() {
//                                // 自定义数据库表字段类型转换【可选】
//                                @Override
//                                public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                                    System.out.println("转换类型：" + fieldType);
//                                    return super.processTypeConvert(globalConfig, fieldType);
//                                }
//                            })
//                            .setDriverName(jdbcMap.get(("driver")))
//                            .setUsername(jdbcMap.get(("username")))
//                            .setPassword(jdbcMap.get(("password")))
//                            .setUrl(jdbcMap.get(("url")))
//            ).setStrategy(
//                    // 策略配置
//                    new StrategyConfig()
//                            // .setCapitalMode(true)// 全局大写命名
//                            // .setDbColumnUnderline(true)//全局下划线命名
//                            .setTablePrefix(new String[]{"bmd_", "mp_"})// 此处可以修改为您的表前缀
//                            .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
//                            .setInclude(tables) // 需要生成的表
//                            .setTableFillList(tableFillList)
//                            .setSuperControllerClass(null)
//                            .setSuperServiceClass(null)
//                            .setSuperServiceImplClass(null)
//                            .setSuperMapperClass(null)
//                            .setSuperEntityClass(null)
//                            .setSuperEntityColumns(null)
//            ).setPackageInfo(
//                    // 包配置
//                    new PackageConfig()
//                            .setModuleName("goods")
//                            .setParent("io.moria.core")// 自定义包路径
//                            .setController("controller")// 这里是控制器包名，默认 web
//            ).setCfg(
//                    // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
//                    new InjectionConfig() {
//                        @Override
//                        public void initMap() {
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                            this.setMap(map);
//                        }
//                    }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
//                            "/template/Dao.xml.vm" ) {
//                        // 自定义输出文件目录
//                        @Override
//                        public String outputFile(TableInfo tableInfo) {
//                            return "/Users/Aaron/my-workspace/ht-workspace/spring-cloud-moria/src/main/resources/mapper/goods/" + tableInfo.getEntityName() + ".xml";
//                        }
//                    }))
//            ).setTemplate(
//                    // 关闭默认 xml 生成，调整生成 至 根目录
//                    new TemplateConfig().setXml(null)
//            );
//            mpg.execute();
//
//            // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
//            System.err.println(mpg.getCfg().getMap().get("abc"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new CustomException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm" )) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("DTO.java.vm" )) {
            return packagePath + "DTO" + File.separator + className + "DTO.java";
        }


        if (template.contains("Dao.java.vm" )) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Service.java.vm" )) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm" )) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm" )) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        return null;
    }
}
