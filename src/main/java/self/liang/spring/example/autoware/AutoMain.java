package self.liang.spring.example.autoware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import self.liang.spring.example.lifecycle.MyConfigOfLifeCycle;

public class AutoMain {
    /*
        autowired:自动注入
        @Service
        public class BookService {
            @Autowired
            BookDao bookDao;//属性名
           }
        1.按照类型去getbean
        2.找多多个。在将属性名作为组件id去容器中找
        3. @Qualifier("bookDao")指定装配id
        5 @Primary:让spring自动装配的时候。。默认使用首选bean


        ----------------------------------------------------
        spring还支持@Resource和@Inject 反正我不用

        ----------------------------------------------------
        自定义组件想要使用spring的底层的组件 applicationContext beanfactory xxx;
        自定义组件实现xxxAware;
     */

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
        BookService bookService = ctx.getBean(BookService.class);
        bookService.bookDao.selectByid();

        BookController bookController = ctx.getBean(BookController.class);
        System.out.println("bookservice id :"+bookController.bookService.getId());


        Boss boss = ctx.getBean(Boss.class);
        boss.getSecretary().handleEmail();
        System.out.println(boss.getSecretary() == ctx.getBean(Secretary.class));

        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(">>>" + name);
        }
    }
}
