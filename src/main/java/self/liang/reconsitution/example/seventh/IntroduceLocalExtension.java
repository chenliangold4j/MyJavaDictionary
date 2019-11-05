package self.liang.reconsitution.example.seventh;

import java.util.Date;

/**
 *
 * 加入本地扩展：
 * 、    和IntroduceForeignMethod解决类似的问题。
 *       建立一个新类，使它包含这些额外函数，让这个扩展品成为源类的子类或包装类
 *      坚持函数和数据应该被统一封装。
 *      子类和包装类，，通常选子类。子类满足不了需求，就需要包装类
 *
 */
public class IntroduceLocalExtension {

}
class MyDateSub extends Date{

    public MyDateSub(Date date) {
        super(date.getTime());
    }

    Date nextDay(){
        return new Date(getYear(),getMonth(),getDay()+1);
    }

}