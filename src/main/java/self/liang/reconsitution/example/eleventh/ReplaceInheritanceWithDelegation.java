package self.liang.reconsitution.example.eleventh;

import java.util.Vector;

/**
 * 委托取代继承：
 *
 * 常常会遇到这样的情况：一开始继承一个类，随后发现超类中的许多操作并不真正适合于子类。
 * 这种情况下，你所拥有的接口并未真正反映出子类的功能。或者  子类继承了一堆不需要的数据或者方法。
 *
 *如果以委托取代继承，你可以清除的表明：你只需要受托类的一部分功能。
 *
 *
 * 与之相反，如果有太多功能需要委托，，就靠转换为继承。
 *
 */
public class ReplaceInheritanceWithDelegation {
    public static void main(String[] args) {
        Mystack mystack = new Mystack();
//        mystack.  这里有太多方法于栈无关
    }

}

/**
 *  用mystack 只需要 push ，pop，size isEmpty 四个函数。两个继承而来。
 */
class Mystack extends Vector{


    public void push(Object ele){
        insertElementAt(ele,0);
    }

    public Object pop(){
        Object result = firstElement();
        removeElementAt(0);
        return result;
    }

}

class Mystack2 {

    private  Vector vector = new Vector();

    public void push(Object ele){
        vector.insertElementAt(ele,0);
    }

    public Object pop(){
        Object result = vector.firstElement();
        vector.removeElementAt(0);
        return result;
    }

    public int size(){
        return vector.size();
    }
    public boolean isEmpty(){
        return  vector.isEmpty();
    }

}