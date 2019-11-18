package self.liang.concurrent.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeExample5 {
    private long address = 0;

    private Unsafe unsafe = null;

    {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public UnsafeExample5()
    {
        address = unsafe.allocateMemory(2 * 1024 * 1024);
    }

    // Exception in thread "main" java.lang.OutOfMemoryError
    /**
     * 这段代码会抛出OutOfMemoryError。这是因为UnsafeExample5对象是在堆内存中分配的，
     * 当该对象被垃圾回收的时候，并不会释放堆外内存，因为使用Unsafe获取的堆外内存，
     * 必须由程序显示的释放，JVM不会帮助我们做这件事情。由此可见，使用Unsafe是有风险的，很容易导致内存泄露。
     *
     * 也就说，这样创建的对象内存不会被垃圾回收。
     */
    public static void main(String[] args)
    {
        while (true)
        {
            UnsafeExample5 heap = new UnsafeExample5();
            System.out.println("memory address=" + heap.address);
        }
    }
}
