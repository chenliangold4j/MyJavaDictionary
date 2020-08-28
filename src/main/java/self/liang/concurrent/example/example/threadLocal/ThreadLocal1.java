package self.liang.concurrent.example.example.threadLocal;

public class ThreadLocal1 {

    /**
     * 每个Thread里面都有一个ThreadLocal.ThreadLocalMap成员变量，也就是说每个线程通过ThreadLocal.ThreadLocalMap与ThreadLocal相绑定，
     * 这样可以确保每个线程访问到的thread-local variable都是本线程的。
     *
     * 获取了ThreadLocalMap实例以后，如果它不为空则调用ThreadLocalMap.ThreadLocalMap 的set方法设值；
     * 若为空则调用ThreadLocal 的createMap方法new一个ThreadLocalMap实例并赋给Thread.threadLocals。
     *
     * @param args
     */
    public static void main(String[] args) {
        ThreadLocal test;
    }

}
