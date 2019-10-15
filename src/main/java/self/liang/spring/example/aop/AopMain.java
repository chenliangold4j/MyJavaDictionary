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
        *
        *   Aop流程：
        *       1）传入配置类
        *       2）刷新容器refresh
        *       3）	registerBeanPostProcessors(beanFactory);// Register bean processors that intercept bean creation.  注册bean处理器用来拦截bean的创建 intercept：拦截
        *            1）获取ioc容器的所有beanPostProcessor
        *            2) 给容器中加别的beanPostProcessor
        *            3）注册优先PriorityOrdered，接着 ordered 以及 regular的beanPostProcessor   注册的时候会创建beanPostProcessor对象
        *            4）创建internalAutoProxyCreator 的BeanPostProcessor{AnnotationAwareAspectJAutoProxyCreator}
        *                 1.创建bean实例。2.population：给bean赋值，3.initializeBean初始化bean
        *                                                               （一。invokeAwareMethods 判断是不是aware接口。调用方法）
        *                                                                （二。applyBeanPostProcessorsBeforeInitialization 应用后置处理器）
        *                                                                   三。invokeInitMethods 执行自定义初始化方法
        *                                                                     四 applyBeanPostProcessorsAfterInitialization
        *           5) 吧beanPostProcessor注册到BeanFactory中 beanFactory.addBeanPostProcessor(。。。);
        *
        * ======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator
        *
        *
        * 	   4） 完成beanFactory初始化工作，初始化剩下的单实例bean，finishBeanFactoryInitialization(beanFactory);
        *           1）便利容器中的bean  依次getBean创建对象
        *                   getBean --- doGetBean -- getSingleton
        *           2）创建bean：
        *                       1.先从缓存获取
        *                       2.createBean
        *                                 【beanPostProcessor  是创建bean的完成初始化前后】、【InstantiationAwareBeanPostProcessor是在创建bean实例之前先尝试用后置处理器返回对象】
        *                                 // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.给bean后处理器一个机会返回代理对象
			                               1.   Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			                               *     1.后置处理器先返回对象
			                               *            bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
                                                        if (bean != null) {
                                                            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                                                        }
                                                        *
        *                                  2. 正真创建是doCreateBean
        *
        *
        *       1)每一个bean创建之前。。用postProcessBeforeInstantiation（）；
        *           关心MatchCalculator 和LoaAspect的创建
        *               1）判断当前bean是否在advisedBeans中（保存了所有需要增强bean）  infrastructure：基础设施  candidate：候选人
        *               2）判断当前bean是否是基础类型的advice。pointCut。advisor。AopInfrastructureBean。或者是切面@apsect
        *               3）是否跳过
        *                   1）获取候选的增强器（切面里的通知方法）InstantiationModelAwarePointcutAdvisor
        *                   2）一直false跳过
        *       2）创建对象
        *        postProcessAfterInitialization
        *           return  wrapIfNecessary(bean, beanName, cacheKey);
        *        包装。。如果需要的话。
        *           1）获取当前所有增强器（切面） Object[] specificInterceptors
        *               1）找到候选的所有的增强器（找到通知方法是需要切入当前bean方法的）
        *               2)找到当前bean可以使用的增强器
        *               3）给增强器排序
        *           2）保存当前bean在advisedBeans中
        *           3）如果当前bean需要增强，创建当前bean的代理对象；
        *               1）获取所有增强器（通知方法）
        *               2）保存到proxyFactory
        *               3）通过代理工厂。创建代理对象
        *                       jdk和cglib  由spring决定  （未验证。我只看到直接用了cglib）
        *           4）给容器中返回使用cglib增强后的对象
        *           5）之后获取的就是这个组件的代理对象
        *
        *       3）目标方法执行
        *           容器中保存代理对象（cglib增强后的），这个对象保存了详细信息。
        *          1） 拦截CglibAopProxy.intercept
        *           2)getInterceptorsAndDynamicInterceptionAdvice获取拦截器链，通过AdvisorChainFactory
        *                   1）List<Object> interceptorList 保存所有拦截器
        *                   2）遍历所有的增强器 转为Interceptor  registry.getInterceptors
        *                   3）将增强器转为 MethodInterceptor  （如果是这个类型直接加，不是的话遍历适配器，看是否有适配器适配）
        *
        *           3）如果没有拦截器，直接执行目标方法
        *                   每一个通知方法又被包装为方法拦截器。。利用MethodInterceptor机制
        *           4）new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed(); 如果有，把执行的目标对象，目标方法，拦截器链等信息传入
        *           5） CglibMethodInvocation.proceed() 执行。调用拦截器链
        *               1)没有拦截器。。则直接执行方法
        *               2）判断类型 invocation.get()  invocation是个ThreadLocal对象
        *               3）proceed（）方法会链式调用。。顺序执行before  当前方法  after return throw等拦截器
        *
        * 总结 ：EnableAspectJAutoProxy 开启aop 会注册一个组件AnnotationAwareAspectJAutoProxyCreator，这是一个后处理器.在bean容器创建
        *       时，会注册后处理器（registerBeanPostProcessors）。之后会初始化剩下单实例bean（finishBeanFactoryInitialization）。初始化
        *       时，会创建业务逻辑组件和切面组件，AnnotationAwareAspectJAutoProxyCreator会拦截组件创建过程。组件创建完之后wrapIfNecessary
        *       会包装组件，切面和通知方法包装成增强器（Advisor），给业务逻辑对象创建一个代理对象。bean容器中就有代理对象。获取bean之后就可以
        *       执行目标方法。对象是一个CglibAopProxy对象。会执行CglibAopProxy的intercept（）方法。得到拦截器链。并按顺序调用即可
        *
        * */

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyAopConfig.class);
         MathCalculator mathCalculator =  ctx.getBean(MathCalculator.class);
         mathCalculator.div(1,1);
        ctx.close();
    }

}
