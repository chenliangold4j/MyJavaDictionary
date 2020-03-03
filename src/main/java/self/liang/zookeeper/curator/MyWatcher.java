package self.liang.zookeeper.curator;

import org.apache.zookeeper.WatchedEvent;

public class MyWatcher implements org.apache.zookeeper.Watcher {
    @Override
    public void process(WatchedEvent event) {
        System.out.println("触发watcher，节点路径为：" + event.getPath());
    }
}
