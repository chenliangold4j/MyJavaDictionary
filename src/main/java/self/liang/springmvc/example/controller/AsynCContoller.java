package self.liang.springmvc.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@Controller//异步任务
public class AsynCContoller {

    @RequestMapping("/createOrder")
    @ResponseBody
    public DeferredResult<Object> createOrder(){
        DeferredResult<Object> deferredResult = new DeferredResult<>(3000L,"create error");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deferredResult.setResult("123456");
            }
        }).start();

        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/async01")
    public Callable<String> async01(){
        System.out.println("主线程开始"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {

                System.out.println("子线程开始："+Thread.currentThread()+"==>"+System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("子线程结束："+Thread.currentThread()+"==>"+System.currentTimeMillis());
                return "Callable<String> async01()";
            }
        };
        System.out.println("主线程结束"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        return callable;
    }

}
