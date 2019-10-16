package self.liang.concurrent.example.example.publish;

import org.slf4j.Logger;

import self.liang.concurrent.example.annoations.NotThreadSafe;
import self.liang.log.example.TestLogger;

import java.util.Arrays;


@NotThreadSafe
public class UnsafePublish {

    private static Logger log= new TestLogger();
    private String[] states = {"a", "b", "c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("{}"+Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";
        log.info("{}"+ Arrays.toString(unsafePublish.getStates()));
    }
}
