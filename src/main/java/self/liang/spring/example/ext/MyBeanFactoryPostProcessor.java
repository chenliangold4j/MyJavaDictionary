package self.liang.spring.example.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import self.liang.MarkUtil;

import java.util.Arrays;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MarkUtil.soutWithMark(">>>>>>>>>>>>>>>>>>> beanfacotry size :",beanFactory.getBeanDefinitionNames().length);
        MarkUtil.soutWithMark(Arrays.toString(beanFactory.getBeanDefinitionNames()));
    }
}
