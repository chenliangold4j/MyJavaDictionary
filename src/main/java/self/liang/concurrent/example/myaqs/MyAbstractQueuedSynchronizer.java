package self.liang.concurrent.example.myaqs;

import sun.misc.Unsafe;

import java.util.concurrent.locks.LockSupport;

/**
 * 抄代码，理解aqs
 */
public class MyAbstractQueuedSynchronizer extends  MyAbstractSynchronizer {

    protected MyAbstractQueuedSynchronizer() { }

    static final class Node {
        /** 指示节点在共享模式下等待的标记 */
        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = null;
        /** 表示线程已取消的等待状态值 */
        static final int CANCELLED =  1;
        /** 等待状态值，指示后续线程需要取消停靠 */
        static final int SIGNAL    = -1;
        /** 表示线程处于等待状态 */
        static final int CONDITION = -2;
        /**
         共享模式下，前继结点不仅会唤醒其后继结点，
         同时也可能会唤醒后继的后继结点
         propagate:传播
         */
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        //这种就是典型的双向链表
        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;

        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        //predecessor :n. 前任，前辈    返回节点的前节点
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }
    /*头节点*/
    private transient volatile Node head;
    /*尾巴节点*/
    private transient volatile Node tail;

    //这个状态？
    private volatile int state;
    protected final int getState() {
        return state;
    }
    protected final void setState(int newState) {
        state = newState;
    }

    // 剩余时间小于这个阈值的时候，就
    // 不要进行挂起了，自旋的性能会比较好
    static final long spinForTimeoutThreshold = 1000L;

    //将节点插入队列，必要时进行初始化
    //插入操作利用unsafe的cas变为原子操作。
    //在链表尾部添加了一个节点。
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // 如果尾巴节点没有值。Must initialize
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;//将尾节点设置为新节点的前驱，并用cas更换尾节点。
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }

//    为当前线程和给定模式创建和排队节点
    /**  新建节点，设置了节点的nextWaiter和thread
     *   并把节点插入链表尾部。
     *
     *   Node的共享判断。
     *    final boolean isShared() {
     *             return nextWaiter == SHARED;
     *         }
     *
     */
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }//
        enq(node);
        return node;
    }

    //设置头节点。并将node的前驱和thread设置为空。
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }
    /**
     * 变量waitStatus则表示当前Node结点的等待状态，
     * 共有5种取值CANCELLED、SIGNAL、CONDITION、PROPAGATE、0。
     * CANCELLED(1)：表示当前结点已取消调度。当timeout或被中断（响应中断的情况下），
     * 会触发变更为此状态，进入该状态后的结点将不会再变化。
     *
     * SIGNAL(-1)：表示后继结点在等待当前结点唤醒。后继结点入队时，
     * 会将前继结点的状态更新为SIGNAL。
     *
     * CONDITION(-2)：表示结点等待在Condition上，
     * 当其他线程调用了Condition的signal()方法后，
     * CONDITION状态的结点将从等待队列转移到同步队列中，等待获取同步锁。
     *
     * PROPAGATE(-3)：共享模式下，前继结点不仅会唤醒其后继结点，
     * 同时也可能会唤醒后继的后继结点。
     *
     * 0：新结点入队时的默认状态。
     *
     * 唤醒node之后最近的可以唤醒的节点。
     */
    private void unparkSuccessor(Node node) {
        // unparkSuccessor()来唤醒该线程的继任节点。
        // 在unparkSuccessor()方法中通过LockSupport.unpark()来唤醒。
        // unpark():如果给定线程的许可尚不可用，则使其可用

        //判断waitStatus是否小于0，不是则置为0
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0);

        Node s = node.next;
        //2 这里其实就是说。节点的后继为空或者节点的后继已取消调度
        if (s == null || s.waitStatus > 0) {
            s = null;
            //这时候，从后向前寻找  离node最近的可以唤醒的节点。
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        //用unpard唤醒节点。
        if (s != null)
            LockSupport.unpark(s.thread);
    }

    /**
     *     此方法主要用于唤醒后继，头结点的后继。
     *     判断头节点的状态，-1(SIGNAL)则唤醒后继。
     *     ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE) 状态为0 且
     */
    private void doReleaseShared() {
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                //当后继节点等待唤醒
                if (ws == Node.SIGNAL) {
                    //不停的尝试更新状态，更新成功则唤醒后继。
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                        continue;            // loop to recheck cases
                    unparkSuccessor(h);
                }
                //如果是0 则改为-3 具体-3作用还不知道
                else if (ws == 0 &&
                        !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed
                break;
        }
    }

    /**
     * 设置头节点。
      如果条件符合（比如还有剩余资源），还会去唤醒后继结点
     */
    private void setHeadAndPropagate(Node node, int propagate) {
        //1.将头节点设置为node
        Node h = head; // Record old head for check below
        setHead(node);
        //根据propagate的状态，和头结点waitStatus的状态，这么写很怪。。可能是为了双层校验？
        if (propagate > 0 || h == null || h.waitStatus < 0 || (h = head) == null || h.waitStatus < 0) {
            //node已经设置成了头，这里看头节点的后继，看是不是共享。然后调用doReleaseShared
            Node s = node.next;
            if (s == null || s.isShared())
                doReleaseShared();
        }
    }

    /**
     * 取消正在进行的获取尝试
     */
    private void cancelAcquire(Node node) {
        // Ignore if node doesn't exist
        if (node == null)
            return;
        node.thread = null;
        // Skip cancelled predecessors  跳过取消的前驱。waitStatus>0就是cancelled；
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        //这时候predNext节点就是 最近没有取消的前驱的后继。
        Node predNext = pred.next;

        //把node的状态设置为取消。
        node.waitStatus = Node.CANCELLED;


        //设置为取消。那么下列就是善后工作

        //如果node是尾节点  把pred设置为尾巴，因为pred是没有取消的最后一个节点。
        //之后再把pred的next置为null;
        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            //如果node不是尾节点 pred不是头节点   说明是中间节点。
            // 且 (pred.waitStatus不是SIGNAL 或 waitStatus小于0且将waitStatus切换为SIGNAL成功。 要不是SIGNAl 要么同样小于0然后切换成SIGNAL
            // 且pred的线程不是空。
            //最后将pred的后继切换成node.next节点。
            //一句话，将node不是尾，将node的后继设置为pred的后继。为了取消node节点。
            int ws;
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL || (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);
            } else {
                //不是上列的情况。唤醒node之后最近的可以唤醒的节点
                //思考的话，就是node是当前应该unpark的节点，那么unpark node的后继。
                unparkSuccessor(node);
            }
            node.next = node; // help GC
        }
    }

    /**
     此方法主要用于检查状态，
     看看自己是否真的可以去休息了（进入waiting状态，
     如果线程状态转换不熟，可以参考本人上一篇写的Thread详解），
     万一队列前边的线程都放弃了只是瞎站着，那也说不定，对吧

     整个流程中，如果前驱结点的状态不是SIGNAL，
     那么自己就不能安心去休息，需要去找个安心的休息点，
     同时可以再尝试下看有没有机会轮到自己拿号
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            return true;
        if (ws > 0) {
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    /**
     * 使线程阻塞在等待队列中获取资源，一直获取到资源后才返回。
     * 如果在整个等待过程中被中断过，则返回true，否则返回false
     *
     * 在等待队列中排队拿号（中间没其它事干可以休息），直到拿到号后再返回
     */
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     以互斥中断模式获取
     */
    private void doAcquireInterruptibly(int arg)
            throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     *  以独占时间模式获取
     */
    private boolean doAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return true;
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 以共享不可中断模式获取
     */
    private void doAcquireShared(int arg) {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 以共享中断模式获取
     */
    private void doAcquireSharedInterruptibly(int arg)
            throws InterruptedException {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }


    /**
     * 以共享时间模式获取
     */
    private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return true;
                    }
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 以独占模式获取，忽略中断。
     * 通过至少调用一次tryAcquire来实现，成功后返回。
     * 否则线程将排队，
     * 可能会重复阻塞和取消阻塞，调用tryAcquire直到成功。
     * 此方法可用于实现Lock.lock方法
     */
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
                acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
    /**
     * 以独占模式获取，如果中断则中止。
     * 首先检查中断状态，然后至少调用一次tryAcquire，
     * 成功后返回。否则，线程将排队，可能会重复阻塞和取消阻塞，调用tryAcquire，
     * 直到成功或线程被中断。此方法可用于实现Lock.lockInterruptibly(可中断地)方法
     */
    public final void acquireInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (!tryAcquire(arg))
            doAcquireInterruptibly(arg);
    }

    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    /**
     * 这里运用unsafe类
     * 获取field的偏移，等待用unsafe的原子操作进行替换。
     *
     * */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;
    static {
        try {
            /*objectFieldOffset()方法用于获取某个字段相对Java对象的“起始地址”的偏移量，
            也提供了getInt、getLong、getObject之类的方法可以使用前面获取的偏移量
            来访问某个Java对象的某个字段。*/
            stateOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (Node.class.getDeclaredField("next"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     *以下都是对上面的记录的地址做cas替换
     */
    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                expect, update);
    }
    private static final boolean compareAndSetNext(Node node,
                                                   Node expect,
                                                   Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }
}
