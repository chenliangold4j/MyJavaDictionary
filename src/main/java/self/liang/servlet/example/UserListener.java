package self.liang.servlet.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听启动和停止
 */
public class UserListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        ServletContextEvent.getServletContext();
        System.out.println("listener contextInitialized ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("listener contextDestroyed ");
    }

}
