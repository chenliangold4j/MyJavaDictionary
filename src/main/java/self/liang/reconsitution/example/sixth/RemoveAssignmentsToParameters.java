package self.liang.reconsitution.example.sixth;

/**
 * 移除对参数的赋值
 *
 *  尽量不要修改参数的值。这也是java按值传递的本意。
 *  如果是个object。。除非调用者清晰的要求这样的功能。不然不要修改。
 *
 *
 */
public class RemoveAssignmentsToParameters {

    int discoount(int inputVal ,int quantity,int yearToDate){
            if(inputVal > 50) inputVal -=2;
            return 0;
    }

    int discoount2(int inputVal ,int quantity,int yearToDate){
        int result = inputVal;
        if(inputVal > 50) result -=2;
        return 0;
    }

}
