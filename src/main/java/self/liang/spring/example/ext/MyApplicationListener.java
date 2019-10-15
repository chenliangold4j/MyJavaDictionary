package self.liang.spring.example.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import self.liang.MarkUtil;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        MarkUtil.soutWithMark("收到事件",event);
    }
}
