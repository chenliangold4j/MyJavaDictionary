package self.liang.concurrent.example.lock;

/**
 *    Java中的synchronized同步块是可重入的。
 *    这意味着如果一个java线程进入了代码中的synchronized同步块，
 *    并因此获得了该同步块使用的同步对象对应的管程上的锁
 *    ，那么这个线程可以进入由同一个管程对象所同步的另一个java代码块。下面是一个例子：
 */
public class Reentrant {
    public synchronized void  outer(){
//        inner();
    }
    public synchronized void inner(){
        //do something
    }

}
