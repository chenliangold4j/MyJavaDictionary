package self.liang.spring.example.transaction;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TxMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TxConfig.class);
          UserService  userService = ctx.getBean(UserService.class);
          userService.insertUser();
    }
}
