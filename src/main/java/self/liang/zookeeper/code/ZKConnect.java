package self.liang.zookeeper.code;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ZKConnect   implements Watcher {

    public static final String zkServerPath = "127.0.0.1:2181";
    public static final Logger log  = LoggerFactory.getLogger("ZKConnect");

//    //集群的话可以这么写
//    public static final String ZkServerPath = "192.168.1.101:2181,192.168.1.111:2182,192.168.1.123:2181";

    public static final String zkServers = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    public static final Integer timeout  =  5000;//会话超时时间

    public static void main(String[] args) throws IOException, InterruptedException {
        ZKConnect zkConnect =  new ZKConnect();
        ZooKeeper zooKeeper =  new ZooKeeper(zkServers,timeout,zkConnect);
        //由于zookeeper是异步链接的，所以要sleep之后才有状态的改变
        log.debug("链接zookeeper:{}",zooKeeper.getState());
        Thread.sleep(2000);
        log.debug("链接zookeeper:{}",zooKeeper.getState());

    }

    @Override
    public void process(WatchedEvent event) {
        log.debug(" 接受到事件：{}",event);
    }
}
