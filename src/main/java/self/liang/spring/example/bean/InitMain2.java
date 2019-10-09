package self.liang.spring.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class InitMain2 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(InitCoinfig2.class);

        Environment e =  ctx.getEnvironment();
        System.out.println("<<<<"+e.getProperty("os.name"));

        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(">>>>>>" + name);
        }

//        Object o1 = ctx.getBean("person");
//        Object o2 = ctx.getBean("person");
//        System.out.println(o1 == o2);
//
//        Object o3 = ctx.getBean("dog2");
        Map<String,Person> map = ctx.getBeansOfType(Person.class);
        System.out.println(map);

    }
}
