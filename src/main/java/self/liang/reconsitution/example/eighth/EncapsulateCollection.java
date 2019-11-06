package self.liang.reconsitution.example.eighth;

import java.util.Collection;
import java.util.Collections;

/**
 * 封装集合
 *
 * 让这个函数返回改集合的一个只读副本。
 * 对于集合的修改，，另外设置方法
 *
 *
 */
public class EncapsulateCollection {
    public static void main(String[] args) {
        Collection<String> collection = null;
        //制造无法修改的集合。返回只读副本
        Collection<String>  unmodify =   Collections.unmodifiableCollection(collection);

        //对于数组返回拷贝，虽然里面的对象可以修改，但是无法修改numbers的对象
        int[] numbers = new int[10];
        int[] cp =new int[10];
        System.arraycopy(numbers,0,cp,0,10);
    }
}
