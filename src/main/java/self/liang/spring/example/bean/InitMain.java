package self.liang.spring.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitMain {

    public static void main(String[] args) {
//        一
        //基础xml的bean初始化和注入
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("testbean.xml");
//        // 使用 getBean() 方法, 通过传入刚才的 id 名,来获取 bean, 但是这里返回的是一个 Object 对象, 所以要转型
//        Dog person = (Dog) ctx.getBean("dog");
//        // 打印 person
//        System.out.println(person);


//        二
        //基于配置类
        ApplicationContext ctx = new AnnotationConfigApplicationContext(InitConfig.class);
        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(">>>>>>" + name);
        }
        System.out.println("------\nBean 总计:" + ctx.getBeanDefinitionCount());
    }
}
