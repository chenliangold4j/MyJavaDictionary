package self.liang.concurrent.example.lock;

/**
 * lock1 没有考虑重入性
 *注意到现在的while循环（自旋锁）也考虑到了已锁住该Lock实例的线程。如果当前的锁对象没有被加锁(isLocked = false)，
 * 或者当前调用线程已经对该Lock实例加了锁，那么while循环就不会被执行，调用lock()的线程就可以退出该方法（译者注：“被允许退出该方法”在当前语义下就是指不会调用wait()而导致阻塞）。
 *
 * 除此之外，我们需要记录同一个线程重复对一个锁对象加锁的次数。
 * 否则，一次unblock()调用就会解除整个锁，即使当前锁已经被加锁过多次。在unlock()调用没有达到对应lock()调用的次数之前，我们不希望锁被解除。
 *
 * 现在这个Lock类就是可重入的了。
 * 如果用Lock来保护临界区，并且临界区有可能会抛出异常，那么在finally语句中调用unlock()就显得非常重要了。
 * 这样可以保证这个锁对象可以被解锁以便其它线程能继续对其加锁。以下是一个示例：
 *
 * lock.lock();
 * try{
 *     //do critical section code,
 *     //which may throw exception
 * } finally {
 *     lock.unlock();
 * }
 * 这个简单的结构可以保证当临界区抛出异常时Lock对象可以被解锁。
 * 如果不是在finally语句中调用的unlock()，当临界区抛出异常时，
 * Lock对象将永远停留在被锁住的状态，这会导致其它所有在该Lock对象上调用lock()的线程一直阻塞。
 */
public class Lock2 {
    boolean isLocked = false;
    Thread lockedBy = null;
    int lockedCount = 0;

    public synchronized void lock()
            throws InterruptedException {
        Thread callingThread =
                Thread.currentThread();
        while (isLocked && lockedBy != callingThread) {
            wait();
        }
        isLocked = true;
        lockedCount++;
        lockedBy = callingThread;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() ==
                this.lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }


}
