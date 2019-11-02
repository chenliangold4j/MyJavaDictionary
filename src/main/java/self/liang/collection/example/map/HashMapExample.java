package self.liang.collection.example.map;

import java.util.HashMap;

/**
 *
 * java 7 的hashMap可能引发并发的情况下可能引发死锁或者循环链表
 *         也可以精心构造恶意的hash碰撞来引发Dos
 *
 * java 8 对hashMap的改进
 *       数组+链表/红黑树
 *       扩容时插入顺序的改进
 *       函数方法
 *          forEach
 *          compute
 *        新api
 *          merge
 *          replace
 *
 *  static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16 初始容量
 *
 *  static final int MAXIMUM_CAPACITY = 1 << 30; 最大容量
 *
 *  static final float DEFAULT_LOAD_FACTOR = 0.75f;  扩容因子
 *
 *  * if ((p = tab[i = (n - 1) & hash]) == null) 630行  下标是hash和长度的按位与。。。所以长度必须是2的次方倍，不然 n-1的二进制不全为1
 *
 *  1.8的resize方法保持了顺序。
 *         else { // preserve order
 *            Node<K,V> loHead = null, loTail = null;
 *
 */
public class HashMapExample
{
   public static void main(String[] args)
   {
      HashMap<String, String> map = new HashMap<>();
   }
}
