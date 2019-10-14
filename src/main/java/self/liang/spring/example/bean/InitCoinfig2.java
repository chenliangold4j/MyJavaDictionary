package self.liang.spring.example.bean;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import self.liang.spring.example.bean.conditions.LinuxCondition;
import self.liang.spring.example.bean.conditions.MyImportBeanDefinnitionRegister;
import self.liang.spring.example.bean.conditions.MyImportSelector;
import self.liang.spring.example.bean.conditions.WindowsCondition;
import self.liang.spring.example.bean.simulate.Color;
import self.liang.spring.example.bean.simulate.ColorFaceotryBean;
import self.liang.spring.example.bean.simulate.Skin;

@Configuration
@Conditional(value =  {WindowsCondition.class
})//如果满足条件。则类下的bean都会被注册
@Import({Color.class, Skin.class, MyImportSelector.class, MyImportBeanDefinnitionRegister.class})
public class InitCoinfig2 {

    @Bean("dog2")
    @Lazy
    public  Dog dog(){
        System.out.println("---------创建dog");
        return new Dog("tidi",120);
    }

    @Scope("prototype")

    @Bean("person")
    public Person person(){
        System.out.println("---------创建person");
        return new Person("tom",11);
    }

    @Bean("bill")
    @Conditional(value =  {WindowsCondition.class
    })
    public Person person02(){
        return new Person("bill",23);
    }

    @Bean("jobs")
    @Conditional(value = {LinuxCondition.class})
    public Person person01(){
        return new Person("jobs",54);
    }

    @Bean
    public ColorFaceotryBean colorFaceotryBean(){
        return new ColorFaceotryBean();
    }
}
