package self.liang.concurrent.example.example.publish;

import org.slf4j.Logger;

import self.liang.concurrent.example.annoations.NotRecommend;
import self.liang.concurrent.example.annoations.NotThreadSafe;
import self.liang.log.example.TestLogger;


@NotThreadSafe
@NotRecommend
public class Escape {

    private static Logger log= new TestLogger();
    private int thisCanBeEscape = 0;

    public Escape () {
        new InnerClass();
    }

    private class InnerClass {

        public InnerClass() {
            log.info("{}"+Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
