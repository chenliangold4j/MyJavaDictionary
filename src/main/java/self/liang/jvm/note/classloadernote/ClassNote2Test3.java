package self.liang.jvm.note.classloadernote;

public class ClassNote2Test3 {
    public static void main(String[] args) {
        //被动引用示例3   在编辑阶段。常量会被存入到调用方法的类的常量池中  这里存入了ClassNote2Test3的常量池中 和  ClassNote23没有关系了
        //甚至可以把ClassNote23的class文件删除
        //javap -c self.liang.jvm.note.classloadernote.ClassNote23 反编译

        /**
         *       助记符：ldc表示将int，float或是String类型的常量值从常量池中推送至栈顶
         *              bipush表示将单子接（-128~127）的常量推送至栈顶
         *              sipush表示将一个短整型（-32768~32767）常量推送至栈顶
         *              iconst_1表示将int的数字1推送至栈顶  最多-1到5 自行验证
         *              iconst_m1 示将int的数字-1推送至栈顶 m代表minus
         *
         *              public class ICONST extends Instruction 这个类是java的类  其他的也有相关类进行操作
         */
        /**
         * 命令：javap -c self.liang.jvm.note.classloadernote.ClassNote2Test3
         *
         *  public class self.liang.jvm.note.classloadernote.ClassNote2Test3 {
         *   public self.liang.jvm.note.classloadernote.ClassNote2Test3();
         *     Code:
         *        0: aload_0
         *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         *        4: return
         *
         *   public static void main(java.lang.String[]);
         *     Code:
         *        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         *        3: ldc           #4                  // String hello world //这样代码已经被存入到常量值中，可以直接拿到了hello world
         *        5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         *        8: return
         *
         *     public static void main(java.lang.String[]);
         *     Code:
         *        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         *        3: ldc           #4                  // String hello world
         *        5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         *        8: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         *       11: bipush        7                                //修改之后再编译 这里就不是地址了，而是值
         *       13: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
         *       16: return
         *
         *
         *       19: sipush        128             //添加s1之后
         * }
         *
         *         iconst_1                       //添加m
         */
        System.out.println(ClassNote23.HELLOWORLD);
        System.out.println(ClassNote23.s);
        System.out.println(ClassNote23.s1);
        System.out.println(ClassNote23.m);
        System.out.println(ClassNote23.m2);
    }
}
