package self.liang.reconsitution.example.sixth;


/**
 * 好处：
 *  1.可复用
 *  2.命名代替注释
 *  3.修改变得容易
 *  4.此示例没有局部变量。碰到局部变量传入，或者返回，提炼。
 */
public class ExtractMethod {
    public final String name = "tom";

    void printWoing(double amount){
//        ....... 逻辑代码

        System.out.println("name:"+name);
        System.out.println("amount"+amount);
    }

    void printWoing2(double amount){
//        .......逻辑代码
        printDetails(amount);
    }

    void printDetails(double amount){

        System.out.println("name:"+name);
        System.out.println("amount"+amount);
    }

}
