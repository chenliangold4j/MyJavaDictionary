package self.liang.concurrent.example.myaqs;

/**
 * 抄代码。aqs的顶级父类。只有get和set 独有线程
 */
public class MyAbstractSynchronizer {

    protected MyAbstractSynchronizer() { }

    private Thread exclusiveOwnerThread;

    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }
    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

}
