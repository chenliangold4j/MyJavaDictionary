package self.liang.spring.example.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import self.liang.spring.example.transaction.TxConfig;
import self.liang.spring.example.transaction.UserService;

public class TestMain {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FactoryConfig.class);
        User user = (User) ctx.getBean("user");
        TestService testService = ctx.getBean(TestService.class);
        testService.sayHello();
        System.out.println(user);
    }
}
