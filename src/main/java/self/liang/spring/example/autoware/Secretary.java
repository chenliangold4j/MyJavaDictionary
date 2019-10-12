package self.liang.spring.example.autoware;

import org.springframework.stereotype.Component;

@Component
public class Secretary {

    public void handleEmail(){
        System.out.println("处理老板的邮件");
    }

}
