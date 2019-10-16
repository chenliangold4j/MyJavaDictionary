package self.liang.concurrent.example;


import jdk.internal.instrumentation.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import self.liang.concurrent.example.example.threadLocal.RequestHolder;
import self.liang.log.example.TestLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpInterceptor extends HandlerInterceptorAdapter {
    private static Logger log= new TestLogger();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        log.info("afterCompletion");
        return;
    }
}
