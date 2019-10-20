package self.liang.springmvc.example;


import ch.qos.logback.core.joran.util.StringToObjectConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import self.liang.springmvc.example.controller.bindparams.Car;
import self.liang.springmvc.example.controller.bindparams.StringToDateConvert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


//父容器不扫描controller
@ComponentScan(value = "self.liang.springmvc.example",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
})
public class RootConfig {

    @Bean
    Car cartest(){
        Car car = new Car();
        car.setName("benchi");
        car.setPrice("2312321321");
        return  car;
    }

}
