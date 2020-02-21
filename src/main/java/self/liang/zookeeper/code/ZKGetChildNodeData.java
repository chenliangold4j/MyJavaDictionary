package self.liang.zookeeper.code;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZKGetChildNodeData implements Watcher {

    private ZooKeeper zooKeeper = null;
    public static final String zkServerPath = "127.0.0.1:2182";
    public static final Integer timeout = 5000;

    private static Stat stat = new Stat();

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public ZKGetChildNodeData() {

    }


    public ZKGetChildNodeData(String connectString) {
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

        try {
            if(event.getType()== Event.EventType.NodeChildrenChanged){
                System.out.println("NodeChildrenChanged");
                ZKGetChildNodeData zkServer = new ZKGetChildNodeData(zkServerPath);
                List<String> strChildList = zkServer.getZooKeeper().getChildren(event.getPath(), false);
                for (String s : strChildList) {
                    System.out.println(s);
                }
                countDownLatch.countDown();
            } else if(event.getType() == Event.EventType.NodeCreated) {
                System.out.println("NodeCreated");
            } else if(event.getType() == Event.EventType.NodeDataChanged) {
                System.out.println("NodeDataChanged");
            } else if(event.getType() == Event.EventType.NodeDeleted) {
                System.out.println("NodeDeleted");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKGetChildNodeData zkGetNodeData = new ZKGetChildNodeData(zkServerPath);
        // 异步调用
        String ctx = "{'callback':'ChildrenCallback'}";
//		zkServer.getZookeeper().getChildren("/imooc", true, new ChildrenCallBack(), ctx);
        zkGetNodeData.getZooKeeper().getChildren("/imooc", zkGetNodeData, new AsyncCallback.Children2Callback() {


            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                System.out.println("path:"+path);
            }
        }, ctx);

        countDownLatch.await();

    }
}
