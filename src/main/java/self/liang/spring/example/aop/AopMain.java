package self.liang.spring.example.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import self.liang.spring.example.lifecycle.MyConfigOfLifeCycle;

public class AopMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyAopConfig.class);
         MathCalculator mathCalculator =   ctx.getBean(MathCalculator.class);
        System.out.println(mathCalculator.div(1,1));
        ctx.close();
    }

}
