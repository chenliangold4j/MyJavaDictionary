package self.liang.spring.example.autoware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Boss {

//    @Autowired //有参构造注入 只有一个可以省略
    public Boss(Secretary secretary) {
        this.secretary = secretary;
    }

    Secretary secretary;

    public Secretary getSecretary() {
        return secretary;
    }

//    @Autowired //方法参数从容器中获取
    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }
}
