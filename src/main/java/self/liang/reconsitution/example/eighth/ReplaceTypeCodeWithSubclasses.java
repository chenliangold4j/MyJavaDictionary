package self.liang.reconsitution.example.eighth;

/**
 * 以子类取代类型码
 * <p>
 * 你有一个不可变的类型码，
 * 他会影响类的行为
 * <p>
 * 不影响行为的话ReplaceTypeCodeWithClass即可，如果影响行为，多态是个好选择。
 * <p>
 * 这种情况的标志是switch。或者大量if elseif
 * <p>
 * 有两种情况你不能这么做：
 * 1）类型码值再对象创建之后发生了改变
 * 2）类型码的宿主类已经有了子类。
 * <p>
 * 如果是这样，就需要用replace type code with state/strategy  //策略
 * <p>
 * <p>
 * ReplaceTypeCodeWithSubclasses只要是搭一个舞台
 * 为replace conditional with polymorphism得以一展身手。。虽然我不知道那是什么东西。。- -！~
 */
public class ReplaceTypeCodeWithSubclasses {

}

class Employee {
    private int type;
    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANANGER = 2;

    Employee(int type) {
        this.type = type;
    }


}

abstract class Employee2 {
    private int type;
    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANANGER = 2;

    abstract int getType();

    static Employee2 create(int type) {
        switch (type) {
            case ENGINEER:
                return new Engineer();
            case SALESMAN:
                return new SalesMan();
            case MANANGER:
                return new Manager();
            default:
                throw new IllegalArgumentException("type code error");
        }
    }
}

class Engineer extends Employee2 {

    @Override
    public int getType() {
        return Employee2.ENGINEER;
    }


}

class SalesMan extends Employee2 {


    @Override
    int getType() {
        return Employee2.SALESMAN;
    }
}

class Manager extends Employee2 {


    @Override
    int getType() {
        return Employee2.MANANGER;
    }
}