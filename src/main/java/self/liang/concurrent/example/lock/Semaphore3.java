package self.liang.concurrent.example.lock;

/**
 * Semaphore2并没有限制信号的数量。下面的代码将Semaphore2改造成一个信号数量有上限的BoundedSemaphore 即Semaphore3
 *
 * 通过有上限的Semaphore可以限制进入某代码块的线程数量。
 * 设想一下，在例子中，如果BoundedSemaphore 上限设为5将会发生什么？
 * 意味着允许5个线程同时访问关键区域，但是你必须保证，这个5个线程不会互相冲突。
 * 否则你的应用程序将不能正常运行。
 */
public class Semaphore3 {
    private int signals = 0;
    private int bound = 0;

    public Semaphore3(int upperBound) {
        this.bound = upperBound;
    }

    public synchronized void take() throws InterruptedException {
        while (this.signals == bound) wait();
        this.signals++;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        while (this.signals == 0) wait();
        this.signals--;
        this.notify();
    }

    public static void main(String[] args) throws InterruptedException {
//        当信号量的数量上限是1时，Semaphore可以被当做锁来使用。通过take和release方法来保护关键区域。请看下面的例子：
        Semaphore3 semaphore = new Semaphore3(1);
        semaphore.take();
        try {
        //critical section
        } finally {
            semaphore.release();
        }
        /*
        在前面的例子中，Semaphore被用来在多个线程之间传递信号，这种情况下，take和release分别被不同的线程调用。
        但是在锁这个例子中，take和release方法将被同一线程调用，因为只允许一个线程来获取信号（允许进入关键区域的信号），
        其它调用take方法获取信号的线程将被阻塞，知道第一个调用take方法的线程调用release方法来释放信号。
        对release方法的调用永远不会被阻塞，这是因为任何一个线程都是先调用take方法，然后再调用release。
         */
    }
}
