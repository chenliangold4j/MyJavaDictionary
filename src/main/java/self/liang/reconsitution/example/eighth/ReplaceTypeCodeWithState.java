package self.liang.reconsitution.example.eighth;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;

import java.util.Map;

/**
 * 有一个状态码，影响类的行为，但是无法通过继承手段消除它
 * <p>
 * ReplaceTypeCodeWithSubclasses 中的示例，如果对象的身份随时可变，则不应该用继承。
 * 这时候可以考虑用策略模式取代
 */
public class ReplaceTypeCodeWithState {

}

class Employee3 {
    private int type;
    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANANGER = 2;

    int monthSalary;
    int commission;
    int bonus;

    Employee3(int type) {
        this.type = type;
    }

    int payAmount() {
        switch (type) {
            case ENGINEER:
                return monthSalary;
            case SALESMAN:
                return monthSalary + commission;
            case MANANGER:
                return monthSalary + commission + bonus;
            default:
                throw new IllegalArgumentException("type code error");
        }

    }


}

class Employee4 {
    private EmployeeType type;

    Employee4(int type) {
        this.type = EmployeeType.newType(type);
    }

    int payAmount() {
        return type.payAmount();
    }

    public int getType() {
        return type.getTypeCode();
    }

    public void setType(int code) {
        type = EmployeeType.newType(code);
    }
}

abstract class EmployeeType {

    int monthSalary;
    int commission;
    int bonus;


    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANANGER = 2;

    abstract int getTypeCode();

    abstract int payAmount();

    static EmployeeType newType(int code) {
        switch (code) {
            case ENGINEER:
                return new Engineer2();
            case SALESMAN:
                return new SalasMan2();
            case MANANGER:
                return new Manager2();
            default:
                throw new IllegalArgumentException("type code error");
        }
    }
}

class Engineer2 extends EmployeeType {

    @Override
    int getTypeCode() {
        return ENGINEER;
    }

    @Override
    int payAmount() {
        return monthSalary;
    }
}

class SalasMan2 extends EmployeeType {

    @Override
    int getTypeCode() {
        return SALESMAN;
    }

    @Override
    int payAmount() {
        return monthSalary + commission;
    }
}

class Manager2 extends EmployeeType {

    @Override
    int getTypeCode() {
        return MANANGER;
    }

    @Override
    int payAmount() {
        return monthSalary + commission + bonus;
    }
}