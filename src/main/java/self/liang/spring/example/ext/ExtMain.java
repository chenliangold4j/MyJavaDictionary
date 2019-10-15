package self.liang.spring.example.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.EventObject;

public class ExtMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        annotationConfigApplicationContext.publishEvent(new MyApplicationEvent("我发布的"));
        annotationConfigApplicationContext.close();
    }
}
