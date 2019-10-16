package self.liang.log.example;

import jdk.internal.instrumentation.Logger;

public class TestLogger implements Logger {

    @Override
    public void error(String s) {
        System.out.println(s);
    }

    @Override
    public void warn(String s) {
        System.out.println(s);
    }

    @Override
    public void info(String s) {
        System.out.println(s);
    }

    @Override
    public void debug(String s) {
        System.out.println(s);
    }

    @Override
    public void trace(String s) {
        System.out.println(s);
    }

    @Override
    public void error(String s, Throwable throwable) {
        System.out.println(s);
    }
}
