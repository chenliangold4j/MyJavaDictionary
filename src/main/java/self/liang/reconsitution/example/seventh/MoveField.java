package self.liang.reconsitution.example.seventh;

/**
 * 某个字段被其所驻之外的另一个类更多的用到。
 *
 * 在类之间移动状态和行为，是重构过程中必不可少的措施。随着系统发展，你会发现自己需要新的类，并需要将现有的工作责任拖到新类中。
 *
 * 单独看这样的重构技巧似乎不太负载。。但是当和其他技巧一起使用的时候。。比如：moveMethod 会很好用
 *
 */
public class MoveField {


}

class Account3 {
    private AccountType3 accountType;
//    private double interestRate;

//    double intersetForAmount_days(double amount,int days){
//        return  interestRate * amount*days/365;
//    }

    double intersetForAmount_days2(double amount,int days){
        return  accountType.getInterestRate() * amount*days/365;
    }

    public double getInterestRate() {
        return accountType.getInterestRate();
    }

    public void setInterestRate(double interestRate) {
        accountType.setInterestRate(interestRate);
    }
}

class AccountType3 {
    private double interestRate;


    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}