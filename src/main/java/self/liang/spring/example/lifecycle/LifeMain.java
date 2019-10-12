package self.liang.spring.example.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfigOfLifeCycle.class);
        Object o1 = ctx.getBean("car");
        Object o2 = ctx.getBean("car");//容器不管理多实例bean
        ctx.close();
    }
}
