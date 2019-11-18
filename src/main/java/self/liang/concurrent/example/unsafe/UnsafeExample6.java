package self.liang.concurrent.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class UnsafeExample6 {
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

    // 让对象占用堆内存,触发[Full GC
    private byte[] bytes = null;

    public UnsafeExample6()
    {
        address = unsafe.allocateMemory(2 * 1024 * 1024);
        bytes = new byte[1024 * 1024];
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        System.out.println("finalize." + bytes.length);
        unsafe.freeMemory(address);
    }

    /**
     * 我们覆盖了finalize方法，手动释放分配的堆外内存。如果堆中的对象被回收，那么相应的也会释放占用的堆外内存。这里有一点需要注意下：
     *
     * // 让对象占用堆内存,触发[Full GC
     * private byte[] bytes = null;
     *
     * 这行代码主要目的是为了触发堆内存的垃圾回收行为，顺带执行对象的finalize释放堆外内存。
     * 如果没有这行代码或者是分配的字节数组比较小，程序运行一段时间后还是会报OutOfMemoryError。
     * 这是因为每当创建1个RevisedObjectInHeap对象的时候，占用的堆内存很小（就几十个字节左右），
     * 但是却需要占用2M的堆外内存。这样堆内存还很充足（这种情况下不会执行堆内存的垃圾回收），
     * 但是堆外内存已经不足，所以就不会报OutOfMemoryError。
     *
     * 也就是说利用堆内的full gc来主动调用freeMemory
     *
     * ByteBuffer.allocateDirect(); 里面会创建直接内存，这个直接内存就是调用unsafe.allocateMemory来实现的。
     *   DirectByteBuffer(int cap) {                   // package-private
     *      ......
     *         long base = 0;
     *         try {
     *             base = unsafe.allocateMemory(size);
     *         } catch (OutOfMemoryError x) {
     *             Bits.unreserveMemory(size, cap);
     *             throw x;
     *         }
     *         unsafe.setMemory(base, size, (byte) 0);
     *     ......
     */
    public static void main(String[] args)
    {
        while (true)
        {
            UnsafeExample6 heap = new UnsafeExample6();
            System.out.println("memory address=" + heap.address);
        }
    }
}
