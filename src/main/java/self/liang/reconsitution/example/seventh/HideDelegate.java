package self.liang.reconsitution.example.seventh;

/**
 * 隐藏委托关系
 *
 * 随着经验日渐丰富，你会发现，有更多可以封装的东西。
 *  如果某个客户想通过服务对象的字段得到另一个对象，然后调用后者的函数。那么客户就必须知晓这一层委托关系。万一魏国关系发生变化，客户
 *  也得相应变化。你可以在服务对象上放置一个简单的魏国函数，将委托关系隐藏起来，从而去除依赖。即使发生变化，变化也在内部。
 *
 *
 * 如果希望知道某人的经理是谁，必须取得Department对象。这样的化，必须使用者知道通过department追踪manager这条信息。
 *
 */
public class HideDelegate {



}

class Staff{

    Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    //modify
    public Person getManager(){
        return department.getManager();
    }

}
class Department{
    private String chargeCode;
    private Person manager;

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }
}
