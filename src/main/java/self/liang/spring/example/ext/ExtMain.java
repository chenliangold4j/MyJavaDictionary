package self.liang.spring.example.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.EventObject;

/**
 *spring refresh()解析
 *   1）prepareRefresh 预处理
 *          1）initPropertySources 初始化属性设置 // For subclasses: do nothing by default. 子类实现这个。用于自定义设置
 *          2）getEnvironment().validateRequiredProperties(); 校验属性等
 *          3）this.earlyApplicationEvents = new LinkedHashSet<>();保存容器中的一些早期事件
 *   2）ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();（obtain：获取）获取bean工厂
 *          1）refreshBeanFactory刷新以及创建bean工厂。
 *              DefaultListableBeanFactory是在GenericApplicationContext构造的时候new的。
 *              public GenericApplicationContext() {
 * 		          this.beanFactory = new DefaultListableBeanFactory();
 *              }
 *          2）ConfigurableListableBeanFactory beanFactory = getBeanFactory();获取bean工厂，由上一步创建
 *          3）返回beanFactory
 *   3)prepareBeanFactory(beanFactory);beanFactory的预准备工作
 *              		beanFactory.setBeanClassLoader(getClassLoader());  //设置类加载器
 * 	                	beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));//设置表达式解析器
 * 		                beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));//设置属性编辑器。用于属性转换
 *
 * 	                 	// Configure the bean factory with context callbacks.
 * 	                	beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));//添加BeanPostProcessor。用来给bean创建之后调用aware接口
 * 		                beanFactory.ignoreDependencyInterface(EnvironmentAware.class);//设置忽略的自动装配的接口。意思是autoAware这些不会装配
 * 		                EmbeddedValueResolverAware.class，ResourceLoaderAware.class，ApplicationEventPublisherAware.class，MessageSourceAware.class，ApplicationContextAware.class;
 *
 * 	                    // BeanFactory interface not registered as resolvable type in a plain factory.
 * 	                	// MessageSource registered (and found for autowiring) as a bean.
 * 	                   	beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);//注册可以解析的自动装配。可以自动注入
 * 	                	ResourceLoader.class, this)ApplicationEventPublisher.class, this)ApplicationContext.class, this);
 *
 *                      beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));//添加后处理器
 *                      。。。。。。//添加aspectJ支持
 *
 *                      beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());//注册环境对象【ConfigurableEnvironment】
 *                      beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());//注册系统属性对象
 *                      beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());//注册系统环境对象
 *
 *    4)postProcessBeanFactory(beanFactory);beanFactory装备工作完成后，后处理工作，给子类重写用于进一步设置
 *    以上是beanFactory的创建以及准备工作
 *
 *    5）invokeBeanFactoryPostProcessors(beanFactory);执行Bean工厂的后处理,有两种：BeanFactoryPostProcessor,BeanDefinitionRegistryPostProcessor
 *                      PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());
 *                          获取所有BeanDefinitionRegistryPostProcessor：invokeBeanFactoryPostProcessors：64 ~ 74行
 *                          看优先级排序if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {processedBeans.add(ppName);}
 *                          invokeBeanDefinitionRegistryPostProcessors按序执行
 *                          再排序Ordered接口并执行BeanDefinitionRegistryPostProcessor：if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class))则加入执行
 *                          然后找到剩下的BeanDefinitionRegistryPostProcessor并执行。
 *                          类上面的逻辑执行BeanFactoryPostProcessor
 *    6）registerBeanPostProcessors(beanFactory);注册BeanPostProcessors注册bean的后处理器，拦截bean创建
 *                      String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false)获取所有BeanPostProcessor
 *                      例如：DestructionAwareBeanPostProcessor，InstantiationAwareBeanPostProcessor，SmartInstantiationAwareBeanPostProcessor
 *                      MergedBeanDefinitionPostProcessor；执行时机不一样
 *                      这里也有优先级。
 *                      按照优先级注册
 *                      registerBeanPostProcessors(beanFactory, 。。。)
 *                      beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));再主个一个ApplicationListenerDetector
 *                      用于处理ApplicationListener
 *    7)initMessageSource();初始化MessageSource组件（国际化；消息绑定，消息解析）
 *                      获取beanFactory。看容器中是否有messageSource类型的组件。有则获取。没有则创建
 *                      MessageSource：取出国际化的配置信息。能按照区域信息获取
 *                      可以通过注入MessageSource
 *    8)initApplicationEventMulticaster()  初始话事件广播器
 *                      获取bean工厂，ConfigurableListableBeanFactory beanFactory = getBeanFactory();
 *                      获取用户的ApplicationEventMulticaster
 *                      如果没有获取到。则创建一个SimpleApplicationEventMulticaster并添加到bean工厂
 *
 *    9）onRefresh（）：留给子容器。给子类自定义行为
 *
 *    10）registerListeners（）：注册listener
 *                      获取所有ApplicationListener组件。然后添加监听到ApplicationEventMulticaster
 *                      并派发早期事件
 *
 *   11）finishBeanFactoryInitialization（） 初始化单实例bean
 *                      前期获取类型转换器，和值解析器，以及aop的组件等其他操作
 *                      beanFactory.preInstantiateSingletons()来开始初始化
 *                          遍历bean定义信息，依次进行初始化创建对象
 *                              判断 !bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()
 *                                  判断 isFactoryBean(beanName) 是的话用工厂的创建
 *                                  不是的话用普通bean创建
 *                                      getBean步骤：
 *                                          doGetBean（）：
 *                                              先获取bean如果已经创建则从singletonObjects中获取。没有则继续
 *                                               Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);是个线程安全的map
 *                                              没有的话进入创建流程
 *                                                  先获取父工厂：这里没有
 *                                                  先标记bean已经被创建，防止多线程创建
 *                                                  getMergedLocalBeanDefinition获取bean定义信息
 *                                                  String[] dependsOn = mbd.getDependsOn();获取依赖的其他bean
 *                                                  【如果没有这个bean。则嗲用getBean获取。这样一定程度保证了依赖的bean先创建】
 *                                                  启动单实例bean的创建流程sharedInstance = getSingleton。。。。
 *                                                      createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
 *                                                          获取定义信息，解析类型信息，解析方法信息等
 *                                                          Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
 *                                                          【给InstantiationAwareBeanPostProcessors用的返回代理对象的机会，这个后处理器会先执行】
 *                                                          没有返回单例对象则doCreateBean(beanName, mbdToUse, args);
 *                                                              instanceWrapper = createBeanInstance(beanName, mbd, args);创建bean实例
 *                                                                  instantiateUsingFactoryMethod用工厂方法创建bean：细节再研究
 *                                                              applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)
 *                                                                  调用MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(mbd, beanType, beanName);
 *                                                                  这个postProcessor再对象调用之后// Allow post-processors to modify the merged bean definition.
 *                                                                  之后缓存单例，以便能够解析循环引用，甚至在由生命周期接口(如BeanFactoryAware)触发时也是如此。
 *                                                              populateBean(beanName, mbd, instanceWrapper);为bean赋值
 *                                                                  赋值之前，拿到InstantiationAwareBeanPostProcessor处理器执行postProcessAfterInstantiation
 *                                                                  获取装配模式等属性并进行一些判断
 *                                                                  继续拿InstantiationAwareBeanPostProcessor处理器执行postProcessPropertyValues
 *                                                                  applyPropertyValues(beanName, mbd, bw, pvs);应用属性值
 *                                                                  为setter方法赋值
 *                                                              exposedObject = initializeBean(beanName, exposedObject, mbd);bean的初始化
 *                                                                  invokeAwareMethods(beanName, bean);执行aware接口的方法：即：xxxAware类的调用
 *                                                                  applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *                                                                  执行后处理器之前的方法 BeanPostProcessor.postProcessBeforeInitialization
 *                                                                  invokeInitMethods(beanName, wrappedBean, mbd);初始化，所谓初始化就是我们指定一开始要调用的方法
 *                                                                       先执行InitializingBean接口的初始化方法
 *                                                                       如果不是，则看是否自定义初始化方法（注解initMeted等）
 *                                                                  applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);初始化的后处理器
 *                                                                  执行postProcessAfterInitialization；
 *                                                              Object earlySingletonReference = getSingleton(beanName, false);从缓存获取bean。
 *                                                              如果这个bean之前缓存过。则执行相应的逻辑
 *                                                              registerDisposableBeanIfNecessary(beanName, bean, mbd);注册bean的销毁方法
 *                                                              容器关闭时调用
 *                                          doGetBean结束
 *                                       getBean结束
 *                                       addSingleton(beanName, singletonObject);添加bean进入map
 *                                       调用SmartInitializingSingleton的afterSingletonsInstantiated方法
 *  12）finishRefresh();完成刷新工作
 *                      clearResourceCaches();清楚缓存
 *                      initLifecycleProcessor();初始化生命周期有关的processor
 *                          LifecycleProcessor可以在refresh和close时候响应
 *                      publishEvent(new ContextRefreshedEvent(this));发布事件
 *                      LiveBeansView.registerApplicationContext(this);
 *  =================总结=====================
 *  1）spring开始先注册竟来bean定义信息
 *          1）xml注册bean
 *          2）注解注册bean
 *  2）spring会在合适的时机创建
 *      1）用到这个bean的时候，用getBean方法bean，创建好则保存在容器中；
 *      2）统一创建
 *      3）后处理
 *          每一个bean创建完成，都会使用各种后处理器进行处理，来增强功能
 *          AutowiredAnnotationBeanPostProcessor用来处理Autowire注解
 *          AnnotationAwareAspectJAutoProxyCreator用来处理Aop功能
 *          AsyncAnnotationBeanPostProcessor
 *          等等
 *      4)spring事件驱动模式：
 *          ApplicationListener:事件监听
 *          ApplicationEventMulticaster:事件派发
 *
 */




public class ExtMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        annotationConfigApplicationContext.publishEvent(new MyApplicationEvent("我发布的"));
        annotationConfigApplicationContext.close();
    }
}
