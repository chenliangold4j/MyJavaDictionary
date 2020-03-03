package self.liang.zookeeper.curator;

import org.apache.zookeeper.WatchedEvent;

public class MyCuratorWatcher implements org.apache.curator.framework.api.CuratorWatcher {

    @Override
    public void process(WatchedEvent event) {
        System.out.println("触发watcher，节点路径为：" + event.getPath());
    }
}
