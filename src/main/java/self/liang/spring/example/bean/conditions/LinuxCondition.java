package self.liang.spring.example.bean.conditions;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory configurableListableBeanFactory =  context.getBeanFactory();
        ClassLoader classLoader = context.getClassLoader();

        BeanDefinitionRegistry beanDefinitionRegistry =  context.getRegistry();

        System.out.println("-----------------"+context.getEnvironment().getProperty("os.name"));
        if(context.getEnvironment().getProperty("os.name").contains("linux"))return true;
        return false;
    }
}
