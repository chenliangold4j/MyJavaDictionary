package self.liang.concurrent.example.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * 读锁重入
 * <p>
 * 要保证某个线程中的读锁可重入，要么满足获取读锁的条件（没有写或写请求），要么已经持有读锁（不管是否有写请求）
 * 要确定一个线程是否已经持有读锁，
 * 可以用一个map来存储已经持有读锁的线程以及对应线程获取读锁的次数，
 * 当需要判断某个线程能否获得读锁时，就利用map中存储的数据进行判断。
 * <p>
 * 写锁重入
 * 仅当一个线程已经持有写锁，才允许写锁重入（再次获得写锁）。下面是方法lockWrite和unlockWrite修改后的的代码。
 * <p>
 * 读锁升级到写锁
 * 有时，我们希望一个拥有读锁的线程，也能获得写锁。想要允许这样的操作，要求这个线程是唯一一个拥有读锁的线程。writeLock()需要做点改动来达到这个目的
 * <p>
 * 写锁降级到读锁
 * 有时拥有写锁的线程也希望得到读锁。如果一个线程拥有了写锁，那么自然其它线程是不可能拥有读锁或写锁了。所以对于一个拥有写锁的线程，再获得读锁，是不会有什么危险的
 */
public class ReadWriteLock2 {
    private Map<Thread, Integer> readingThreads =
            new HashMap<Thread, Integer>();
    // 重入写锁的次数
    private int writeAccesses = 0;
    //请求数写锁的数量
    private int writeRequests = 0;
    //当前写线程
    private Thread writingThread = null;

    public synchronized void lockRead()
            throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.put(callingThread,//获取读锁,读线程map增加计数
                (getReadAccessCount(callingThread) + 1));
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        // 当前读请求的线程持有写锁，直接获取
        if (isWriter(callingThread)) return true;
        if (hasWriter()) return false;//有写线程,失败
        if (isReader(callingThread)) return true;//已经获取读锁,重入成功
        if (hasWriteRequests()) return false;//有写请求,返回失败
        return true;
    }

    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        if (!isReader(callingThread)) {//非写线程,失败处理
            throw new IllegalMonitorStateException(
                    "Calling Thread does not" +
                            " hold a read lock on this ReadWriteLock");
        }
        //如果只获取过一次写锁,删除,否则减少.
        int accessCount = getReadAccessCount(callingThread);
        if (accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount - 1));
        }
        notifyAll();
    }


    public synchronized void lockWrite()
            throws InterruptedException {
        writeRequests++;//首先添加写请求,
        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)) {
            wait();
        }
        //请求写减少,重入写增加,并设置写线程为当前线程.
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }


    public synchronized void unlockWrite()
            throws InterruptedException {
        if (!isWriter(Thread.currentThread())) {
            throw new IllegalMonitorStateException(
                    "Calling Thread does not" +
                            " hold the write lock on this ReadWriteLock");
        }
        writeAccesses--;
        if (writeAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantWriteAccess(Thread callingThread) {
        if (isOnlyReader(callingThread)) return true;//只有一个读锁且是自己的,,返回true
        if (hasReaders()) return false;//还有读锁的获取者，返回false
        if (writingThread == null) return true;//没有写锁的获取者
        if (!isWriter(callingThread)) return false;//写锁是别人的,返回false.
        return true;
    }

    private int getReadAccessCount(Thread callingThread) {
        Integer accessCount = readingThreads.get(callingThread);
        if (accessCount == null) return 0;
        return accessCount.intValue();
    }

    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    private boolean isReader(Thread callingThread) {
        return readingThreads.get(callingThread) != null;
    }

    private boolean isOnlyReader(Thread callingThread) {
        return readingThreads.size() == 1 &&
                readingThreads.get(callingThread) != null;
    }

    private boolean hasWriter() {
        return writingThread != null;
    }

    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    private boolean hasWriteRequests() {
        return this.writeRequests > 0;
    }

}
