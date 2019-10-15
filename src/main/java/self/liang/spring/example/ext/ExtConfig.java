package self.liang.spring.example.ext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展原理
 *  BeanPostProcessor:bean后处理器，bean创建对象初始化前后
 *   BeanFactoryPostProcessor:bean工厂的后处理器
 *         在beanFactory标准初始化之后调用 在bean的定义已经保存加载，但是bean实例还未创建
 *
 * ioc容器创建对象
 * 执行invokeBeanFactoryPostProcessors；里面会查找和执行BeanFactoryPostProcessor，会有有序和无序两种
 * 先有序后无序
 *
 *
 *
 * BeanDefinitionRegistryPostProcessor
 *     postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
 *     bean定义信息将要加载，bean实例还没创建
 *     beanFactory是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例的
 *
 *     具体可自己看源码了。和aop类似
 *
 * ApplicationListener:监听容器中发布的事件，事件驱动模型开发
 *
 * 基于事件开发步骤：
 *
 *      1）写一个监听器监听某个事件(ApplicationEven及其子类)
 *      2)监听器加入容器
 *      3）只要容器中有相关事件发布，我们就能监听到这个事件
 *              ContextRefreshedEvent：容器刷新事件会发布这个事件
 *              ContextClosedEvent：关闭容器事件
 *      4）发布一个事件
 */
@Configuration
@ComponentScan("self.liang.spring.example.ext")
public class ExtConfig {




}
