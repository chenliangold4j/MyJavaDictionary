package self.liang.spring.example.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("self.liang.spring.example.factory")
public class FactoryConfig {

    @Bean
    public UserFactoryBean user() {
        UserFactoryBean bean = new UserFactoryBean();
        bean.setName("kongxuan");
        bean.setEmial("test@131.com");
        return bean;
    }

}
