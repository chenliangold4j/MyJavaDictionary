package self.liang.spring.example.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProfileMain {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfigOfProfile.class);

        //无参构造器 设置环境
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("test","dev");
        ctx.register(MyConfigOfProfile.class);
        ctx.refresh();




        //vm 参数  ： -Dspring.profiles.active=
        String[] names = ctx.getBeanDefinitionNames();
        for(String s:names){
            System.out.println(">>>>"+s);
        }

    }
}
