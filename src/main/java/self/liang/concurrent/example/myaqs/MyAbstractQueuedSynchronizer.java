package self.liang.concurrent.example.myaqs;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

/**
 * 抄代码，理解aqs
 */
public class MyAbstractQueuedSynchronizer extends MyAbstractSynchronizer {

    protected MyAbstractQueuedSynchronizer() {
    }

    static final class Node {
        /**
         * 指示节点在共享模式下等待的标记
         */
        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = null;
        /**
         * 表示线程已取消的等待状态值
         */
        static final int CANCELLED = 1;
        /**
         * 等待状态值，指示后续线程需要取消停靠
         */
        static final int SIGNAL = -1;
        /**
         * 表示线程处于等待状态
         */
        static final int CONDITION = -2;
        /**
         * 共享模式下，前继结点不仅会唤醒其后继结点，
         * 同时也可能会唤醒后继的后继结点
         * propagate:传播
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
        for (; ; ) {
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

    /**
     * 新建节点，设置了节点的nextWaiter和thread
     * 并把节点插入链表尾部。
     * <p>
     * Node的共享判断。
     * final boolean isShared() {
     * return nextWaiter == SHARED;
     * }
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
     * <p>
     * SIGNAL(-1)：表示后继结点在等待当前结点唤醒。后继结点入队时，
     * 会将前继结点的状态更新为SIGNAL。
     * <p>
     * CONDITION(-2)：表示结点等待在Condition上，
     * 当其他线程调用了Condition的signal()方法后，
     * CONDITION状态的结点将从等待队列转移到同步队列中，等待获取同步锁。
     * <p>
     * PROPAGATE(-3)：共享模式下，前继结点不仅会唤醒其后继结点，
     * 同时也可能会唤醒后继的后继结点。
     * <p>
     * 0：新结点入队时的默认状态。
     * <p>
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
     * 此方法主要用于唤醒后继，头结点的后继。
     * 判断头节点的状态，-1(SIGNAL)则唤醒后继。
     * ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE) 状态为0 且
     */
    private void doReleaseShared() {
        for (; ; ) {
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
     * 如果条件符合（比如还有剩余资源），还会去唤醒后继结点
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
     * 总结：
     * 取消 node.waitStatus = Node.CANCELLED;
     * 然后更新链表。改顺序或者unpark要执行的node
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
     * 看看自己是否真的可以去休息了（进入waiting状态，）
     * 如果前驱节点的状态为SIGNAL 则为true
     * <p>
     * 如果前驱已经被取消了 跳过前任并指示重试。
     * 如果前驱没有被取消   吧前驱的状态设置为SIGNAL
     * 返回false
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

    //中断当前线程
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    //将当前线程park，并重置中断
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    /**
     * 使线程阻塞在等待队列中获取资源，一直获取到资源后才返回。
     * 如果在整个等待过程中被中断过，则返回true，否则返回false
     * <p>
     * 在等待队列中排队拿号（中间没其它事干可以休息），直到拿到号后再返回
     */
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
                //前驱是头节点，子类判断可以获取。
                //则将头节点设置为node，吧前驱删除，
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                //不能获取，则看自己是否应该停止并休息  如果可以休息， 将当前线程park，并重置中断
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            //最后，如果失败。取消请求
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 以互斥中断模式获取
     */
    private void doAcquireInterruptibly(int arg)
            throws InterruptedException {
        //  首先在尾部插入独占模式的Node节点。然后和acquireQueued几乎一样的操作，
        //  这样高并发的情况下大概率会使得线程park，低并发，很可能会获取成功然后返回。
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (; ; ) {
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
     * 以独占时间模式获取
     * 记录时间，如果获取超时则返回false
     */
    private boolean doAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (; ; ) {
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
     * 和独占的不同这里调用tryAcquireShared来获取
     * setHeadAndPropagate来设置头
     */
    private void doAcquireShared(int arg) {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
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
            for (; ; ) {
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
            for (; ; ) {
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
     * <p>
     * 否则在等待队列中排队拿号
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

    /**
     * 尝试以独占模式获取，如果中断将中止，如果给定超时超时将失败
     * tryAcquire由子类实现
     */
    public final boolean tryAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) ||
                doAcquireNanos(arg, nanosTimeout);
    }


    /**
     * 以独占模式发布。如果tryRelease返回true，则通过unpark一个或多个线程的阻塞来实现
     * tryRelease由子类实现
     */
    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }

    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }

    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquireShared(arg) >= 0 ||
                doAcquireSharedNanos(arg, nanosTimeout);
    }

    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }

    //查询是否有线程正在等待获取，注意，由于中断和超时导致的取消可能随时发生，一个返回不保证由任何其他线程将会获取。
    public final boolean hasQueuedThreads() {
        return head != tail;
    }

    //查询是否有任何线程争用过此同步器;也就是说，是否由一个acquire方法曾经被阻塞
    // 为什么head 不为null就能证明？
    //  有竞争就会入队列此时head不为null，但是任务执行完了呢？
    //  通过上面的代码知道，head是由队列里刚获得到锁的线程设置的
    //  （把自己设置成head），即使任务执行完也不会修改head，只能由下个入队的线程设置，这样head就永远不会为空了。
    public final boolean hasContended() {
        return head != null;
    }

    public final Thread getFirstQueuedThread() {
        // handle only fast path, else relay
        return (head == tail) ? null : fullGetFirstQueuedThread();
    }


    private Thread fullGetFirstQueuedThread() {

        Node h, s;
        Thread st;
        /**
         *   // 一般来说这个线程就是head->next，需要保证在并发情况下读一致性：
         *         // 1. (h = head) != null
         *         // 2. (s = h.next) != null
         *         // 3. s.prev = head
         *         // 4. (st = s.thread) != null
         *         // 假设2、3中间被并发的插入了一个setHead方法，执行3时发现s.prev为空了
         *         // 因此，这里需要再试一次（第二次其实也有可能会失败，不过概率已经很小
         *         // 了，就像连续被雷劈两次一样;即使第二次也失败了，还有后面最后一道安
         *         // 全措施，从tail开始向前遍历寻找）
         */
        if (((h = head) != null && (s = h.next) != null &&
                s.prev == head && (st = s.thread) != null) ||
                ((h = head) != null && (s = h.next) != null &&
                        s.prev == head && (st = s.thread) != null))
            return st;

        /**
         *  // head.next也有可能被并发修改，比如上面的1、2中间插入了一个acquireQueued
         * // 方法，执行完setHeader方法后将head.next置为null，这样条件2就不成立了，
         *  // 因此，就进入下面的最后一道程序了
         *  从头寻找失败就从尾部开始寻找
         */
        Node t = tail;
        Thread firstThread = null;
        while (t != null && t != head) {
            Thread tt = t.thread;
            if (tt != null)
                firstThread = tt;
            t = t.prev;
        }
        return firstThread;
    }

    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
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

    //找到某个thread是否在链表里
    public final boolean isQueued(Thread thread) {
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }

    //队列里第一个等待的结点是否是独占模式
    final boolean apparentlyFirstQueuedIsExclusive() {
        Node h, s;
        return (h = head) != null &&
                (s = h.next) != null &&
                !s.isShared() &&
                s.thread != null;
    }

    //查询是否有任何线程等待获取的时间比当前线程长
    public final boolean hasQueuedPredecessors() {
        // The correctness of this depends on head being initialized
        // before tail and on head.next being accurate if the current
        // thread is first in queue.
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s;
        return h != t &&
                ((s = h.next) == null || s.thread != Thread.currentThread());
    }

    //队列长度
    public final int getQueueLength() {
        int n = 0;
        for (Node p = tail; p != null; p = p.prev) {
            if (p.thread != null)
                ++n;
        }
        return n;
    }

    //    返回一个 collection，它包含可能正等待获取此锁的线程
    public final Collection<Thread> getQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }

    //返回包含可能正以独占模式等待获取的线程 collection
    public final Collection<Thread> getExclusiveQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (!p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    //返回包含可能正以共享模式等待获取的线程 collection
    public final Collection<Thread> getSharedQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public String toString() {
        int s = getState();
        String q = hasQueuedThreads() ? "non" : "";
        return super.toString() +
                "[State = " + s + ", " + q + "empty queue]";
    }

    //判断该节点是否在队列中
    final boolean isOnSyncQueue(Node node) {
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        if (node.next != null) // If has successor, it must be on queue
            return true;

        return findNodeFromTail(node);
    }

    private boolean findNodeFromTail(Node node) {
        Node t = tail;
        for (; ; ) {
            if (t == node)
                return true;
            if (t == null)
                return false;
            t = t.prev;
        }
    }

    //将一个节点从condition 状态变为SIGNAL 状态
    final boolean transferForSignal(Node node) {
        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
            return false;
        Node p = enq(node);
        int ws = p.waitStatus;
        if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
            LockSupport.unpark(node.thread);
        return true;
    }

    //函数表明在signal之前被中断还是之后被中断，依据就是node.waitStatus域的值，
    //如果此时该域的值还是CONDITION，说明还没有被唤醒；否则，肯定就被唤醒过了
    final boolean transferAfterCancelledWait(Node node) {
        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
            enq(node);
            return true;
        }
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }

    //fullyRelease先获取state的值，把它作为参数调用AQS的release方法，并返回state。
    // 如果release失败或抛异常，则取消node，抛出异常。
    //state相当于锁的数量，这里相当于释放所有锁
    final int fullyRelease(Node node) {
        boolean failed = true;
        try {
            int savedState = getState();
            if (release(savedState)) {
                failed = false;
                return savedState;
            } else {
                throw new IllegalMonitorStateException();
            }
        } finally {
            if (failed)
                node.waitStatus = Node.CANCELLED;
        }
    }

    public final boolean owns(ConditionObject condition) {
        return condition.isOwnedBy(this);
    }

    public final boolean hasWaiters(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.hasWaiters();
    }

    //返回等待与此锁定相关的给定条件condition的线程估计数
    public final int getWaitQueueLength(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    //返回一个 collection，该 collection 包含那些正在与此同步器关联的给定条件上等待的线程。
    public final Collection<Thread> getWaitingThreads(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }


    //在排它模式下，状态是否被占用。
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    public class ConditionObject implements Condition, java.io.Serializable {
        private static final long serialVersionUID = 1173984872572414699L;
        /**
         * First node of condition queue.
         */ //Condition队列的头指针
        private transient Node firstWaiter;
        /**
         * Last node of condition queue.
         *///Condition队列的尾指针
        private transient Node lastWaiter;

        /**
         * Creates a new {@code ConditionObject} instance.
         */
        public ConditionObject() {
        }

        // Internal methods

        /**
         * Adds a new waiter to wait queue.
         *
         * @return its new wait node
         */
        private Node addConditionWaiter() {
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
            if (t != null && t.waitStatus != Node.CONDITION) {
                unlinkCancelledWaiters();// 如果尾节点被取消了,清理掉
                t = lastWaiter;
            }
            // 新建一个Condition状态的节点,并将其加在尾部
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

        //真正的释放信号(唤醒)
        private void doSignal(Node first) {
            do { //将first的nextWaiter设为null
                if ((firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
                // 如果转换队列不成功且等待队列不为null,继续do
            } while (!transferForSignal(first) &&
                    (first = firstWaiter) != null);
        }

        /**
         * 遍历所有节点,使其加入到Sync队列
         */
        private void doSignalAll(Node first) {
            lastWaiter = firstWaiter = null;
            do {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            } while (first != null);
        }

        // 删除Condition队列中被cancel的节点
        private void unlinkCancelledWaiters() {
            Node t = firstWaiter;
            Node trail = null;
            while (t != null) {
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION) {
                    t.nextWaiter = null;
                    if (trail == null)
                        firstWaiter = next;
                    else
                        trail.nextWaiter = next;
                    if (next == null)
                        lastWaiter = trail;
                } else
                    trail = t;
                t = next;
            }
        }

        //唤醒Condition队列的头节点持有的线程
        public final void signal() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }

        //唤醒Condition队列的所有等待线程
        public final void signalAll() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        //线程在等待的时候，线程若主动抛出异常，则相对应的程序也不会抛出异常。
        public final void awaitUninterruptibly() {
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean interrupted = false;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            if (acquireQueued(node, savedState) || interrupted)
                selfInterrupt();
        }

        /**
         * Mode meaning to reinterrupt on exit from wait  表示发生了中断
         */
        private static final int REINTERRUPT = 1;
        /**
         * Mode meaning to throw InterruptedException on exit from wait  在await状态下发生中断
         */
        private static final int THROW_IE = -1;


        private int checkInterruptWhileWaiting(Node node) {
            return Thread.interrupted() ?
                    (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
                    0;
        }

        private void reportInterruptAfterWait(int interruptMode)
                throws InterruptedException {
            if (interruptMode == THROW_IE)
                throw new InterruptedException();
            else if (interruptMode == REINTERRUPT)
                selfInterrupt();
        }

        // 可以响应线程中断命令
        public final void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();//当前线程放入Condition队列的尾
            int savedState = fullyRelease(node);//释放当前线程持有的锁
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {//循环检测线程的状态
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        //    限时的在条件变量上等待
        public final long awaitNanos(long nanosTimeout)
                throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return deadline - System.nanoTime();
        }

        //指定结束时刻的在条件变量上等待
        public final boolean awaitUntil(Date deadline)
                throws InterruptedException {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (System.currentTimeMillis() > abstime) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }


        public final boolean await(long time, TimeUnit unit)
                throws InterruptedException {
            long nanosTimeout = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        //  support for instrumentation


        final boolean isOwnedBy(MyAbstractQueuedSynchronizer sync) {
            return sync == MyAbstractQueuedSynchronizer.this;
        }

        //根据Condition条件去查询是否有线程正在等待获取此锁
        protected final boolean hasWaiters() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        //返回等待与此锁定相关的给定条件condition的线程估计数
        protected final int getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        //返回一个 collection，该 collection 包含那些正在与此同步器关联的给定条件上等待的线程。
        protected final Collection<Thread> getWaitingThreads() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Thread>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION) {
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }


    /**
     * 这里运用unsafe类
     * 获取field的偏移，等待用unsafe的原子操作进行替换。
     */
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
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    /**
     * 以下都是对上面的记录的地址做cas替换
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
