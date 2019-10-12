package self.liang.spring.example.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Car implements ApplicationContextAware {
    ApplicationContext applicationContext;

    public Car(){
        System.out.println("car constructor");
    }

    public void init(){
        System.out.println("car ... init ...");
    }

    public void destroy(){
        System.out.println("car destroy");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;

    }

}
