package self.liang.concurrent.example.example.immutable;

import com.google.common.collect.Maps;
import jdk.internal.instrumentation.Logger;
import self.liang.concurrent.example.annoations.ThreadSafe;

import self.liang.log.example.TestLogger;

import java.util.Collections;
import java.util.Map;


@ThreadSafe
public class ImmutableExample2 {

    private static Logger log= new TestLogger();
    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        log.info("{}"+ map.get(1));
    }

}
