package self.liang.concurrent.example.lock;

//锁的最简单实现

/**
 * 注意其中的while(isLocked)循环，它又被叫做“自旋锁”。当isLocked为true时，调用lock()的线程在wait()调用上阻塞等待。
 * 为防止该线程没有收到notify()调用也从wait()中返回（也称作虚假唤醒），
 * 这个线程会重新去检查isLocked条件以决定当前是否可以安全地继续执行还是需要重新保持等待，
 * 而不是认为线程被唤醒了就可以安全地继续执行了。如果isLocked为false，当前线程会退出while(isLocked)循环，
 * 并将isLocked设回true，让其它正在调用lock()方法的线程能够在Lock实例上加锁。
 *
 * 当线程完成了临界区（位于lock()和unlock()之间）中的代码，就会调用unlock()
 * 。执行unlock()会重新将isLocked设置为false，并且通知（唤醒）其中一个（若有的话）在lock()方法中调用了wait()函数而处于等待状态的线程。
 *
 * 这里本质是利用synchronized。
 */
public class Lock1 {
    private boolean isLocked = false;

    public synchronized void lock()
        throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }
    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
