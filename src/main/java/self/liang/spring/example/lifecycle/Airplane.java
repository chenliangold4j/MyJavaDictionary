package self.liang.spring.example.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class Airplane  implements InitializingBean, DisposableBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始话airplane");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁airplane");
    }
}
