package self.liang.concurrent.example.example.immutable;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import self.liang.concurrent.example.annoations.NotThreadSafe;

import self.liang.log.example.TestLogger;

import java.util.Map;


@NotThreadSafe
public class ImmutableExample1 {

    private static Logger log= new TestLogger();
    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
//        a = 2;
//        b = "3";
//        map = Maps.newHashMap();
        map.put(1, 3);
        log.info("{}"+ map.get(1));
    }

    private void test(final int a) {
//        a = 1;
    }
}
