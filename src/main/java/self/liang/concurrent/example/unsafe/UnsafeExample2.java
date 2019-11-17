package self.liang.concurrent.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe 数组定位示例
 * 索引为 i 的元素可以使用如下代码定位：
 *
 * int baseOffset = unsafe.arrayBaseOffset(array.getClass());
 * int indexScale = unsafe.arrayIndexScale(array.getClass());
 * baseOffset + i*indexScale
 * 在ReentrantLock的源码中我又发现了一种新的元素定位方式：
 *
 * int ssfit = 31 - Integer.numberOfLeadingZeros(indexScale);
 * (i << ssfit) + baseOffset
 *
 * 查看Integer的源码，发现 numberOfLeadingZeros 方法里注释如下：
 * floor(log2(x)) = 31 - numberOfLeadingZeros(x)
 *
 * 如果这是一个int型数组，indexScale 等于4，那么 ssfit 值为2，所以乘以4和向左移2位，结果是一样的。
 *
 */
public class UnsafeExample2 {

    private static int ASHIFT;
    private static int ABASE;


    public static void main(String[] args) throws  Exception{
        //1。获取unsafe对象
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        //2.拒两个我们测试用的数据对象
        String[] array1 = new String[]{"fd", "sds", "hffij", "er", "er", "sds"};
        String[] array2 = new String[]{"ff", "fd", "fdf", "xcv", "xcv", "cv"};

        //3。获取String[].class类的内存地址
        // arrayBaseOffset方法是一个本地方法，可以获取数组第一个元素的偏移地址。
        // arrayIndexScale方法是一个本地方法，可以获取数组的转换因子，也就是数组中元素的增量地址。
        // 将arrayBaseOffset与arrayIndexScale配合使用，
        // 可以定位数组中每个元素在内存中的位置。
        Class<?> ak = String[].class;
        ABASE = unsafe.arrayBaseOffset(ak);

        //4.获取此数组的每个成员的内存偏移量（就是每个对象占内存大小）
        int scale = unsafe.arrayIndexScale(ak);
        /**
         * public static int numberOfLeadingZeros(int i) {
         *         // HD, Figure 5-6
         *         if (i == 0)
         *             return 32;
         *         int n = 1;
         *         if (i >>> 16 == 0) { n += 16; i <<= 16; }
         *         if (i >>> 24 == 0) { n +=  8; i <<=  8; }
         *         if (i >>> 28 == 0) { n +=  4; i <<=  4; }
         *         if (i >>> 30 == 0) { n +=  2; i <<=  2; }
         *         n -= i >>> 31;
         *         return n;
         *     }
         *
         * 该方法的作用是返回无符号整型i的最高非零位前面的0的个数，包括符号位在内；
         * 如果i为负数，这个方法将会返回0，符号位为1.
         * 比如说，10的二进制表示为 0000 0000 0000 0000 0000 0000 0000 1010
         * java的整型长度为32位。那么这个方法返回的就是28
         *
         * >>      :     右移运算符，num >> 1,相当于num除以2
         *
         * >>>    :     无符号右移，忽略符号位，空位都以0补齐
         *
         */
        ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        //5。获取对应位置的属性，如2 << ASHIFT，表示位置为2的数据（2*偏移量）
        String array11 = (String) unsafe.getObject(array1, ((long) 2 << ASHIFT) + ABASE);
        String array21 = (String) unsafe.getObject(array2, ((long) 5 << ASHIFT) + ABASE);

        System.out.println(ABASE);
        System.out.println(scale);
        System.out.println(ASHIFT);
        System.out.println(array11);
        System.out.println(array21);
    }
}
