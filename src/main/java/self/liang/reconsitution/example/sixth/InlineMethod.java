package self.liang.reconsitution.example.sixth;

/**
 * 1.手上有一群组织不甚合理的函数。可以将他们内联到一个大型后汉书中。
 *   再从中提取合理的小型函数。
 * 2.太多的没有必要的中间层。比如对一个函数的简单委托。
 * 3.
 */
public class InlineMethod {

    int score;

    int getRating(){
        return moreThanFive() ? 2: 1;
    }

    boolean moreThanFive(){
        return score > 5;
    }
    int getRating2(){
        return score > 5 ? 2: 1;
    }


}
