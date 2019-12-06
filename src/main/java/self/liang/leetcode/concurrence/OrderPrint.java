package self.liang.leetcode.concurrence;

import org.springframework.core.annotation.Order;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我们提供了一个类：
 * <p>
 * public class Foo {
 *   public void one() { print("one"); }
 *   public void two() { print("two"); }
 *   public void three() { print("three"); }
 * }
 * 三个不同的线程将会共用一个 Foo 实例。
 * <p>
 * 线程 A 将会调用 one() 方法
 * 线程 B 将会调用 two() 方法
 * 线程 C 将会调用 three() 方法
 * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3]
 * 输出: "onetwothree"
 * 解释:
 * 有三个线程会被异步启动。
 * 输入 [1,2,3] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 two() 方法，线程 C 将会调用 three() 方法。
 * 正确的输出是 "onetwothree"。
 * 示例 2:
 * <p>
 * 输入: [1,3,2]
 * 输出: "onetwothree"
 * 解释:
 * 输入 [1,3,2] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 three() 方法，线程 C 将会调用 two() 方法。
 * 正确的输出是 "onetwothree"。
 *  
 * <p>
 * 注意:
 * <p>
 * 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
 * <p>
 * 你看到的输入格式主要是为了确保测试的全面性。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-in-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class OrderPrint {

    volatile int firstFlag = 0;
    volatile int secondFlag = 0;

    ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition = reentrantLock.newCondition();

    public void first(Runnable printFirst) throws InterruptedException {
        reentrantLock.lock();
        while(firstFlag == 1){
            condition.await();
        }
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        firstFlag = 1;
        condition.signalAll();
        reentrantLock.unlock();

    }

    public void second(Runnable printSecond) throws InterruptedException {
        reentrantLock.lock();
        while (firstFlag == 0){
            condition.await();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        secondFlag = 1;
        condition.signalAll();
        reentrantLock.unlock();

    }

    public void third(Runnable printThird) throws InterruptedException {
        reentrantLock.lock();
        while (firstFlag == 0 || secondFlag == 0){
            condition.await();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        firstFlag=0;
        secondFlag = 0;
        condition.signalAll();
        reentrantLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        OrderPrint orderPrint  = new OrderPrint();
        orderPrint.first(new Runnable() {
            @Override
            public void run() {
                System.out.println("one");
            }
        });
        orderPrint.second(new Runnable() {
            @Override
            public void run() {
                System.out.println("two");
            }
        });
        orderPrint.third(new Runnable() {
            @Override
            public void run() {
                System.out.println("three");
            }
        });
    }
}
