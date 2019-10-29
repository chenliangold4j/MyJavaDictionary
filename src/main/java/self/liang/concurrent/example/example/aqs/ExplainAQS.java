package self.liang.concurrent.example.example.aqs;


import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 *
 * AQS为一系列同步器依赖于一个单独的原子变量（state）的同步器提供了一个非常有用的基础。
 * 子类们必须定义改变state变量的protected方法，这些方法定义了state是如何被获取或释放的。
 * 鉴于此，本类中的其他方法执行所有的排队和阻塞机制。子类也可以维护其他的state变量，
 * 但是为了保证同步，必须原子地操作这些变量
 *
 * AbstractQueuedSynchronizer中对state的操作是原子的，且不能被继承。
 * 所有的同步机制的实现均依赖于对改变量的原子操作。
 * 为了实现不同的同步机制，我们需要创建一个非共有的（non-public internal）扩展了AQS类的内部辅助类来实现相应的同步逻辑。
 * AbstractQueuedSynchronizer并不实现任何同步接口，它提供了一些可以被具体实现类直接调用的一些原子操作方法来重写相应的同步逻辑。
 * AQS同时提供了互斥模式（exclusive）和共享模式（shared）两种不同的同步逻辑。
 * 一般情况下，子类只需要根据需求实现其中一种模式，当然也有同时实现两种模式的同步类，如ReadWriteLock。
 *
 *     protected final int getState() {
 *         return state;
 *     }
 *     protected final void setState(int newState) {
 *         state = newState;
 *     }
 *     protected final boolean compareAndSetState(int expect, int update) {
 *         // See below for intrinsics setup to support this
 *         return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
 *     }
 *
 * AQS定义两种资源共享方式：Exclusive（独占，只有一个线程能执行，如ReentrantLock）和Share（共享，多个线程可同时执行，如Semaphore/CountDownLatch）。
 *   不同的自定义同步器争用共享资源的方式也不同。自定义同步器在实现时只需要实现共享资源state的获取与释放方式即可，
 * 至于具体线程等待队列的维护（如获取资源失败入队/唤醒出队等），AQS已经在顶层实现好了。自定义同步器实现时主要实现以下几种方法：
 *
 * isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。
 * tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败则返回false。
 *
 *              tryAcquire尝试以独占的方式获取资源，如果获取成功，则直接返回true，否则直接返回false。
 *              该方法可以用于实现Lock中的tryLock()方法。该方法的默认实现是抛出UnsupportedOperationException，具体实现由自定义的扩展了AQS的同步类来实现。
 *              AQS在这里只负责定义了一个公共的方法框架。这里之所以没有定义成abstract，是因为独占模式下只用实现tryAcquire-tryRelease，
 *              而共享模式下只用实现tryAcquireShared-tryReleaseShared。如果都定义成abstract，那么每个模式也要去实现另一模式下的接口。
 *
 * tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败则返回false。
 * tryAcquireShared(int)：共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
 * tryReleaseShared(int)：共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。
 *
 *
 * 接下来我们开始开始讲解AQS的源码实现。依照acquire-release、acquireShared-releaseShared的次序来。
 * 一）
 *   public final void acquire(int arg)
 *          tryAcquire()尝试直接去获取资源，如果成功则直接返回；
 *          addWaiter()将该线程加入等待队列的尾部，并标记为独占模式；
 *          acquireQueued()使线程在等待队列中获取资源，一直获取到资源后才返回。如果在整个等待过程中被中断过，则返回true，否则返回false。
 *          如果线程在等待过程中被中断过，它是不响应的。只是获取资源后才再进行自我中断selfInterrupt()，将中断补上。
 *
 *   private Node addWaiter(Node mode)
 *          该方法用于将当前线程根据不同的模式（Node.EXCLUSIVE互斥模式、Node.SHARED共享模式）加入到等待队列的队尾，
 *          并返回当前线程所在的结点。如果队列不为空，则以通过compareAndSetTail方法以CAS的方式将当前线程节点加入到等待队列的末尾。
 *          否则，通过enq(node)方法初始化一个等待队列，并返回当前节点
 *
 *   private Node enq(final Node node).
 *          enq(node)用于将当前节点插入等待队列，如果队列为空，则初始化当前队列。整个过程以CAS自旋的方式进行，直到成功加入队尾为止
 *
 *   final boolean acquireQueued(final Node node, int arg)
 *          acquireQueued()用于队列中的线程自旋地以独占且不可中断的方式获取同步状态（acquire），
 *          直到拿到锁之后再返回。该方法的实现分成两部分：
 *          如果当前节点已经成为头结点，尝试获取锁（tryAcquire）成功，然后返回；
 *          否则检查当前节点是否应该被park，然后将该线程park并且检查当前线程是否被可以被中断。
 *
 *   shouldParkAfterFailedAcquire(Node, Node)
 *      shouldParkAfterFailedAcquire方法通过对当前节点的前一个节点的状态进行判断，对当前节点做出不同的操作，至于每个Node的状态表示，可以参考接口文档。
 *
 *   parkAndCheckInterrupt()
 *      该方法让线程去休息，真正进入等待状态。park()会让当前线程进入waiting状态。在此状态下，有两种途径可以唤醒该线程：
 *        1）被unpark()；2）被interrupt()。需要注意的是Thread.interrupted()会清除当前线程的中断标记位。
 *
 * 我们再回到acquireQueued()，总结下该函数的具体流程：
 *
 *          1.结点进入队尾后，检查状态，找到安全休息点；
 *          2.调用park()进入waiting状态，等待unpark()或interrupt()唤醒自己；
 *          3.被唤醒后，看自己是不是有资格能拿到号。如果拿到，head指向当前结点，并返回从入队到拿到号的整个过程中是否被中断过；如果没拿到，继续流程1。
 *
 * 最后，总结一下acquire()的流程：
 *
 *          1.调用自定义同步器的tryAcquire()尝试直接去获取资源，如果成功则直接返回；
 *          2.没成功，则addWaiter()将该线程加入等待队列的尾部，并标记为独占模式；
 *          3.acquireQueued()使线程在等待队列中休息，有机会时（轮到自己，会被unpark()）会去尝试获取资源。获取到资源后才返回。如果在整个等待过程中被中断过，则返回true，否则返回false。
 *          4.如果线程在等待过程中被中断过，它是不响应的。只是获取资源后才再进行自我中断selfInterrupt()，将中断补上。
 *
 *
 * 二）
 *  release(int)   public final boolean release(int arg)
 *      release(int)方法是独占模式下线程释放共享资源的顶层入口。它会释放指定量的资源，如果彻底释放了（即state=0）,
 *      它会唤醒等待队列里的其他线程来获取资源。这也正是unlock()的语义，当然不仅仅只限于unlock()
 *
 *      与acquire()方法中的tryAcquire()类似，tryRelease()方法也是需要独占模式的自定义同步器去实现的。
 *      正常来说，tryRelease()都会成功的，因为这是独占模式，该线程来释放资源，那么它肯定已经拿到独占资源了，直接减掉相应量的资源即可(state-=arg)，
 *      也不需要考虑线程安全的问题。但要注意它的返回值，上面已经提到了，release()是根据tryRelease()的返回值来判断该线程是否已经完成释放掉资源了！
 *      所以自义定同步器在实现时，如果已经彻底释放资源(state=0)，要返回true，否则返回false。
 *
 *      unparkSuccessor(Node)方法用于唤醒等待队列中下一个线程。
 *      这里要注意的是，下一个线程并不一定是当前节点的next节点，而是下一个可以用来唤醒的线程，如果这个节点存在，调用unpark()方法唤醒。
 *    总之，release()是独占模式下线程释放共享资源的顶层入口。它会释放指定量的资源，如果彻底释放了（即state=0）,它会唤醒等待队列里的其他线程来获取资源
 *
 *
 * 三）
 *  acquireShared(int)  public final void acquireShared(int arg)
 *      acquireShared(int)方法是共享模式下线程获取共享资源的顶层入口。它会获取指定量的资源，获取成功则直接返回，获取失败则进入等待队列，直到获取到资源为止，整个过程忽略中断
 *
 *      跟独占模式比，还有一点需要注意的是，这里只有线程是head.next时（“老二”），
 *      才会去尝试获取资源，有剩余的话还会唤醒之后的队友。
 *      那么问题就来了，假如老大用完后释放了5个资源，而老二需要6个，老三需要1个，老四需要2个。
 *      老大先唤醒老二，老二一看资源不够，他是把资源让给老三呢，还是不让？
 *      答案是否定的！老二会继续park()等待其他线程释放资源，也更不会去唤醒老三和老四了。
 *      独占模式，同一时刻只有一个线程去执行，这样做未尝不可；
 *      但共享模式下，多个线程是可以同时执行的，现在因为老二的资源需求量大，而把后面量小的老三和老四也都卡住了。
 *      当然，这并不是问题，只是AQS保证严格按照入队顺序唤醒罢了（保证公平，但降低了并发）
 *
 *      此方法在setHead()的基础上多了一步，就是自己苏醒的同时，如果条件符合（比如还有剩余资源），还会去唤醒后继结点，毕竟是共享模式！
 *      至此，acquireShared()也要告一段落了。
 *
 *      让我们再梳理一下它的流程：
 *
 *          1）tryAcquireShared()尝试获取资源，成功则直接返回；
 *          2）失败则通过doAcquireShared()进入等待队列park()，直到被unpark()/interrupt()并成功获取到资源才返回。整个等待过程也是忽略中断的。
 *
 * 四）
 *   releaseShared(int)   public final boolean releaseShared(int arg)
 *
 *          此方法的流程也比较简单，一句话：释放掉资源后，唤醒后继。跟独占模式下的release()相似，但有一点稍微需要注意：
 *          独占模式下的tryRelease()在完全释放掉资源（state=0）后，才会返回true去唤醒其他线程，这主要是基于独占下可重入的考量；
 *          而共享模式下的releaseShared()则没有这种要求，共享模式实质就是控制一定量的线程并发执行，
 *          那么拥有资源的线程在释放掉部分资源时就可以唤醒后继等待结点
 *
 *
 *
 *
 */


public class ExplainAQS extends AbstractQueuedSynchronizer {



}
