package self.liang.spring.example.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import self.liang.MarkUtil;
import self.liang.spring.example.bean.simulate.Blue;

import java.util.Arrays;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MarkUtil.soutWithMark(">>>>>>>>>>>>>>>>>>> MyBeanDefinitionRegistryPostProcessor size :",registry.getBeanDefinitionCount());
        MarkUtil.soutWithMark(Arrays.toString(registry.getBeanDefinitionNames()));
        registry.registerBeanDefinition("hello",new RootBeanDefinition(Blue.class));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MarkUtil.soutWithMark(">>>>>>>>>>>>>>>>>>> MyBeanDefinitionRegistryPostProcessor size :",beanFactory.getBeanDefinitionCount());
    }
}
