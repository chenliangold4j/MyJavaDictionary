package self.liang.concurrent.example.example.atomic;

import org.slf4j.Logger;
import self.liang.concurrent.example.annoations.ThreadSafe;
import self.liang.log.example.TestLogger;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@ThreadSafe
public class AtomicExample5 {

    private static Logger log= new TestLogger();
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    public volatile int count = 100;

    public static void main(String[] args) {

        AtomicExample5 example5 = new AtomicExample5();

        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 1, {}"+example5.count);
        }

        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 2, {}"+example5.count);
        } else {
            log.info("update failed, {}"+example5.count);
        }
    }

    public int getCount() {
        return count;
    }
}
