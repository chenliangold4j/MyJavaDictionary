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

    @Bean
    Car cartest() {
        Car car = new Car();
        car.setName("benchi");
        car.setPrice("2312321321");
        return car;
    }

}
