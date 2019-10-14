package self.liang.concurrent.example.example.threadLocal;

public class TestNum {
    public static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int getNexNum(){
        seqNum.set(seqNum.get()+1);
        return seqNum.get();
    }


    public  ThreadLocal<Integer> getSeqNum() {
        return seqNum;
    }

    public static void main(String[] args) {
        TestNum sn = new TestNum();
        // ③ 3个线程共享sn，各自产生序列号
        TestClient t1 = new TestClient(sn);
        TestClient t2 = new TestClient(sn);
        TestClient t3 = new TestClient(sn);
        t1.start();
        t2.start();
        t3.start();
    }

    private static class TestClient extends Thread {
        private TestNum sn;

        public TestClient(TestNum sn) {
            this.sn = sn;
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                // ④每个线程打出3个序列值
                System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
                        + sn.getNexNum() + "]"+sn.getSeqNum().get().hashCode());
            }
        }
    }

}
