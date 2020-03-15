package self.liang.springboot.example.listenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;


@Component
public class HelloSpringRunListener implements SpringApplicationRunListener {


    public HelloSpringRunListener(SpringApplication springApplication,String[] args) {
    }

    @Override
    public void starting() {
        System.out.println("HelloSpringRunListener  starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("HelloSpringRunListener  environmentPrepared "+environment.getSystemProperties().get("os.name"));
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringRunListener  contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringRunListener  contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringRunListener  started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringRunListener  running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("HelloSpringRunListener  failed");
    }
}
