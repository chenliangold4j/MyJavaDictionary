package self.liang.springboot.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import self.liang.springboot.example.collection.ioc.ColKeeper;


/**
 * 扫描范围
 * SpringBoot的注解扫描的默认规则是从SpringBoot的项目入口类。
 * 若入口类所在的包是com.example.demo那么自动扫描包的范围是com.example.demo包及其下面的子包，
 * 如果service包和dao包不在此包下面，则不会自动扫描。
 *
 *
 * profile
 * self.liang.spring.example.profile.MyConfigOfProfile 注解类的profile
 * spring boot会默认加载application.properties
 * 为了区分profile，可以在后面加 -{profile} 例如application-dev.properties
 * 之后再application.properties中加上 spring.profiles.active=dev;
 * 在spring中
 *         //无参构造器 设置环境
 *         AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 *         ctx.getEnvironment().setActiveProfiles("test","dev");
 *         ctx.register(MyConfigOfProfile.class);
 *         ctx.refresh();
 *         这是之前的笔记。。记录了spring时如何初始化容器的profile的
 *
 *    在application.properties中指定环境：spring.profiles.active=dev
 *    或者命令行参数：program arguments:--spring.profiles.active=dev
 *
 * 日志
 *      springboot能自动适配几乎所有日志，默认logback，引入其他框架的时候，只需要
 *      把这个框架的日志排除掉
 *      修改配置的话。。把logback.xml放入resources文件夹即可
 *      	Since logging is initialized before the ApplicationContext is created,
 *      	it is not possible to control logging from @PropertySources in Spring @Configuration files.
 *      	The only way to change the logging system or disable it entirely is via System properties.
 *      注意springboot官方文档的这句话。。log先于容器加载所以只能用配置来设置
 *
 *      spring官方推荐带后缀的xml  如 ：logback-spring.xml这样就是由spring来加载而不是日志框架来加载
 *      而且可以使用springProfile来指定环境生效
 *
 *  自动配置的主要实现在spring-boot-autoconfigure下，里面由自动配置的逻辑 一般都是xxxAutoConfiguration  配置文件呢容一般是  xxxProperties
 *      我们只需要编写配置文件即可  例如：org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration 就是
 *      有关jdbc datasource的自动配置 ，里面加载配置文件用@ConfigurationProperties(prefix = "spring.datasource")
 *
 *
 *springboot jar包方式静态资源映射规则：
 *      源码：org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration 的addResourceHandler
 *      1.所有 webjars/**，都去classpath:/META-INF/resources/webjars/ 下找资源
 *          webjars 以jar包的方式引入静态资源
 *          https://www.webjars.org/
 *          可以参照jquery的webjar
 *     2.@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
 *          public class ResourceProperties {
 *          //properties可以设置和资源有关的参数 缓存时间等
 *     3. /**访问档期那项目任何资源
 *          classpath:/META_INF/resources/",
 *          classpath:/resources/;
 *          classpath:/static/,
 *          classpath:/public/
 *
 *          这是源码添加resource的位置
 *          public class ResourceProperties {
 * 	            private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
 * 			    "classpath:/META-INF/resources/", "classpath:/resources/",
 * 			    "classpath:/static/", "classpath:/public/" };
 *
 *      4. 欢迎页 ；静态资源文件夹下所有index.html页面，被"/**"映射.
 *      5.所有的favicon.ico都是在静态资源文件下找
 *      6.添加静态资源文件夹.
 *
 *
 * springboot有关mvc的几个类
 * converter  数据绑定时候的类型转换器，，
 * formatter  格式转换器
 * httpMessageConverter 出去的数据转换。
 *
 * 修改springboot默认配置：
 *      1） springboot 在自动配置的很多组件的时候，先看容器中有没有用户自己配置的 bean，如果有就用用户的。@ConditionalOnMissingBean
 *          如果有些组件可以有多个，则合并起来。
 *
 * 扩展springmvc 示例MyMvcConfig
 *  编写一个配置类 @configuration  类型是 WebMvcConfigurerAdapter 不能标注@EnableWebMvc  如果用了@EnableWebMvc  会全面接管springMvc。不需要springMVC的自动配置
 *  既保留自动配置，也有扩展配置
 *   原理  WebMvcAutoConfigurationAdapter中有一个@Import(EnableWebMvcConfiguration.class)
 *      继承了 DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
 *
 *        @Autowired(required = false)
 * 	        public void setConfigurers(List<WebMvcConfigurer> configurers) {
 * 	        	if (!CollectionUtils.isEmpty(configurers)) {
 * 	        		this.configurers.addWebMvcConfigurers(configurers);
 *              }
 *           }
 *      注入了所有的WebMvcConfigurer。然后把注册的方法都调用一边
 *
 *      @Import(DelegatingWebMvcConfiguration.class)
 *           public @interface EnableWebMvc {
 *
 *      EnableWebMvc的核心就是DelegatingWebMvcConfiguration。
 *
 *      @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
 *           public class WebMvcAutoConfiguration {    mvc的自动配置类只有在WebMvcConfigurationSupport没有的时候才起作用。而DelegatingWebMvcConfiguration继承了它
 *
 * springboot错误处理机制
 *          1）默认返回错误页面。
 *          2）其他客户端，则相应json数据；
 *          参照 ErrorMvcAutoConfiguration；
 *
 * 注册sevlet filter listener
 *        ServletRegistrationBean
 *        FilterRegistrationBean
 *       ServletListenerRegistrationBean
 *
 *       在类  DispatcherServletAutoConfiguration 中
 *       @Bean(name = DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
 *                @ConditionalOnBean(value = DispatcherServlet.class, name = DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
 * 		   public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration(  //就是注册dispatchServlet
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//这里配置不自动初始化datasource ，没有配置datasource的配置文件
public class SpringBootEntrance {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEntrance.class);
    }
}
