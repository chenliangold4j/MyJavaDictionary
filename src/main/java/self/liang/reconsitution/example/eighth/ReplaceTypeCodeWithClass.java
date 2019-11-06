package self.liang.reconsitution.example.eighth;

/**
 * 以类取代代码：
 *
 *      类之中由一个数值型类型码，并不影响类型的行为。
 *
 *
 *
 *  如果把数值换成类，，编译器就可以对他进行类型检查。
 *  只要提供工厂函数，就可以保证只有合法的实例才会被创建出来。
 *
 *
 */
public class ReplaceTypeCodeWithClass {
    public static void main(String[] args) {
        Person2 person2 = new Person2(1);
        person2.setBloodGroup(3);
        System.out.println(person2.getBloodGroup());
    }
}

/**
 * 由不同的血型
 */
class Person{
    public static final int O = 0;
    public static final int A = 1;
    public static final int B=2;
    public static final int AB = 3;


    private int bloodGroup;

    public Person(int bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(int bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}

enum  BloodGroup{
    O(0),A(1),B(2),AB(3);


    private int code;
    BloodGroup(int i){
        this.code= i;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

class Person2{

    private BloodGroup bloodGroup;

    public Person2(int bloodGroup) {
        setBloodGroup(bloodGroup);
    }

    public int getBloodGroup() {
        return bloodGroup.getCode();
    }

    public void setBloodGroup(int bloodGroupCode) {
        for(BloodGroup bloodGroup:BloodGroup.values()) {
            if(bloodGroupCode == bloodGroup.getCode()){
                this.bloodGroup = bloodGroup;
            }
        }
    }
}

