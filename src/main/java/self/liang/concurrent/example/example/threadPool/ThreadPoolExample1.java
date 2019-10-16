package self.liang.concurrent.example.example.threadPool;



import org.slf4j.Logger;
import self.liang.log.example.TestLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolExample1 {
    private static Logger log= new TestLogger();
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("task:{}"+ index);
                }
            });
        }
        executorService.shutdown();
    }
}
