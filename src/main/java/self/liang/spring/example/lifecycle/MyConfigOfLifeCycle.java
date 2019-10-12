package self.liang.spring.example.lifecycle;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("self.liang.spring.example.lifecycle")
public class MyConfigOfLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    @Scope("prototype")
    public Car car(){
        return  new Car();
    }

}
