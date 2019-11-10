package self.liang.springboot.example.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("web 启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("web 关闭");
    }
}
