package self.liang.springmvc.example;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;


//父容器不扫描controller
@ComponentScan(value = "self.liang.springmvc.example",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
})
public class MyRootConfig {

}
