package self.liang.jvm.note.classloadernote;

public class ClassNote2Test {
    public static void main(String[] args) {
        //这时候子类没有初始化，父类初始话了，但是子类加载了，这是被动调用的一种形式
        //具体子类会不会初始化要看虚拟机的实现，
        //-XX:+TraceClassLoading 用于追踪类的加载信息并打印出来
        // [Loaded self.liang.jvm.note.classloadernote.ClassNote2Test from file:/C:/Users/phantom/IdeaProjects/MyJavaDictionary/target/classes/]
        // [Loaded self.liang.jvm.note.classloadernote.ClassNote2 from file:/C:/Users/phantom/IdeaProjects/MyJavaDictionary/target/classes/]
        // [Loaded self.liang.jvm.note.classloadernote.ClassNote2Son from file:/C:/Users/phantom/IdeaProjects/MyJavaDictionary/target/classes/]
        //加了参数之后的打印。。会看到两个类都加载了。。先夫后子。。先主类后主类用到的其他类

        /**
         * 所有jvm参数都是 -XX:开始的
         *   -XX:+<option> 表示要开启option选项
         *   -XX:-<option> 表示关闭option选项
         *   -XX:<option>=value  将option选项的值设置为value
         */

        System.out.println(ClassNote2Son.value);
    }
}
