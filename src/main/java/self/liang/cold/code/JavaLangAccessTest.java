package self.liang.cold.code;

import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;

/**
 * SharedSecrets和JavaLangAccess，通过这两个类来获取Java栈帧中存储的类信息，然后进行挑选，从而找出调用的类。
 *
 * java9  zai jdk.internal.misc
 *
 * 这个类还可以访问被保护的方法，而不用反射。
 */
public class JavaLangAccessTest {
    private String test;
    public static void main(String[] args) {
        JavaLangAccessTest javaLangAccessTes = new JavaLangAccessTest();
        javaLangAccessTes.test();
    }

    public void test(){
        testPrint();
    }

    public void testPrint() {

        JavaLangAccess access = SharedSecrets.getJavaLangAccess();
        Throwable throwable = new Throwable();

        int depth = access.getStackTraceDepth(throwable);

        //输出JVM栈帧中的所有类实例
        for (int i = 0; i < depth; i++) {
            StackTraceElement frame = access.getStackTraceElement(throwable, i);
            System.out.println("--->"+frame);
        }


    }

}
