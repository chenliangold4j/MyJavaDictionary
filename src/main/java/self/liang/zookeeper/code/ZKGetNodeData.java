package self.liang.zookeeper.code;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZKGetNodeData implements Watcher {

    private ZooKeeper zooKeeper = null;
    public static final String zkServerPath = "127.0.0.1:2182";
    public static final Integer timeout = 5000;

    private static Stat stat = new Stat();

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public ZKGetNodeData() {

    }


    public ZKGetNodeData(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZkNodeOperator());
        } catch (IOException e) {
            e.printStackTrace();
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeDataChanged){
            ZKGetNodeData zkGetNodeData = new ZKGetNodeData(zkServerPath);
            try {
                byte[] readBytes = zkGetNodeData.getZooKeeper().getData("/imooc",false,stat);
                String result = new String(readBytes);
                System.out.println("更改之后的值:"+result);
                System.out.println("版本号"+stat.getVersion());
                countDownLatch.countDown();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(event.getType() == Event.EventType.NodeChildrenChanged){

        }

        if(event.getType() == Event.EventType.NodeCreated){

        }

        if(event.getType() == Event.EventType.NodeDeleted){

        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKGetNodeData zkGetNodeData = new ZKGetNodeData(zkServerPath);
        //watch监听
        byte[] resbytes = zkGetNodeData.getZooKeeper().getData("/imooc", zkGetNodeData, stat);
        String string = new String(resbytes);
        System.out.println("value : " + string);
        countDownLatch.await();

    }
}
