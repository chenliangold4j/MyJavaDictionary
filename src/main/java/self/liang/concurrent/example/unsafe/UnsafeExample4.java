package self.liang.concurrent.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeExample4 {
    static Unsafe UNSAFE = null;


    private Object test1;


    public UnsafeExample4 setTest1(Object test1) {
        this.test1 = test1;
        return this;
    }

    private static long test1Index;

    static {

        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
            //获取UnsafeExample4对象内存位置
            Class u = UnsafeExample4.class;
            //获取此对象中test1属性内存偏移
            test1Index = UNSAFE.objectFieldOffset
                    (u.getDeclaredField("test1"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Object o = new Object();
        UnsafeExample4 UnsafeExample4 = new UnsafeExample4();
        //test1属性赋值
        UnsafeExample4.setTest1(o);
        Object o2 = new Object();
        //cas操作，匹配原有属性是否为o，是则赋新值，返回ture
        boolean b = UNSAFE.compareAndSwapObject(UnsafeExample4, test1Index, o2, o);
        System.out.println(b);
        boolean b2 = UNSAFE.compareAndSwapObject(UnsafeExample4, test1Index, o, o2);
        System.out.println(b2);
    }
}
