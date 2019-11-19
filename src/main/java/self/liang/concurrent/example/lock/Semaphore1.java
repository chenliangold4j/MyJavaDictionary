package self.liang.concurrent.example.lock;

/**
 * 简单的信号量
 * <p>
 * Take方法发出一个被存放在Semaphore内部的信号，而Release方法则等待一个信号，当其接收到信号后，标记位signal被清空，然后该方法终止
 * <p>
 * 使用这个semaphore可以避免错失某些信号通知。用take方法来代替notify，release方法来代替wait。
 * 如果某线程在调用release等待之前调用take方法，
 * 那么调用release方法的线程仍然知道take方法已经被某个线程调用过了，
 * 因为该Semaphore内部保存了take方法发出的信号。而wait和notify方法就没有这样的功能。
 */
public class Semaphore1 {
    private boolean signal = false;

    public synchronized void take() {
        this.signal = true;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        while (!this.signal) wait();
        this.signal = false;
    }

    public static void main(String[] args) {
        Semaphore1 semaphore = new Semaphore1();
        SendingThread sender = new SendingThread(semaphore);
        RecevingThread receiver = new RecevingThread(semaphore);
        receiver.start();
        sender.start();
    }
}
class SendingThread  extends  Thread{
    Semaphore1 semaphore = null;

    public SendingThread(Semaphore1 semaphore) {
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
//do something, then signal
            this.semaphore.take();
        }
    }
}

class RecevingThread  extends  Thread {
    Semaphore1 semaphore = null;

    public RecevingThread(Semaphore1 semaphore) {
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            try {
                this.semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //receive signal, then do something...
        }


    }
}