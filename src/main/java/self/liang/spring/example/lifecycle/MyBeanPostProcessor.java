package self.liang.spring.example.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor  implements BeanPostProcessor {

//    populateBean(beanName, mbd, instanceWrapper);  给bean属性赋值
//     if (mbd == null || !mbd.isSynthetic()) {
//        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); 执行bean前处理
//    }
//
//		try {
//        invokeInitMethods(beanName, wrappedBean, mbd);//执行初始化
//    }
//		catch (Throwable ex) {
//        throw new BeanCreationException(
//                (mbd != null ? mbd.getResourceDescription() : null),
//                beanName, "Invocation of init method failed", ex);
//    }
//		if (mbd == null || !mbd.isSynthetic()) {
//        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName); 执行bean后处理
//    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization"+beanName);
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization"+beanName);
        return bean;
    }


}
