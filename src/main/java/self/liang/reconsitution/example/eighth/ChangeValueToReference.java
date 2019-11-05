package self.liang.reconsitution.example.eighth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 将值对象改为引用对象
 *
 *      对象的一种有用的分类：引用对象和值对象。
 *          1.前者像是“客户”  “账户” 这样的东西，每个对象代表一个实物。以== 相等操作判断两个对象是否相等
 *          2.后者像“日期” “钱” 这样的东西，完全由其所含的数据值来定义，你并不在意副本的存在。系统
 *          或许存在上千个“1/1/2020“的日志对象。覆写equals，hashcode方法，这上千个对象对自己来说是一样的。
 **
 *          这个示例：希望一个客户由多个不同订单。代表订单的order
 *          先用replace constructor with factory method 这样可以控制customer的创建。
 *
 *          因为要让customer不只用来获取值，也要让其和order对应。就用工厂模式重构。
 *
 *    最初是一个String代表customer，之后是值对象。。。再后是引用对象。
 *    都是由于业务的推进，慢慢重构而来。。
 *
 *    其实这也说明了测试用例的重要性，，，因为要保证业务不受影响。不然你哪敢修改。
 *
 */
public class ChangeValueToReference {
}
class Customer{
    private final String name;

    private static Map<String,Customer> instances = new HashMap<>();

    static{
        new Customer("lemon").store();
        new Customer("hire").store();
        new Customer("jay").store();
    }

    //modify
    private Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //modify
    public static Customer create(String name){
        Customer result = instances.get(name);
        if(result == null){
            result = new Customer(name);
            result.store();
        }
        return result;
    }

    private void store(){
        instances.put(this.getName(),this);
    }
}

class Order{
    private  Customer customer;

    public Order(String customerName) {
//        this.customer = new Customer(customerName);
        //modify
        this.customer = Customer.create(customerName);
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public void setCustomer(String customerName) {
        this.customer = Customer.create(customerName);
    }

    private static int numberOfOrdersFor(Collection orders,String customer){
        int result = 0;
        Iterator iterator = orders.iterator();
        while(iterator.hasNext()){
            Order order = (Order) iterator.next();
            if(order.getCustomerName().equals(customer))result++;
        }
        return result;


    }
}