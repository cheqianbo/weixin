package com.onesuo.app.common.utils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动生成MyBatis的java文件
 */
public class GeneratorCERT {
    /**
     * 必要配置
     */
    protected String root_path = "D:\\files"; // 生成的文件存放目录
    protected String customTableNames[] = {
            "sys_issuing_cert_channel","sys_issuing_org_cert"
    }; //表名, 如果为空则生成该数据库内全部的表对应的Java文件

    /**
     * 数据库相关配置
     */
    protected String url = "jdbc:mysql://115.159.190.245:3306/certextraction?characterEncoding=utf8";
    protected String driverName = "com.mysql.jdbc.Driver";
    protected String user = "quanmin110";
    protected String password = "quanmin110.com";

    /**
     * 其他路径配置
     */
    protected String root_path_full = root_path + "/Generator";
    protected String bean_path = root_path + "/Generator/model";
    protected String mapper_path = root_path + "/Generator/dao";
    protected String query_path = root_path + "/Generator/query";
    protected String xml_path = root_path + "/Generator/mapper";
    protected String service_path = root_path + "/Generator/service";
    protected String controller_path = root_path + "/Generator/controller";

    /**
     * 包路径配置
     */
    private final String bean_package = "com.quanmin110.certextraction.model";
    private final String dao_package = "com.quanmin110.certextraction.dao";
    private final String query_package = "com.quanmin110.certextraction.model.query";
    private final String service_package = "com.quanmin110.certextraction.service";
    private final String controller_package = "com.quanmin110.certextraction.controller";

    private String tableName = null;
    private String tableNameFull = null;
    private String beanName = null;
    private String mapperName = null;
    private String daoName = null;
    private String queryName = null;
    private String serviceName = null;
    private String controllerName = null;

    private final String type_char = "char";
    private final String type_date = "date";
    private final String type_timestamp = "timestamp";
    private final String type_int = "int";
    private final String type_bigint = "bigint";
    private final String type_text = "text";
    private final String type_bit = "bit";
    private final String type_decimal = "decimal";
    private final String type_blob = "blob";
    private final String type_double = "double";

    private Connection conn = null;

    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }

    /**
     * 获取所有的表
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            // 是否输出自定义的表
            if (this.customTableNames.length > 0) {
                for (String customTableName : customTableNames) {
                    String tableName = results.getString(1);
                    if (customTableName.toUpperCase().equalsIgnoreCase(tableName)) {
                        if (tableName.startsWith("wp_")) {
                            customTableName = customTableName.substring(3);
                        }
                        tables.add(customTableName);
                    }
                }
            } else {
                String tableName = results.getString(1);
                tables.add(tableName);
            }
        }

        return tables;
    }

    private void processTable(String table) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_");
        String temp = null;
        for (int i = 0; i < tables.length; i++) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        String name = sb.toString();
        if(name.contains("Sys")){
            name = name.substring(3, name.length());
        }
        beanName = name + "";
        mapperName = name + "Mapper";
        daoName = name + "Dao";
        queryName = name + "Query";
        serviceName = name + "Service";
        controllerName = name + "Controller";
    }

    private String processType(String type) {
        if (type.indexOf(type_char) > -1) {
            return "String";
        } else if (type.indexOf(type_bigint) > -1) {
            return "Long";
        } else if (type.indexOf(type_int) > -1) {
            return "Integer";
        } else if (type.indexOf(type_date) > -1) {
            return "Date";
        } else if (type.indexOf(type_text) > -1) {
            return "String";
        } else if (type.indexOf(type_timestamp) > -1) {
            return "Date";
        } else if (type.indexOf(type_bit) > -1) {
            return "Boolean";
        } else if (type.indexOf(type_decimal) > -1) {
            return "java.math.BigDecimal";
        } else if (type.indexOf(type_blob) > -1) {
            return "String";
        } else if (type.indexOf(type_double) > -1) {
            return "Double";
        }
        return null;
    }

    private String processField(String field) {
        StringBuffer sb = new StringBuffer(field.length());
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }

    /**
     * 将实体类名首字母改为小写
     *
     * @param beanName
     * @return
     */
    private String processResultMapId(String beanName) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }

    /**
     * 构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" **/");
        return bw;
    }

    /**
     * 创建根目录
     */
    private void createRootDir(String rootPath) {
        File folder = new File(rootPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    /**
     * 生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildEntityBean(List<String> columns, List<String> types, List<String> comments, String tableComment,
                                 String className, String packName, String path) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File beanFile = new File(path, className + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + packName + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import io.swagger.annotations.ApiModel;");
        bw.newLine();
        bw.write("import io.swagger.annotations.ApiModelProperty;");
        bw.newLine();
        bw.write("import BaseModel;");
        bw.newLine();
        bw.write("import java.util.Date;");
        bw.newLine();
        bw.write("import lombok.Data;");
        bw.newLine();
        bw.newLine();
        bw.write("@Data");
        bw.newLine();
        bw.write("@ApiModel(value = \"" + (this.stringNotNull(tableComment) ? tableComment : className) + " 对象\")");
        bw.newLine();
        bw.write("public class " + className + " extends BaseModel {");
        bw.newLine();
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            if (this.stringNotNull(comments.get(i))) {
                bw.write("	@ApiModelProperty(value = \"" + comments.get(i) + "\")");
            } else {
                bw.write("	@ApiModelProperty(value = \"" + columns.get(i) + "\")");
            }
            bw.newLine();
            bw.write("\tprivate " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
        }
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 生成数据层查询参数类query
     *
     * @throws IOException
     */
    private void buildQuery(String tableComment) throws IOException {
        File folder = new File(query_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mapperFile = new File(query_path, this.queryName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + query_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import io.swagger.annotations.ApiModel;");
        bw.newLine();
        bw.write("import io.swagger.annotations.ApiModelProperty;");
        bw.newLine();
        bw.write("import BaseQuery;");
        bw.newLine();
        bw.write("import java.util.Date;");
        bw.newLine();
        bw.write("import lombok.Data;");
        bw.newLine();
        bw.newLine();
        bw.write("@Data");
        bw.newLine();
        bw.write("@ApiModel(value = \"" + (this.stringNotNull(tableComment) ? tableComment : queryName) + " 查询对象\")");
        bw.newLine();
        bw.write("public class " + queryName + " extends BaseQuery {");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 生成Dao文件
     *
     * @throws IOException
     */
    private void buildMapper(String tableComment) throws IOException {
        File folder = new File(mapper_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mapperFile = new File(mapper_path, this.daoName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + dao_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + this.bean_package + "." + this.beanName + ";");
        bw.newLine();
        bw.write("import BaseDao;");
        bw.newLine();
        bw = buildClassComment(bw, (this.stringNotNull(tableComment) ? tableComment + " " : "") + "数据层数据库操作接口类.");
        bw.newLine();
        bw.write("public interface " + daoName + " extends BaseDao<" + this.beanName + "> {");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildMapperXml(List<String> columns, List<String> types, List<String> comments, String tableComment)
            throws IOException {
        File folder = new File(xml_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mapperXmlFile = new File(xml_path, mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<!-- " + this.tableName + " " + tableComment + " -->");
        bw.newLine();
        bw.write("<mapper namespace=\"" + dao_package + "." + this.daoName + "\">");
        bw.newLine();
        buildSQL(bw, columns, types);
        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }

    private void buildSQL(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {
        String resultMapName = "entityResultMap";
        String beanNameFull = this.bean_package + "." + this.beanName;
        int size = columns.size();
        bw.write("\t<resultMap id=\"" + resultMapName + "\" type=\"" + beanNameFull + "\">");
        bw.newLine();
        for (String column : columns) {
            String tempField = processField(column);
            if ("id".equalsIgnoreCase(column)) {
                bw.write("\t\t<id property=\"" + tempField + "\" column=\"" + column + "\" />");
            } else {
                bw.write("\t\t<result property=\"" + tempField + "\" column=\"" + column + "\" />");
            }
            bw.newLine();
        }
        bw.write("\t</resultMap>");
        bw.newLine();

        bw.write("\t<sql id=\"Base_Column_List\" >");
        bw.newLine();
        bw.write("\t\t");
        for (int i = 0; i < size; i++) {
            bw.write("`" + columns.get(i) + "`");
            if (i != size - 1) {
                bw.write(",");
            }
        }
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();

        bw.write("\t<!-- 查询 -->");
        bw.newLine();
        bw.write("\t<select id=\"findOne\" resultMap=\"" + resultMapName + "\" parameterType=\"java.lang.Integer\">");
        bw.newLine();
        bw.write("\t\tSELECT");
        bw.newLine();

        bw.write("\t\t<include refid=\"Base_Column_List\" />");
        bw.newLine();

        bw.write("\t\tFROM " + tableNameFull);
        bw.newLine();
        bw.write("\t\tWHERE " + columns.get(0) + " = #{id}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();

        bw.write("\t<!-- 查询列表 -->");
        bw.newLine();
        bw.write("\t<select id=\"findAll\" resultMap=\"" + resultMapName + "\" parameterType=\"" + this.query_package + "." + this.queryName + "\">");
        bw.newLine();
        bw.write("\t\tSELECT");
        bw.newLine();

        bw.write("\t\t<include refid=\"Base_Column_List\" />");
        bw.newLine();

        bw.write("\t\tFROM " + tableNameFull);
        bw.newLine();
        bw.write("\t\t<if test=\"sortColumn != null and sortColumn != ''\">");
        bw.newLine();
        bw.write("\t\t\torder by ${sortColumn}");
        bw.newLine();
        bw.write("\t\t</if>");
        bw.newLine();
        bw.write("\t\t<if test=\"null != pgIndex and null != pgCount\">");
        bw.newLine();
        bw.write("\t\t\tlimit ${pgIndex*pgCount}, #{pgCount}");
        bw.newLine();
        bw.write("\t\t</if>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();

        bw.write("\t<!-- 查询数量 -->");
        bw.newLine();
        bw.write("\t<select id=\"count\" resultType=\"java.lang.Integer\" parameterType=\"" + this.query_package + "." + this.queryName + "\">");
        bw.newLine();
        bw.write("\t\tSELECT count(*) FROM " + tableNameFull);
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();

        bw.write("\t<!-- 保存 -->");
        bw.newLine();
        bw.write("\t<insert id=\"save\" parameterType=\"" + beanNameFull + "\" useGeneratedKeys=\"true\" keyProperty=\"id\">");
        bw.newLine();

        bw.write("\t\tINSERT INTO " + tableNameFull);
        bw.newLine();

        bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
        for (int i = 1; i < size; i++) {
            bw.write("\t\t\t<if test=\"" + processField(columns.get(i)) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\t\t"+columns.get(i)+",");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.write("\t\t</trim>");
        bw.newLine();

        bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
        for (int i = 1; i < size; i++) {
            bw.write("\t\t\t<if test=\"" + processField(columns.get(i)) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\t\t#{" + processField(columns.get(i)) + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.write("\t\t</trim>");
        bw.newLine();

        bw.write("\t</insert>");
        bw.newLine();

        bw.write("\t<!-- 更新 -->");
        bw.newLine();
        bw.write("\t<update id=\"update\" parameterType=\"" + beanNameFull + "\">");
        bw.newLine();
        bw.write("\t\tUPDATE " + tableNameFull);
        bw.newLine();

        bw.write("\t\t<set>");
        bw.newLine();
        for (int i = 1; i < size; i++) {
            bw.write("\t\t\t<if test=\"" + processField(columns.get(i)) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\t\t" + columns.get(i) + " = #{" + processField(columns.get(i)) + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.write("\t\t</set>");
        bw.newLine();

        bw.write("\t\tWHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();

        bw.write("\t<!-- 删除 -->");
        bw.newLine();
        bw.write("\t<update id=\"delete\" parameterType=\"java.lang.Integer\">");
        bw.newLine();
        bw.write("\t\tupdate " + tableNameFull + " set is_deleted=1 where " + columns.get(0) + " = #{id}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
    }

    /**
     * 获取所有的数据库表注释
     *
     * @return
     * @throws SQLException
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }

    /**
     * 生成Service
     */
    private void buildService(String path, String className, String packName, String tableComment) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File beanFile = new File(path, className + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));

        bw.write("package " + packName + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + this.bean_package + "." + this.beanName + ";");
        bw.newLine();
        bw.write("import " + this.query_package + "." + this.queryName + ";");
        bw.newLine();
        bw.write("import BaseService;");
        bw.newLine();
        bw = buildClassComment(bw, (this.stringNotNull(tableComment) ? tableComment + " " : "") + "服务.");
        bw.newLine();
        bw.write("public interface " + className + " extends BaseService<" + this.beanName + ", " + this.queryName + "> {");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 生成ServiceImpl
     */
    private void buildServiceImpl(String path, String className, String packName, String tableComment) throws IOException {
        path += "Impl";
        className += "Impl";

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File beanFile = new File(path, className + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        String minMapperName = daoName;
        minMapperName = minMapperName.substring(0, 1).toLowerCase() + minMapperName.substring(1);

        bw.write("package " + packName + ".impl;");
        bw.newLine();
        bw.newLine();
        bw.write("import " + this.service_package + "." + this.serviceName + ";");
        bw.newLine();
        bw.write("import " + this.dao_package + "." + this.daoName + ";");
        bw.newLine();
        bw.write("import " + this.bean_package + "." + this.beanName + ";");
        bw.newLine();
        bw.write("import " + this.query_package + "." + this.queryName + ";");
        bw.newLine();
        bw.write("import BaseServiceImpl;");
        bw.newLine();
        bw.write("import lombok.extern.slf4j.Slf4j;");
        bw.newLine();
        bw.write("import org.springframework.stereotype.Service;");
        bw.newLine();
        bw = buildClassComment(bw, (this.stringNotNull(tableComment) ? tableComment + " " : "") + "服务实现.");
        bw.newLine();
        bw.write("@Slf4j");
        bw.newLine();
        bw.write("@Service");
        bw.newLine();
        bw.write("public class " + className + " extends BaseServiceImpl<" + this.daoName + ", " + this.beanName + ", " + this.queryName + ">" + " implements " + this.serviceName + " {");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 生成Controller
     */
    private void buildController(String path, String className, String packName, String tableComment) throws IOException {
        String modelNote = this.stringNotNull(tableComment) ? tableComment : "";
        String modelName = processResultMapId(className.split("Controller")[0]);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File beanFile = new File(path, className + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));

        bw.write("package " + packName + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + this.service_package + "." + this.serviceName + ";");
        bw.newLine();
        bw.write("import " + this.bean_package + "." + this.beanName + ";");
        bw.newLine();
        bw.write("import " + this.query_package + "." + this.queryName + ";");
        bw.newLine();
        bw.write("import Page;");
        bw.newLine();
        bw.write("import BaseController;");
        bw.newLine();
        bw.write("import io.swagger.annotations.*;");
        bw.newLine();
        bw.write("import lombok.extern.slf4j.Slf4j;");
        bw.newLine();
        bw.write("import org.springframework.beans.factory.annotation.Autowired;");
        bw.newLine();
        bw.write("import org.springframework.web.bind.annotation.*;");
        bw.newLine();
        bw.write("import com.quanmin110.ResultDTO;");
        bw.newLine();
        bw.write("import java.util.List;");
        bw.newLine();
        bw.newLine();
        bw.write("@Slf4j");
        bw.newLine();
        bw.write("@RestController");
        bw.newLine();
        bw.write("@RequestMapping");
        bw.newLine();
        bw.write("@Api(description = \"" + (this.stringNotNull(tableComment) ? tableComment + " " : "") + "服务." + "\")");
        bw.newLine();
        bw.write("public class " + className + " extends BaseController {");
        bw.newLine();
        bw.write("\t@Autowired");
        bw.newLine();
        bw.write("\tprivate " + this.serviceName + " " + processResultMapId(this.serviceName) + ";");
        bw.newLine();

        bw.write("\t");
        bw.newLine();
        bw.write("\t@ApiOperation(value = \"查询\", notes = \"根据id获取" + modelNote + "对象\")");
        bw.newLine();
        bw.write("\t@ApiImplicitParam(paramType = \"path\", name = \"id\", value = \"" + modelNote + "id\", required = true, dataType = \"Integer\")");
        bw.newLine();
        bw.write("\t@RequestMapping(value = \"/" + modelName + "/{id}\", method = RequestMethod.GET)");
        bw.newLine();
        bw.write("\tObject findOne(@PathVariable(\"id\") Integer id) {");
        bw.newLine();
        bw.write("\t    log.debug(\"请求/"+modelName+"获取" + modelNote + "对象\");");
        bw.newLine();
        bw.write("\t    " + this.beanName + " result = " + processResultMapId(this.serviceName) + ".findOne(id);");
        bw.newLine();
        bw.write("\t    return result;");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();

        bw.write("\t");
        bw.newLine();
        bw.write("\t@ApiOperation(value = \"查询列表\", notes = \"根据查询条件获取" + modelNote + "对象列表\")");
        bw.newLine();
        bw.write("\t@ApiImplicitParams({");
        bw.newLine();
        bw.write("\t        @ApiImplicitParam(paramType = \"query\", name = \"search\", value = \"关键字\", required = false, dataType = \"String\"),");
        bw.newLine();
        bw.write("\t        @ApiImplicitParam(paramType = \"query\", name = \"sortColumn\", value = \"排序列与方向\", required = false, dataType = \"String\"),");
        bw.newLine();
        bw.write("\t        @ApiImplicitParam(paramType = \"query\", name = \"pgIndex\", value = \"当前页\", required = false, dataType = \"Integer\"),");
        bw.newLine();
        bw.write("\t        @ApiImplicitParam(paramType = \"query\", name = \"pgCount\", value = \"页大小\", required = false, dataType = \"Integer\"),");
        bw.newLine();
        bw.write("\t        @ApiImplicitParam(paramType = \"query\", name = \"beginTime\", value = \"开始时间\", required = false, dataType = \"String\"),");
        bw.newLine();
        bw.write("\t        @ApiImplicitParam(paramType = \"query\", name = \"endTime\", value = \"结束时间\", required = false, dataType = \"String\")})");
        bw.newLine();
        bw.write("\t@RequestMapping(value = \"/" + modelName + "s\", method = RequestMethod.GET)");
        bw.newLine();
        bw.write("\tObject findAll(" + this.queryName + " query) {");
        bw.newLine();
        bw.write("\t    Page<" + this.beanName + "> result = " + processResultMapId(this.serviceName) + ".findAllByPage(query);");
        bw.newLine();
        bw.write("\t    return result;");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();

        bw.write("\t");
        bw.newLine();
        bw.write("\t@ApiOperation(value = \"保存\", notes = \"保存" + modelNote + "对象并返回保存的" + modelNote + "对象\")");
        bw.newLine();
        bw.write("\t@RequestMapping(value = \"/" + modelName + "\", method = RequestMethod.POST)");
        bw.newLine();
        bw.write("\tObject save(@RequestBody " + this.beanName + " obj) {");
        bw.newLine();
        bw.write("\t    int id = " + processResultMapId(this.serviceName) + ".save(obj);");
        bw.newLine();
        bw.write("\t    return id;");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();

        bw.write("\t");
        bw.newLine();
        bw.write("\t@ApiOperation(value = \"更新\", notes = \"更新" + modelNote + "对象\")");
        bw.newLine();
        bw.write("\t@RequestMapping(value = \"/" + modelName + "\", method = RequestMethod.PUT)");
        bw.newLine();
        bw.write("\tObject update(@RequestBody " + this.beanName + " obj) {");
        bw.newLine();
        bw.write("\t    " + processResultMapId(this.serviceName) + ".update(obj);");
        bw.newLine();
        bw.write("\t    return ResultDTO.wrapSuccess(null);");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();

        bw.write("\t");
        bw.newLine();
        bw.write("\t@ApiOperation(value = \"删除\", notes = \"根据id删除" + modelNote + "对象\")");
        bw.newLine();
        bw.write("\t@ApiImplicitParam(paramType = \"path\", name = \"id\", value = \"" + modelNote + "id\", required = true, dataType = \"Integer\")");
        bw.newLine();
        bw.write("\t@RequestMapping(value = \"/" + modelName + "/{id}\", method = RequestMethod.DELETE)");
        bw.newLine();
        bw.write("\tObject delete(@PathVariable(\"id\") Integer id) {");
        bw.newLine();
        bw.write("\t    " + processResultMapId(this.serviceName) + ".delete(id);");
        bw.newLine();
        bw.write("\t    return ResultDTO.wrapSuccess(null);");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();

        bw.write("}");
        bw.flush();
        bw.close();
    }


    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for (String table : tables) {
            if ("schema_version".equalsIgnoreCase(table))
                continue;
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            comments = new ArrayList<String>();
            tableName = table;
            tableNameFull = table;
            pstate = conn.prepareStatement(prefix + tableNameFull);
            ResultSet results = pstate.executeQuery();
            while (results.next()) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            processTable(table);
            String tableComment = tableComments.get(tableNameFull);
            createRootDir(this.root_path_full);
            buildEntityBean(columns, types, comments, tableComment, this.beanName, this.bean_package, this.bean_path);
            buildMapper(tableComment);
            buildQuery(tableComment);
            buildMapperXml(columns, types, comments, tableComment);
            buildService(this.service_path, this.serviceName, this.service_package, tableComment);
            buildServiceImpl(this.service_path, this.serviceName, this.service_package, tableComment);
            buildController(this.controller_path, this.controllerName, this.controller_package, tableComment);
        }
        conn.close();
    }

    /**
     * 字符串空判断, null返回false
     *
     * @param str
     * @return
     */
    private boolean stringNotNull(String str) {
        if (null != str && !"".equals(str)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            GeneratorCERT generator = new GeneratorCERT();
            generator.generate();
            // 自动打开生成文件的目录
            Runtime.getRuntime().exec("cmd /c start explorer " + generator.root_path_full.replace("/", "\\"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}