package self.liang.concurrent.example.lock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 这个模板意在给你一个关于非阻塞算法大致是什么样子的一个idea。如果，
 * 你想实现自己的非阻塞算法，首先学习一些实际的工业水平的非阻塞算法的时间，在实践中学习更多关于非阻塞算法实现的知识。
 */
public class NonblockingTemplate {
    public  static class IntendedModification{
        public AtomicBoolean completed = new AtomicBoolean(false);
    }

    private AtomicStampedReference<IntendedModification> ongoinMod = new AtomicStampedReference<IntendedModification>(null, 0);
    //declare the state of the data structure here. 在这里声明数据结构的状态。

    public void modify(){
        while(!attemptModifyASR());
    }


    public boolean attemptModifyASR(){
        boolean modified = false;

        IntendedModification currentlyOngoingMod = ongoinMod.getReference();
        int stamp = ongoinMod.getStamp();

        if(currentlyOngoingMod == null){
            //copy data structure - for use
            //in intended modification

            //prepare intended modification
            IntendedModification newMod = new IntendedModification();

            boolean modSubmitted = ongoinMod.compareAndSet(null, newMod, stamp, stamp + 1);

            if(modSubmitted){
                //complete modification via a series of compare-and-swap operations.
                //note: other threads may assist in completing the compare-and-swap
                // operations, so some CAS may fail
                modified = true;
            }
        }else{
            //attempt to complete ongoing modification, so the data structure is freed up
            //to allow access from this thread.
            modified = false;
        }

        return modified;
    }
}

