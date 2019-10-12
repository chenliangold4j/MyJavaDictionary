package self.liang.spring.example.attribute;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/man.properties"})
public class MyConfigOfPropertyValues  {

    @Bean
    public Man man(){
        return new Man();
    }

}
