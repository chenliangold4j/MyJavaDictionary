package self.liang.concurrent.example.unsafe;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * a. LockSupport.park()对应Unsafe的Unsafe.park(false, 0L)------>给当前所在线程加锁,第一个参数表示true为精度型单位为纳秒,false单位毫秒,第二次参数表示等待时间;
 *
 * b. LockSupport.park.unpark --------->Thread thread对应Unsafe的UNSAFE.unpark(thread)方法(解锁指定线程)
 * 如果,我们直接使用Unsafe,是这样子的:
 *
 */
public class UnsafeExample3 {
    public static void main(String[] args)  throws IllegalAccessException, NoSuchFieldException, IOException, InterruptedException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);

        Unsafe unsafe = (Unsafe) f.get(null);

        Thread thread=new Thread(
                ()->{
                    unsafe.park(false,0l);
                    System.out.println("线程一执行");
                }
        );
        thread.start();

        Thread thread2=new Thread(
                ()->{
                    unsafe.park(false,0l);
                    System.out.println("线程二执行");
                }
        );
        thread2.start();
        TimeUnit.SECONDS.sleep(2);
        unsafe.unpark(thread2);
        unsafe.unpark(thread);
//        System.in.read();
        TimeUnit.SECONDS.sleep(2);
    }
}
