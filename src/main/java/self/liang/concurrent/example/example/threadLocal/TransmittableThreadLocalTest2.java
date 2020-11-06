package self.liang.concurrent.example.example.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.*;

public class TransmittableThreadLocalTest2 {

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1));

    public static void main(String[] args) throws InterruptedException {
        // 1 创建线程变量
        TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<String>();
        // 2 投递三个任务
        for (int i = 0; i < 3; ++i) {
            bizPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 3休眠4s


        Thread.sleep(4000);
        // 4.设置线程变量
        parent.set("value-set-in-parent");
        // 5. 提交任务到线程池
        Runnable task = () -> {
            try {
                // 5.1访问线程变量
                System.out.println("parent:" + parent.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        // 5.2额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task);
        //5.3
        bizPoolExecutor.execute(ttlRunnable);

    }

    /**
     * 在使用线程池并且使用有界队列的时候，如果队列满了，任务添加到线程池的时候就会有问题，针对这些问题java线程池提供了以下几种策略：
     *
     * AbortPolicy
     * DiscardPolicy
     * DiscardOldestPolicy
     * CallerRunsPolicy
     * 自定义
     *
     * ◇AbortPolicy
     * 该策略是线程池的默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
     * 源码如下：
     *  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *             //不做任何处理，直接抛出异常
     *             throw new RejectedExecutionException("Task " + r.toString() +
     *                                                  " rejected from " +
     *                                                  e.toString());
     *         }
     *
     * ◇DiscardPolicy
     * 这个策略和AbortPolicy的slient版本，如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
     * 源码如下：
     *    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *         	//就是一个空的方法
     *         }
     *
     * ◇DiscardOldestPolicy
     * 这个策略从字面上也很好理解，丢弃最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
     * 因为队列是队尾进，队头出，所以队头元素是最老的，因此每次都是移除对头元素后再尝试入队。
     * 源码如下：
     *         public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *             if (!e.isShutdown()) {
     *             	//移除队头元素
     *                 e.getQueue().poll();
     *                 //再尝试入队
     *                 e.execute(r);
     *             }
     *         }
     *
     * CallerRunsPolicy
     * 使用此策略，如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。就像是个急脾气的人，我等不到别人来做这件事就干脆自己干。
     * 源码如下：
     * public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *             if (!e.isShutdown()) {
     *                 //直接执行run方法
     *                 r.run();
     *             }
     *         }
     *
     * 自定义
     * 如果以上策略都不符合业务场景，那么可以自己定义一个拒绝策略，只要实现RejectedExecutionHandler接口，并且实现rejectedExecution方法就可以了。具体的逻辑就在rejectedExecution方法里去定义就OK了。
     * 例如：我定义了我的一个拒绝策略，叫做MyRejectPolicy，里面的逻辑就是打印处理被拒绝的任务内容
     * public class MyRejectPolicy implements RejectedExecutionHandler{
     *     public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
     *         //Sender是我的Runnable类，里面有message字段
     *         if (r instanceof Sender) {
     *             Sender sender = (Sender) r;
     *             //直接打印
     *             System.out.println(sender.getMessage());
     *         }
     *     }
     * }
     *
     * 这几种策略没有好坏之分，只是适用不同场景，具体哪种合适根据具体场景和业务需要选择，如果需要特殊处理就自己定义好了。
     */
    class TestHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }

}
