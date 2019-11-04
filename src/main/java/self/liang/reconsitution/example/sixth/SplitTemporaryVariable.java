package self.liang.reconsitution.example.sixth;

/**
 * 分解临时变量
 * <p>
 * 某个临时变量被赋值多次
 * 既不是循环变量也不是收集计算结果：
 * 这时候就可以
 *
 * 类似单一职责
 *
 * 更重要的是。。分开之后。。又可以用extractMethod来重构
 */
public class SplitTemporaryVariable {

    int height = 4;
    int width = 8;

    public void test1() {
        double temp = 21 * height * width;
        System.out.println(temp);
        temp = height * width;
        System.out.println(temp);
    }

    public void test2() {
        final double preimeter = 21 * height * width;
        System.out.println(preimeter);
        final double area = height * width;
        System.out.println(area);
    }

}
