package self.liang.spring.example.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import self.liang.spring.example.lifecycle.MyConfigOfLifeCycle;

public class AopMain {
    public static void main(String[] args) {

        /*
        * @EnableAspectJAutoProxy
        *    @Import(AspectJAutoProxyRegistrar.class)
        *        class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar
        *                org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
        *                     extends AspectJAwareAdvisorAutoProxyCreator
        *                        extends AbstractAdvisorAutoProxyCreator
        *                             AbstractAdvisorAutoProxyCreator 。。。。等等继承
        *                                   implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware 最终要是实现了者两个接口
        *
        * //在spirng中Enablexxx要看注册了什么组件。。以及组件的功能是什么
        *
        * */

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyAopConfig.class);
         MathCalculator mathCalculator =   ctx.getBean(MathCalculator.class);
        System.out.println(mathCalculator.div(1,1));
        ctx.close();
    }

}
