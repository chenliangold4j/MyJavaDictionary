package self.liang.reconsitution.example.eleventh;


/**
 * 提炼子类
 *
 * 类中某些行为只被一部分实例用到。
 *
 * 相反的操作是提炼超类：
 *      两个类有相似的特性。
 *      Composite模式：组合模式。
 *
 *      或者提炼接口
 */
public class ExtractSubclass {

    public static void main(String[] args) {
        Employee kent = new Employee(10);
//        JobItem jobItem = new JobItem(0,5,true,kent);

        JobItem jobItem1 = new LoaborItem(3,kent);
    }

}
//用来决定当地修车厂报价
class  JobItem{

    int unitPrice;//单价
    int quantity;//数量
    Employee employee;//雇员

    public JobItem(int unitPrice, int quantity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isLabor() {
        return false;
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

class LoaborItem extends  JobItem{

    public LoaborItem(int quantity, Employee employee) {
        super(0, quantity);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isLabor() {
        return true;
    }

    @Override
    public int getUnitPrice() {
        return employee.getRate();
    }
}
