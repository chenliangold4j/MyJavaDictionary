package self.liang.reconsitution.example.eleventh;


/**
 * 提炼子类
 *
 * 类中某些行为只被一部分实例用到。
 *
 */
public class ExtractSubclass {



}
//用来决定当地修车厂报价
class  JobItem{

    int unitPrice;
    int quantity;
    boolean isLabor;
    Employee employee;

    public JobItem(int unitPrice, int quantity, boolean isLabor, Employee employee) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.isLabor = isLabor;
        this.employee = employee;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isLabor() {
        return isLabor;
    }

    public Employee getEmployee() {
        return employee;
    }
}

class Employee{
    int rate;

    public Employee(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
