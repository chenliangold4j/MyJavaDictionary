package self.liang.spring.example.bean.conditions;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import self.liang.spring.example.bean.simulate.Email;

public class MyImportBeanDefinnitionRegister implements ImportBeanDefinitionRegistrar {



    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if( !registry.containsBeanDefinition("Email")){
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Email.class);
            registry.registerBeanDefinition("Email",rootBeanDefinition);
        }
    }
}
