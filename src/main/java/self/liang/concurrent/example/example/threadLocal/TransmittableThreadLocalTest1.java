package self.liang.concurrent.example.example.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransmittableThreadLocalTest1 {

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {


        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<String>();
        context.set("value-set-in-parent");

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(context.get());
            }
        };
        // 额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task);
        executorService.submit(ttlRunnable);


    }

}
