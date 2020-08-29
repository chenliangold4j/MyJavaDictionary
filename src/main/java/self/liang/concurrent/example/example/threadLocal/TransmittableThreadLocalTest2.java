package self.liang.concurrent.example.example.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.*;

public class TransmittableThreadLocalTest2 {

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1));

    public static void main(String[] args) throws InterruptedException {
        // 1 创建线程变量
        TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<String>();
        // 2 投递三个任务
        for (int i = 0; i < 3; ++i) {
            bizPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 3休眠4s


        Thread.sleep(4000);
        // 4.设置线程变量
        parent.set("value-set-in-parent");
        // 5. 提交任务到线程池
        Runnable task = () -> {
            try {
                // 5.1访问线程变量
                System.out.println("parent:" + parent.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        // 5.2额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task);
        //5.3
        bizPoolExecutor.execute(ttlRunnable);

    }

}
