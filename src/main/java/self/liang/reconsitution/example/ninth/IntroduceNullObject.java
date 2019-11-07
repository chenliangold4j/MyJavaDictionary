package self.liang.reconsitution.example.ninth;

/**
 * 引入空对象：
 * 多态的最根本好处在于：你不必再向对象询问“你是什么类型”而后根据得到的答案调用对象的某个行为；
 *
 * 空对象一定是常量，他们的任何成分不会发生变化。
 * 因此我们可以用单例来实现它
 *
 *
 *
 *你可以有几种不同情况的空对象：比如 什么都没有的顾客：全空，，  没有名字的顾客：等等
 *
 * 本质上。这是一个special case模式。降低错误处理的开销。
 *
 */
public class IntroduceNullObject {

    static Site site;
    static String infoName;
    static int weeksDelingquent;

    public static void main(String[] args) {
        Customer customer = site.getCustomer();
        BillingPlan billingPlan;
        if (customer == null) billingPlan = BillingPlan.basic();//总是有这样的
        else billingPlan = customer.getPlan();
        if (customer == null) infoName = "defaultName";
        else infoName = customer.getName();
        if (customer == null) weeksDelingquent = 0;
        else weeksDelingquent = customer.getPaymentHistory().getCount();


        //有空对象之后；
        infoName = customer.getName(); //空对象默认为defaultName  如果需要空判断还是可以用isnull判断
        billingPlan = customer.getPlan();
        weeksDelingquent = customer.getPaymentHistory().getCount();

    }


}

class Site {

    Customer customer;

    public Customer getCustomer() {
        return (customer == null) ? Customer.newNull() : customer;
    }
}

class Customer {
    String name;
    BillingPlan billingPlan;
    PaymentHistory paymentHistory;

    public String getName() {
        return name;
    }

    public BillingPlan getPlan() {
        return billingPlan;
    }

    public PaymentHistory getPaymentHistory() {
        return paymentHistory == null ? PaymentHistory.newNull(): paymentHistory ;
    }

    public boolean isNull() {
        return false;
    }

    static Customer newNull() {
        return new NullCustomer();
    }
}

class PaymentHistory {
    public int getCount() {
        return 100 * 2;
    }

    public boolean isNull(){
        return false;
    }

    public static PaymentHistory newNull(){
        return new NullPayentHisotory();
    }
}

class NullPayentHisotory extends  PaymentHistory{
    @Override
    public int getCount() {
        return 0;
    }
    public boolean isNull(){
        return true;
    }

}

class BillingPlan {
    static BillingPlan basic() {
        return null;
    }
}

class NullCustomer extends Customer {


    public boolean isNull() {
        return true;
    }

    @Override
    public String getName() {
        return "defaultName";
    }

    @Override
    public BillingPlan getPlan() {
        return BillingPlan.basic();
    }


}