package self.liang.reconsitution.example.ninth;

/**
 *
 *
 * 难以看清除正常的执行路径。
 *
 * nested
 * 英 /'nestɪd/  美  全球(英国)
 * adj. 嵌套的，内装的
 * v. 筑巢；嵌入（nest的过去分词
 *
 *guard
 * 英 /ɡɑːd/  美 /ɡɑːrd/  全球(美国)
 * n. 守卫；警戒；护卫队；防护装置
 * vi. 警惕
 * vt. 保卫；监视
 *
 *
 * Clauses
 * n. [法] 条款（clause的复数形式）；[计] 子句
 */
public class ReplaceNestedConditionalWithGuardClauses {
//    double getPayAmount(){
//        double result;
//
//        if(condition1) result = oneAmount();
//        else{
//            if(condition2)result = another();
//            else {
//                if(conditon3)result = another2();
//                else result = another3();
//            }
//        }
//        return  result;
//    }

//    改为
//    double getPayAmount(){
//        if(condition1) return oneAmount();
//        if(condition2)return another();
//         if(conditon3)return another2();
//        return  another3();
//    }


}
