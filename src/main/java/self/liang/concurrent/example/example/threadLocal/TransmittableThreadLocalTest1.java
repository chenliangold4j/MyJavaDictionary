package self.liang.concurrent.example.example.threadLocal;

public class TransmittableThreadLocalTest1 {

    public static void main(String[] args) {
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<String>();

        context.set("value-set-in-parent")
        println("[parent thread] set ${context.get()}")

        /////////////////////////////////////
        // create sub-thread
        /////////////////////////////////////
        thread {
            val value = context.get()
            println("[child thread] get $value")
        }.join();

        println("[parent thread] get ${context.get()}")
    }

}
