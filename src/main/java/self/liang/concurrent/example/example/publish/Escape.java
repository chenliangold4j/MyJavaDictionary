package self.liang.concurrent.example.example.publish;

import lombok.extern.slf4j.Slf4j;
import self.liang.concurrent.example.annoations.NotRecommend;
import self.liang.concurrent.example.annoations.NotThreadSafe;

@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {

    private int thisCanBeEscape = 0;

    public Escape () {
        new InnerClass();
    }

    private class InnerClass {

        public InnerClass() {
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
