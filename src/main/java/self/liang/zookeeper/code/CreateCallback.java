package self.liang.zookeeper.code;

import org.apache.zookeeper.AsyncCallback;

public class CreateCallback implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("path:"+path+",name:"+name);
        System.out.println((String) ctx);
    }
}
