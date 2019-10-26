package self.liang.classload.example;

/**
 * 验证过程。。需要编译一个class文件到项目不可达的地方。。让后用类加载器加载
 *
 */
public class LoaderTest  {
    public static void main(String[] args) {
       ClassLoader classLoader =   LoaderTest.class.getClassLoader();
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());//这里获取不到bootstrap class loader

        /**
         * Bootstrap 类加载器是用 C++ 实现的，是虚拟机自身的一部分，如果获取它的对象，
         * 将会返回 null；扩展类加载器和应用类加载器是独立于虚拟机外部，
         * 为 Java 语言实现的，均继承自抽象类 java.lang.ClassLoader ，开发者可直接使用这两个类加载器。
         *
         * Application 类加载器对象可以由 ClassLoader.getSystemClassLoader() 方法的返回，
         * 所以一般也称它为系统类加载器。它负责加载用户类路径（ClassPath）上所指定的类库，
         * 如果应用程序中没有自定义过自己的类加载器，一般情况下这个就是程序中默认的类加载器。
         *
         * 双亲委派模型对于保证 Java 程序的稳定运作很重要，
         * 例如类 java.lang.Object，它存放在 rt.jar 之中，无论哪一个类加载器要加载这个类，
         * 最终都是委派给处于模型最顶端的启动类加载器进行加载，因此 Object 类在程序的各种类加载器环境中都是同一个类。
         */
    }
}
