package self.liang.zookeeper.code;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZkNodeOperator implements Watcher {

    private ZooKeeper zooKeeper  = null;
    public static final String zkServerPath = "127.0.0.1:2182";
    public static final Integer  timeout  = 5000;

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public ZkNodeOperator(){

    }

    public ZkNodeOperator(String connectString){
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
            String ctx = "{'create':'success'}";
//            result = zooKeeper.create(path,data,acls, CreateMode.EPHEMERAL);//同步方式
            zooKeeper.create(path,data,acls, CreateMode.PERSISTENT,new CreateCallback(),ctx);//异步

            System.out.println("创建节点成功:"+result);
            new Thread().sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

}



    @Override
    public void process(WatchedEvent event) {

    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkNodeOperator zkNodeOperator = new ZkNodeOperator(zkServerPath);
        //创建节点
//        zkNodeOperator.createZKNode("/testnode","testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);

        //修改节点  这里同样也有异步版本
//        Stat stat = zkNodeOperator.getZooKeeper().setData("/testnode","modify".getBytes(),0);//dataVersion版本号不对。则无法设置
//        System.out.println(stat.getVersion());//stat 既是get的数据

        //删除节点 同样有异步
//        zkNodeOperator.createZKNode("/test-delete","testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
//        zkNodeOperator.getZooKeeper().delete("/test-delete",0);


    }

}
