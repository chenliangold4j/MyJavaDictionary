package self.liang.spring.example.autoware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringValueResolver;

import javax.annotation.Resource;

@Controller
public class BookController implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    @Autowired
    BookService bookService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("--------------------------获取applicationContext0-------------");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("--------------------------setBeanName"+name+"-------------");
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println(resolver.resolveStringValue("你好${os.name} 我是#{18+19}"));
        System.out.println("--------------------------StringValueResolver"+resolver+"-------------");
    }
}
