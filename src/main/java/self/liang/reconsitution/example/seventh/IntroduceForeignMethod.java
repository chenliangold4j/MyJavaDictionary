package self.liang.reconsitution.example.seventh;

import java.util.Date;

/**
 *
 * 引入外加函数：
 *  你需要为提供服务的类增加一个函数，但你无法修改这个类的时候。
 *
 *  可以修改就在类修改，不能则在自己的类编码。
 *  如果只用一次。则没有必要。
 *  如果增加了大量外加函数。就不应该用这个方法。
 *  而应该使用IntroduceLocalExtension
 *
 *
 * 这种方法终归是权权宜之计。可能的话。还是要移动到他所属的类。
 *
 */
public class IntroduceForeignMethod {

    private int yaer;
    private int month;
    private int day;

    public IntroduceForeignMethod() {
    }

    public IntroduceForeignMethod(int yaer, int month, int day) {
        this.yaer = yaer;
        this.month = month;
        this.day = day;
    }

    public static void main(String[] args) {

        IntroduceForeignMethod introduceForeignMethod = new IntroduceForeignMethod(1921,12,12);

        //从老对象增加新对象。无法更改的话。每次都太麻烦了
        IntroduceForeignMethod newIn = new IntroduceForeignMethod(introduceForeignMethod.getYaer(),introduceForeignMethod.getMonth(),introduceForeignMethod.getDay());

        //修改后
        IntroduceForeignMethod newIn2 = newIntroduceForeignMethod(introduceForeignMethod);


    }

    public int getYaer() {
        return yaer;
    }

    public void setYaer(int yaer) {
        this.yaer = yaer;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private static IntroduceForeignMethod newIntroduceForeignMethod(IntroduceForeignMethod introduceForeignMethod){
        return new IntroduceForeignMethod(introduceForeignMethod.getYaer(),introduceForeignMethod.getMonth(),introduceForeignMethod.getDay());
    }

}
