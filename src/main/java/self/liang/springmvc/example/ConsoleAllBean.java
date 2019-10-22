package self.liang.springmvc.example;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConsoleAllBean implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
        String[] names =      applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("-----------------------"+name);
        }
    }
}
