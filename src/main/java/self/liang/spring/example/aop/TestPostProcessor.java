package self.liang.spring.example.aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;
import self.liang.MarkUtil;

@Component
public class TestPostProcessor implements InstantiationAwareBeanPostProcessor
{
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {


          MarkUtil.soutWithMark(beanName+"---postProcessBeforeInstantiation");

         return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        MarkUtil.soutWithMark(beanName+"---postProcessAfterInstantiation");
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        MarkUtil.soutWithMark(beanName+"---postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        MarkUtil.soutWithMark(beanName+"---postProcessAfterInitialization");
        return bean;
    }
}
