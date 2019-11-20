package self.liang.concurrent.example.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLHLock {
    /**
     * CLH锁节点状态 - 每个希望获取锁的线程都被封装为一个节点对象
     */
    private static class CLHNode {

        /**
         * 默认状态为true - 即处于等待状态或者加锁成功(换言之，即此节点处于有效的一种状态)
         */
        volatile boolean active = true;

    }

    /**
     * 隐式链表最末等待节点
     */
    private volatile CLHNode tail = null;

    /**
     * 线程对应CLH节点映射
     */
    private ThreadLocal<CLHNode> currentThreadNode = new ThreadLocal<>();

    /**
     * 原子更新器
     */
    private static final AtomicReferenceFieldUpdater UPDATER = AtomicReferenceFieldUpdater
            .newUpdater(
                    CLHLock.class,
                    CLHNode.class,
                    "tail");

    /**
     * CLH加锁
     */
    public void lock() {

        //新建一个节点
        CLHNode cNode = currentThreadNode.get();

        if (cNode == null) {
            cNode = new CLHNode();
            currentThreadNode.set(cNode);
        }

        // 通过这个操作完成隐式链表的维护，后继节点只需要在前驱节点的locked状态上自旋
        //将前驱节点get到，然后set进现在的节点。那么下一个人调用就会等待现在节点。
        CLHNode predecessor = (CLHNode) UPDATER.getAndSet(this, cNode);//这里是队列对尾部进行操作。
        if (predecessor != null) {
            // 自旋等待前驱节点状态变更 - unlock中进行变更
            while (predecessor.active) {  //后继节点自旋检查前驱的状态
                //如果前驱的活动状态为false  就得到锁。
            }
        }
        // 没有前驱节点表示可以直接获取到锁，由于默认获取锁状态为true，此时可以什么操作都不执行
        // 能够执行到这里表示已经成功获取到了锁
    }

    /**
     * CLH释放锁
     */
    public void unlock() {

        CLHNode cNode = currentThreadNode.get();

        // 只有持有锁的线程才能够释放
        if (cNode == null || !cNode.active) {
            return;
        }

        // 从映射关系中移除当前线程对应的节点
        currentThreadNode.remove();

        // 尝试将tail从currentThread变更为null，因此当tail不为currentThread时表示还有线程在等待加锁
        if (!UPDATER.compareAndSet(this, cNode, null)) {
            // 不仅只有当前线程，还有后续节点线程的情况 - 将当前线程的锁状态置为false，因此其后继节点的lock自旋操作可以退出
            cNode.active = false;
        }

    }

    /**
     * 用例
     *
     * @param args
     */
    public static void main(String[] args) {

        final CLHLock lock = new CLHLock();

        for (int i = 1; i <= 10; i++) {
            new Thread(generateTask(lock, String.valueOf(i))).start();
        }

    }

    private static Runnable generateTask(final CLHLock lock, final String taskId) {
        return () -> {
            lock.lock();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }

            System.out.println(String.format("Thread %s Completed", taskId));
            lock.unlock();
        };
    }
}

/**
 * 另一篇文章的实现。
 * 这里用myPre保存了前驱。
 *
 * 结点之间是通过隐形的链表相连，之所以叫隐形的链表是因为这些结点之间没有明显的next指针，而是通过myPred所指向的结点的变化情况来影响myNode的行为
 * 。CLHLock上还有一个尾指针，始终指向队列的最后一个结点
 *
 */
class CLHLock2 implements Lock {

    class QNode {
        public volatile boolean locked = false;
    }

    AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public CLHLock2() {
        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);//将tail取出，作为当前线程的前驱
        myPred.set(pred);
        while (pred.locked) {

        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
