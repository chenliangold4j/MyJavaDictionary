package self.liang.netty.example.com.self.worker;

import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ResultCollector {

    static AtomicLong beginTime = new AtomicLong(0);
    static AtomicInteger resultCount = new AtomicInteger(0);
    static volatile int count;

    public static void increaseCount() {
        if (resultCount.get() == 0) {
            beginTime.set(System.currentTimeMillis());
            new Thread(new ThroughputReporter()).start();
        }
        resultCount.getAndIncrement();
    }

    public static long getBeginTime() {
        return beginTime.get();
    }

    public static int getResultCount() {
        return resultCount.get();
    }

    static class ThroughputReporter implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (beginTime != null) {
                    double time = (System.currentTimeMillis() - beginTime.get()) / 1000;
                    int throughput = (int) (getResultCount() / time);
                    System.out.println("当前时间测试结果:" + throughput);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    if(count > 20){
                        resultCount.set(1);
                        beginTime.set(System.currentTimeMillis());
                        count =0;
                    }
                }
            }
        }
    }
}


