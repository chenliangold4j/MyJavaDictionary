package self.liang.springmvc.example;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import self.liang.mybatis.example.base.Employee;
import self.liang.springmvc.example.controller.bindparams.Car;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;


//父容器不扫描controller
@ComponentScan(value = {"self.liang.springmvc.example","self.liang.mybatis.example.base"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
@PropertySource("classpath:/mybatisConfig/config.properties")
@EnableTransactionManagement
public class RootConfig {

    /**
     * 配置spring的声明式事务
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    Config config(){
        return  new Config();
    }

    @Bean
    DataSource dataSource(@Autowired Config config){

        System.out.println(">>>>>>>>>>>>"+config.getDriver()+":"+config.getUrl());
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver(config.getDriver());
        pooledDataSource.setUrl(config.getUrl());
        pooledDataSource.setUsername(config.getUsername());
        pooledDataSource.setPassword(config.getPassword());
        return pooledDataSource;
    }

    /**
     * 配置SqlSessionFactoryBean
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver classPathResource = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setDataSource(dataSource);//设置数据源
        Class<?>[] typeAliases = new Class[1];
        typeAliases[0] = Employee.class;
        sqlSessionFactoryBean.setTypeAliases(typeAliases);//有alias注解的类

//        sqlSessionFactoryBean.setConfigLocation();设置本地配置文件。。只有少数如setting 和provider等起作用

        sqlSessionFactoryBean.setMapperLocations(classPathResource.getResources("classpath:mybatisConfig/mapper2/*.xml"));//设置mapper的路径
        return sqlSessionFactoryBean;
    }

    /**
     * 扫描所有mapper接口的实现。让这些mapper能够直接自动注入
     * @return
     */
    @Bean
    MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("self.liang.springmvc.example.dao");
        return mapperScannerConfigurer;
    }


    @Bean
    Car cartest() {
        Car car = new Car();
        car.setName("benchi");
        car.setPrice("2312321321");
        return car;
    }

}
