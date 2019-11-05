package self.liang.reconsitution.example.seventh;

/**
 * 你的程序中，有个函数与其所驻类之外的另一个类进行更多交流：
 * 调用后者，或者被后者调用。
 * <p>
 * moveMethod是重构理论的支柱。
 * <p>
 * 浏览类的所有函数，从中找出：使用另一个对象的次数比使用自己所驻对象的次数还多。
 *
 * 可以能会移来移去，，根本的思想不变。
 */
public class MoveMethod {


}

class Account {
    private AccountType accountType;
    private int daysOverDrawn;


    double bankCharge() {
        double result = 4.5;
        if (daysOverDrawn > 0) result += overDraftCharge();
        return result;
    }

    double overDraftCharge() {
        if (accountType.isPremium()) {
            double result = 10;
            if (daysOverDrawn > 7) result += (daysOverDrawn - 7) * 0.85;
            return result;
        } else
            return daysOverDrawn * 1.75;
    }

}

class AccountType {
    //premium 额外费用
    public boolean isPremium() {
        return true;
    }
}




class Account2 {
    private AccountType2 accountType;
    private int daysOverDrawn;

    double bankCharge() {
        double result = 4.5;
        if (daysOverDrawn > 0) result += accountType.overDraftCharge(daysOverDrawn);
        return result;
    }
}

class AccountType2 {
    //premium 额外费用
    public boolean isPremium() {
        return true;
    }

    double overDraftCharge(int daysOverDrawn) {
        if (isPremium()) {
            double result = 10;
            if (daysOverDrawn > 7) result += (daysOverDrawn - 7) * 0.85;
            return result;
        } else
            return daysOverDrawn * 1.75;
    }
}