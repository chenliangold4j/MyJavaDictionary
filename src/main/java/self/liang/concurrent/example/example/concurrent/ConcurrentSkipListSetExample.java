package self.liang.concurrent.example.example.concurrent;

import jdk.internal.instrumentation.Logger;
import self.liang.concurrent.example.annoations.ThreadSafe;
import self.liang.log.example.TestLogger;

import java.util.Set;
import java.util.concurrent.*;


@ThreadSafe
public class ConcurrentSkipListSetExample {

    private static Logger log= new TestLogger();
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static Set<Integer> set = new ConcurrentSkipListSet<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}"+set.size());
    }

    private static void update(int i) {
        set.add(i);
    }
}
