package self.liang.reconsitution.example.sixth;


/**
 * 作为 replace temp with query的一部分使用。
 * 临时变量是某个函数的返回值。
 *
 */

public class InlineTemp {



    public boolean ex1(){
        double basePrice = 0;
//        double basePrice = anOrder.basePrice();
        return basePrice > 1000;
    }

//    public boolean ex12() {
//        return  anOrder.basePrice();
//    }
}
