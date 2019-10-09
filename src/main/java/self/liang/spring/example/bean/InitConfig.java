package self.liang.spring.example.bean;


import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

//基于配置类的
@Configuration
//@ComponentScan(value = "self.liang.spring.example",excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
//})
@ComponentScans(value = {
        @ComponentScan(value = "self.liang.spring.example", includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Service.class}),//注解包含
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {PersonComponent.class}),//类型包含
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFIlter.class})
        }, useDefaultFilters = false)
})
public class InitConfig {

    //id为方法名
    @Bean("dogN")
    public Dog dog() {
        return new Dog("lisi", 20);
    }

}
