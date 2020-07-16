package com.onesuo.app.config.database;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.onesuo.app.dao" ,sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig {
    @Value("${spring.datasource.url}")
    String dbUrl;
    @Value("${spring.datasource.name}")
    String userName;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;
    private static String MAPPER_PATH = "/mapper/*.xml";

    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean createSqlSessionFactoryBean(
        @Qualifier("dataSourceMybatis") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //添加mapper 扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_PATH;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        //设置datasource
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean(name = "dataSourceMybatis")
    @Qualifier("dataSourceMybatis")
    @Primary
    public DataSource dataSourceMybatis() {
        Map<String,Object> properties=new HashMap<>();
        properties.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME,driverClassName);
        properties.put(DruidDataSourceFactory.PROP_URL,dbUrl);
        properties.put(DruidDataSourceFactory.PROP_USERNAME,userName);
        properties.put(DruidDataSourceFactory.PROP_PASSWORD,password);
        //添加统计、SQL注入、日志过滤器
        properties.put(DruidDataSourceFactory.PROP_FILTERS,"stat,wall,log4j2");
        //sql合并，慢查询定义为5s
        properties.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES,"druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        try {
            return DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("sqlSessionFactoryBean") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}