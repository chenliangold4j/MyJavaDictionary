package self.liang.springmvc.example;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//
public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 获取根容器的配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MyRootConfig.class};
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
