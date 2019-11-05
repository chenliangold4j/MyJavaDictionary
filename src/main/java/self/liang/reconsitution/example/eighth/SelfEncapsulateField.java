package self.liang.reconsitution.example.eighth;

/**
 *自封装字段：
 *  encapsulate
 * 英 /ɪnˈkæpsjuleɪt/  美 /ɪnˈkæpsjuleɪt/  全球(美国)
 * vt. 压缩；将…装入胶囊；将…封进内部；概述
 * vi. 形成胶囊
 *
 *
 *一种方法，一种直接。
 *      作者更喜欢先用直接访问，必要的时候重构。
 *      但在子类返回值需要加工时，必须使用方法。
 *
 *
 */
public class SelfEncapsulateField {

    private  int low,high;
    boolean include(int arg){
        return arg > low & arg < high;
    }
//    /-------------------------------

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }

    boolean include2(int arg){
        return arg > getLow() & arg < getHigh();
    }
}
