package self.liang.spring.example.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(value = "self.liang.spring.example.aop")
@EnableAspectJAutoProxy//之后会有很多@eablexxxx  用来开启功能
public class MyAopConfig {


    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }




}
