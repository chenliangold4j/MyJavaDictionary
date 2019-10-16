package self.liang.servlet.example;


import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;


/**
 *
 * META-INF\services\javax.servlet.ServletContainerInitializer指定容器加载的全类名，
 * 之后就可以等容器启动了
 *
 * 1)servletContext注册web组件（servlet，Filter,Listener)
 * 2)使用编码的方式。在项目启动的时候添加组件
 *      必须项目启动时候添加
 *      1）ServletContainerInitializer得到的ServletContext对象添加
 *      2）ServletContextListener的ServletContextEvent.getServletContext()得到的ServletContext对象注册
 *
 *
 */

@HandlesTypes(value = {HelloService.class})//容器启动时。会将指定的类型下面的子类（实现类，子接口）传递过来
public class MyServletContainerInit implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("111111111111111111112132134214124124");
        System.out.println(c);
        //        ctx.addFilter()
        //        ctx.addServlet()
        //        ctx.addListener();
        //注册组件
        ServletRegistration.Dynamic dynamic = ctx.addServlet("userServlet",new UserServlet());
        //配置servlet映射
        dynamic.addMapping("/user");

        ctx.addListener(UserListener.class);
        FilterRegistration.Dynamic f_dynamic = ctx.addFilter("userFilter",new UserFilter());
        f_dynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

    }
}
