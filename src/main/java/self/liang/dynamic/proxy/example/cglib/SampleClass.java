package self.liang.dynamic.proxy.example.cglib;

public class SampleClass {
    public String test(String input){
        System.out.println("内部方法");
        return "hello world";
    }
}
