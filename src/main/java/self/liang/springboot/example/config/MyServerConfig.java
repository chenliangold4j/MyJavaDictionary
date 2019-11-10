package self.liang.springboot.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import self.liang.springboot.example.servlet.MyFilter;
import self.liang.springboot.example.servlet.MyListener;
import self.liang.springboot.example.servlet.MyServlet;

import java.util.Arrays;

@Configuration
public class MyServerConfig {

    //配置嵌入式servlet容器，注册三大组件

    @Bean
    public ServletRegistrationBean myServlet(){
       return  new ServletRegistrationBean(new MyServlet(),"/myServlet");
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        return new ServletListenerRegistrationBean(new MyListener());
    }
}
