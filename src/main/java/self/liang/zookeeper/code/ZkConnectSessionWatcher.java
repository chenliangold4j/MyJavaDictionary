package self.liang.zookeeper.code;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 恢复之前会话连接的demo演示
 */
public class ZkConnectSessionWatcher {

    public static final String zkServerPath = "127.0.0.1:2182";
    public static final Logger log  = LoggerFactory.getLogger("ZKConnect");

//    //集群的话可以这么写
//    public static final String ZkServerPath = "192.168.1.101:2181,192.168.1.111:2182,192.168.1.123:2181";

    public static final String zkServers = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    public static final Integer timeout  =  5000;//会话超时时间

    public static void main(String[] args) throws IOException, InterruptedException {
        ZKConnect zkConnect =  new ZKConnect();
        ZooKeeper zooKeeper =  new ZooKeeper(zkServerPath,timeout,zkConnect);

        Thread.sleep(2000);
        long sessionId  = zooKeeper.getSessionId();
        System.out.println(Long.toHexString(sessionId));
        byte[] sessionPassword = zooKeeper.getSessionPasswd();

        /*
        root@zoo2:/zookeeper-3.4.14/bin# echo dump | nc localhost 2181
        SessionTracker dump:
        Session Sets (3):
        0 expire at Thu Jan 01 16:22:30 UTC 1970:
        0 expire at Thu Jan 01 16:22:40 UTC 1970:
        2 expire at Thu Jan 01 16:22:50 UTC 1970:
                0x100003465510002
                0x300010e40450002
        ephemeral nodes dump:
         */

        //由于zookeeper是异步链接的，所以要sleep之后才有状态的改变
        log.debug("链接zookeeper:{}",zooKeeper.getState());
        Thread.sleep(2000);
        log.debug("链接zookeeper:{}",zooKeeper.getState());
        Thread.sleep(2000);
        log.debug(" 开始会话连接:{}");

        ZooKeeper zkSession = new ZooKeeper(zkServerPath,timeout,new ZKConnect(),sessionId,sessionPassword);
        log.debug("重连zkSession:{}",zkSession.getState());
        Thread.sleep(2000);
        log.debug("重连zkSession:{}",zkSession.getState());

    }

}
