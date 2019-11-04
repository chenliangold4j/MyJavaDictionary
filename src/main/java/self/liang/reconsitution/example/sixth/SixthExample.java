package self.liang.reconsitution.example.sixth;

public class SixthExample {

    double quantity = 2;
    double itemPrice = 7;

    /**
     * 开始
     * 这里如果修改价格的计算和折扣使非常麻烦的。
     * @return
     */
    double getPrice(){
        double basePrice = quantity * itemPrice;
        double discountFactor;
        if(basePrice > 1000)discountFactor = 0.95;
        else  discountFactor = 0.98;
        return  basePrice * discountFactor;
    }

    /**
     *运用各种技巧后
     *  确实。在不考虑性能，优先考虑结构的情况下。
     *  扩展性。方法清晰度会好很多。
     *  修改价格计算--方便
     *  修改折扣---方便
     *  getPrice --调用既注释。结构清晰。
     */
    double getPrice2() {
        return  basePrice() * discountFactor();
    }

    double basePrice(){
        return  quantity * itemPrice;
    }

    double discountFactor(){
        if(basePrice() > 1000)return 0.95;
        else return 0.98;
    }






}
