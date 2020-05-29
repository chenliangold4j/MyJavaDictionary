package self.liang.concurrent.example.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -8540764104913403569L;

        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("锁资源数不能为负数~");
            }
            // 调用 AQS 设置资源总数，备用
            setState(count);
        }

        @Override
        public int tryAcquireShared(int reduceCount) {
            // cas 获取锁
            // 由 AQS 的 acquireShared -> doAcquireShared 调用
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        public boolean tryReleaseShared(int returnCount) {
            // cas 释放锁
            // 由AQS releaseShared -> doReleaseShared 调用
            for (; ; ) {
                int current = getState();
                int newState = current + returnCount;
                if (compareAndSetState(current, newState)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public boolean tryLock() {
        return false;
    }
    // 忽略，如要实现，直接调用 AQS

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        TwinsLock twinsLock = new TwinsLock();

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int num = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    twinsLock.lock();
                    System.out.println("开始工作"+num);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    twinsLock.unlock();
                    System.out.println("工作完成"+num);
                }
            });
        }
        Thread.sleep(10000);

    }
}
