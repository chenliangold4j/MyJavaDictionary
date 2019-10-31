package self.liang.jvm.note.classloadernote;

public class ClassNote2Test2 {
    public static void main(String[] args) {
        //这里又是一个被动加载，此类的静态方法没有触发
        ClassNote2[] classNote2s = new ClassNote2[10];
        System.out.println(classNote2s.getClass());//这个类是由jvm自动生成的直接继承自object的子类。
        //java中对数组的访问比C/C++相对安全。。就是因为这个类封装了数组元素的访问方法。而C/C++直接翻译为对数组的指针移动
        //所以在java越界是封装好的异常，但是c/c++则可能访问到别的内存地址

    }
}
