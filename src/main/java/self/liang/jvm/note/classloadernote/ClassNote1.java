package self.liang.jvm.note.classloadernote;

public class ClassNote1  {

    static{
        i=0;//正常编译通过   也就是说前面的静态块可以赋值，但是不能访问
//        System.out.println(i);//illegal forward reference 非法向前引用
    }
    static  int i=1;
}
