package self.liang.concurrent.example.myaqs;

import sun.misc.Unsafe;

/**
 * 抄代码，理解aqs
 */
public class MyAbstractQueuedSynchronizer extends  MyAbstractSynchronizer {

    protected MyAbstractQueuedSynchronizer() { }

    static final class Node {
        /** 指示节点在共享模式下等待的标记 */
        static final Node SHARED = new Node();
        /**指示节点正在排他模式中等待的标记 */
        static final Node EXCLUSIVE = null;
        /** 表示线程已取消的等待状态值 */
        static final int CANCELLED =  1;
        /** 等待状态值，指示后续线程需要取消停靠 */
        static final int SIGNAL    = -1;
        /** 表示线程处于等待状态 */
        static final int CONDITION = -2;
        /**
         表示下一个被默认对象的等待状态值应该无条件传播
         */
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        //这种就是典型的双向链表
        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;

        //是不是共享的。如果是共享，按代码来说，只能传入Node.SHARED这个节点.
        final boolean isShared() {
            return nextWaiter == SHARED;
        }

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

    private volatile int state;
    protected final int getState() {
        return state;
    }
    protected final void setState(int newState) {
        state = newState;
    }


    /*这里运用unsafe类*/
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


    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }


}
