package self.liang.spring.example.lifecycle;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Train  {

    public Train(){
        System.out.println("constrcutor train");
    }

    @PostConstruct
    public void init(){
        System.out.println("init train");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("destroy train");
    }
}
