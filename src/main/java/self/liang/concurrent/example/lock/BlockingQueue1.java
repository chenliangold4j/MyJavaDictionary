package self.liang.concurrent.example.lock;

import java.util.LinkedList;
import java.util.List;

/**
 * 阻塞队列与普通队列的区别在于，当队列是空的时，从队列中获取元素的操作将会被阻塞，或者当队列是满时，往队列里添加元素的操作会被阻塞。
 * 试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程往空的队列插入新的元素。
 * 同样，试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其他的线程使队列重新变得空闲起来，
 * 如从队列中移除一个或者多个元素，或者完全清空队列
 *
 *
 * 线程池里面的线程就可以用 阻塞队列来获取任务。队列为空就阻塞。
 */
public class BlockingQueue1 {

    private List queue = new LinkedList();
    private int  limit = 10;
    public BlockingQueue1(int limit){
        this.limit = limit;
    }
    public synchronized void enqueue(Object item)
            throws InterruptedException  {
        //满了就阻塞
        while(this.queue.size() == this.limit) {
            wait();
        }
        //长度为0就唤醒 ，因为当这里没有元素的时候，dequeue是阻塞状态。
        if(this.queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(item);
    }
    public synchronized Object dequeue()
            throws InterruptedException{
        //没有元素，则阻塞
        while(this.queue.size() == 0){
            wait();
        }
        //满了，就唤醒 。
        if(this.queue.size() == this.limit){
            notifyAll();
        }
        //把第一个出队
        return this.queue.remove(0);
    }

}