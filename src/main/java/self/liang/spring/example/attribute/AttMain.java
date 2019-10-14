package self.liang.spring.example.attribute;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import self.liang.spring.example.lifecycle.MyConfigOfLifeCycle;

public class AttMain  {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfigOfPropertyValues.class);
        String[] names = ctx.getBeanDefinitionNames();
        for(String name:names){
            System.out.println(">>>"+name);
        }

        System.out.println(ctx.getBean("man").toString());
    }
}
