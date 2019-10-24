package self.liang.springmvc.example;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * 1、 Spring 与SpringMVC 两个都是容器,存在父子关系（包含和被包含的关系） 。
 *
 * 2 、 Spring容器中存放着mapper代理对象，service对象，SpringMVC存放着Controller对象。
 * 子容器SpringMVC中可以访问父容器中的对象。但父容器Spring不能访问子容器SpringMVC的对象
 * （存在领域作用域的原因，子容器可以访问父容器中的成员，而子容器的成员则只能被自己使用）。如：Service对象可以在Controller层中注入，反之则不行。
 *
 * 3、Spring容器导入的properties配置文件，只能在Spring容器中用而在SpringMVC容器中不能读取到。
 * 需要在SpringMVC 的配置文件中重新进行导入properties文件，并且同样在父容器Spring中不能被使用，导入后使用@Value("${key}")在java类中进行读取。

 */
public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //添加编码器
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic dynamic =  servletContext.addFilter("characterEncodingFilter",characterEncodingFilter);
        dynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
    }


    /**
     * 获取根容器的配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 获取web容器的配置类（springMVC配置文件）子容器
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }


    /**
     * 获取映射
     * 拦截所有请求。
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
