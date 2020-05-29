package self.liang.concurrent.example.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyLock  extends AbstractQueuedSynchronizer {


    public boolean lock(){
        return tryAcquire(1);
    }

    public boolean unlock(){
        return tryRelease(1);
    }

    @Override
    protected boolean tryAcquire(int arg) {
        for(;;){
            int c=getState();
            if(c==0){
                if(compareAndSetState(0,arg)){
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }else{
                if(getExclusiveOwnerThread()==Thread.currentThread()){
                    setState(c+1);
                    return true;
                }
            }
        }
    }

    @Override
    protected boolean tryRelease(int arg) {
        if(getExclusiveOwnerThread()!=Thread.currentThread()){
            throw new IllegalMonitorStateException();
        }else{
            setState(getState()-1);
            if(getState()==0){
                setExclusiveOwnerThread(null);
            }else if(getState()<=0){
                throw new IllegalMonitorStateException();
            }
            return true;
        }
    }

    public static int sum=0;
    public static void main(String[] args) {
        MyLock lock=new MyLock();

        Runnable runnable=()->{
            lock.lock();
            sum+=1;
            System.out.println(sum);
            lock.unlock();
        };
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        for(int i=0;i<10;i++){
            executorService.submit(runnable);
        }
        executorService.shutdown();
    }
}

