package self.liang.reconsitution.example.sixth;

/**
 * 其实这种方式我是存疑的。。这种计算还好。要是是一些数据库查询。再缓存不够的情况可能会导致性能问题。亦或者是网络状况不是特别顺畅的查询。
 *
 * 尽量只在简单返回或者简单计算 ，或者调用一次的情况下使用
 *
 * 可以需要split temporary variable 或 separate query from modifier使情况变得简单一下
 *
 * 作者对性能的解释：优先使得代码组织良好，组织良好的代码，往往能发现更有效的优化方案。
 */
public class ReplaceTempWithQuery {

    double quantity = 2;
    double itemPrice = 7;

    public double getCount(){
        double basePrice = quantity * itemPrice;
        if(basePrice > 1000)
            return basePrice * 0.95;
        else
            return basePrice*0.98;
    }

    public double getCount2(){
        if(basePrice() > 1000)
            return basePrice() * 0.95;
        else
            return basePrice()*0.98;
    }


    double basePrice(){
        return quantity * itemPrice;
    }


}
