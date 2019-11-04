package self.liang.reconsitution.example.sixth;

/**
 * 函数对象取代函数
 *
 * 有一个大型函数。无法采用extractMethod。
 *
 *
 * 将这个函数放进一个对象，，这样局部变量就成为了对象内字段，然后可以在同一个对象中将这个大函数拆分。
 *
 * 难以拆解的函数，可以祭出  函数对象（method object）
 *
 * 之后再用extractMethod
 *
 */
public class ReplaceMethodWithMethodObject {

    double price(){
        double primaryBasePrice;
        double secondaryBasePrice;
        double tertiaryBaePrice;
        //long function

        return 0;
    }

    double price2(){
        return new PriceCalculator().compute();
    }
}

class  PriceCalculator{
    double primaryBasePrice;
    double secondaryBasePrice;
    double tertiaryBaePrice;

    public double compute(){

        return 0;
    }
}
