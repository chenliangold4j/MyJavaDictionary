package self.liang.zookeeper.code;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

public class ZkNodeOperator implements Watcher {

    private ZooKeeper zooKeeper  = null;
    public static final String zkServerPath = "127.0.0.1:2182";
    public static final Integer  timeout  = 5000;

    ZkNodeOperator(){

    }

    ZkNodeOperator(String connectString){
        try {
            zooKeeper  = new ZooKeeper(connectString,timeout,new ZkNodeOperator());
        } catch (IOException e) {
            e.printStackTrace();
            if(zooKeeper != null){
                try {
                    zooKeeper.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public  void createZKNode(String path, byte[] data, List<ACL> acls){
        String result = "";

        try {
            result = zooKeeper.create(path,data,acls, CreateMode.EPHEMERAL);
            System.out.println("创建节点成功:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(WatchedEvent event) {

    }

    public static void main(String[] args) {
        ZkNodeOperator zkNodeOperator = new ZkNodeOperator(zkServerPath);
        zkNodeOperator.createZKNode();
    }

}
