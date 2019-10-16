package self.liang.servlet.example;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(value = "/test2",asyncSupported = true)//支持异步模式
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        AsyncContext  startContext = req.startAsync();//开启异步模式

        startContext.start(new Runnable() {//异步处理的业务逻辑
            @Override
            public void run() {
                try {
//                    resp.getWriter().write("test2");
//////                    resp.getWriter().close();
                    Thread.sleep(1000);
                    startContext.complete();

                    //获取到异步上下文
                    AsyncContext asyncContext = req.getAsyncContext();
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
