package self.liang.reconsitution.example.seventh;

/**
 * 提炼类
 *
 * 某个类做了应该由两个类做的事
 *
 * 一个类应该是一个清楚的抽象，处理一些明确的责任。但是实际工作中，类会不断成长扩展。慢慢增加代码和责任。最终变得过于负载。
 *
 * 示例将officeAreaCode，officeNumber合并为TelephoneNumber类，
 * 下一步要做决定：要不要对用户公开这个类。
 *      如果公开新类，就要考虑别名带来的危险。如果公开了，TelephoneNumber,有有个用户修改了areaCode字段，我怎么知道。而且
 *      修改的可能不是直接用户，而是用户的用户。
 *
 *      几种选择：
 *          1.允许任何对象修改TelephoneNumber对象的任何部分，这就使得TelephoneNumber对象成为引用对象，应该考虑
 *          使用change value to reference 这种情况下，Person应该是TelephoneNumber的访问点。
 *          2.不许任何人不通过Person对象就修改TelephoneNumber对象。为此，我可以将TelephoneNumber设置为不可修改的，或为它
 *          提供一个不可修改接口。
 *          3.另一个方法：复制一个TelephoneNumber对象，返回复制的对象。
 *
 *      用ExtractClass存在一定的危险性，比如需要两个对象同时被锁定，你就面临事务问题。
 *
 */
public class ExtractClass {

}

class  Person{

    private String name;
    private String officeAreaCode;
    private String officeNumber;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber(){
        return ("("+officeAreaCode+")")+officeNumber;
    }

    public String getOfficeAreaCode() {
        return officeAreaCode;
    }

    public void setOfficeAreaCode(String officeAreaCode) {
        this.officeAreaCode = officeAreaCode;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    private TelephoneNumber telephoneNumber;

    public String getTelephoneNumber2(){
        return  telephoneNumber.getTelephoneNumber();
    }
}
class TelephoneNumber{
    private String officeAreaCode;
    private String officeNumber;

    public String getOfficeAreaCode() {
        return officeAreaCode;
    }

    public void setOfficeAreaCode(String officeAreaCode) {
        this.officeAreaCode = officeAreaCode;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getTelephoneNumber(){
        return ("("+officeAreaCode+")")+officeNumber;
    }
}


