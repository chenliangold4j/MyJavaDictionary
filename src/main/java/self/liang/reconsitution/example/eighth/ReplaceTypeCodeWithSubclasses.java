package self.liang.reconsitution.example.eighth;

/**
 * 以子类取代类型码
 *
 * 你有一个不可变的类型码，
 * 他会影响类的行为
 *
 * 不影响行为的话ReplaceTypeCodeWithClass即可，如果影响行为，多态是个好选择。
 *
 * 这种情况的标志是switch。或者大量if elseif
 *
 * 有两种情况你不能这么做：
 *      1）类型码值再对象创建之后发生了改变
 *      2）类型码的宿主类已经有了子类。
 *
 *      如果是这样，就需要用replace type code with state/strategy  //策略
 *
 *
 *      ReplaceTypeCodeWithSubclasses只要是搭一个舞台
 *      为replace conditional with polymorphism得以一展身手。。虽然我不知道那是什么东西。。- -！~
 */
public class ReplaceTypeCodeWithSubclasses {




}
