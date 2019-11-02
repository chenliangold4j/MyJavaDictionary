package self.liang.jvm.note.example;

import java.util.HashMap;
import java.util.Map;

public class OOM {

    public static void main(String[] args) throws InterruptedException {
        Map<String ,byte[]> result  = new HashMap<>();
        for(int i = 0;i<128;i++){
            result.put(""+i,new byte[1024*1024]);
        }
    }

}
