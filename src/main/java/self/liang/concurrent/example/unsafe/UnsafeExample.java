package self.liang.concurrent.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe的字段定位示例.
 */
public class UnsafeExample {

    private volatile int state;
    protected final int getState() {
        return state;
    }
    protected final void setState(int newState) {
        state = newState;
    }

    private static Unsafe unsafe;
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe =  (Unsafe)f.get(null);
        } catch (Exception e) {

        }
    }
    private static final long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (UnsafeExample.class.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    public static void main(String[] args) {
        UnsafeExample unsafeExample = new UnsafeExample();
        unsafeExample.setState(3);
        //unsafe的原子替换。
        unsafeExample.compareAndSetState(1,5);
        unsafeExample.compareAndSetState(3,6);
        System.out.println(unsafeExample.state);
    }
}
