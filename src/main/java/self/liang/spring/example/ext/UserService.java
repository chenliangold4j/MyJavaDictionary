package self.liang.spring.example.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @EventListener(classes ={ ApplicationEvent.class})
    public void listen(ApplicationEvent applicationEvent){
        System.out.println("业务监听到shjian");
    }
}

