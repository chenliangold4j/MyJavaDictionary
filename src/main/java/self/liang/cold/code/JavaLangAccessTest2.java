package self.liang.cold.code;

import sun.misc.SharedSecrets;

import java.util.EnumSet;

/**
 * A repository of "shared secrets", which are a mechanism for calling implementation-private methods in another package without using reflection.
 *
 * “共享秘密”的存储库，这是一种在另一个包中调用实现私有方法而不使用反射的机制。
 *
 * 这里修改了不可变的枚举类，，可能会导致崩盘
 * 该方法返回相同的数组，而不反射或复制/克隆基础数组。这样可以提高性能，但是公开可变数组并不是一个好主意
 */
public class JavaLangAccessTest2 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++)
            System.out.println(SharedSecrets.getJavaLangAccess().getEnumConstantsShared(ReturnCode.class));
        ReturnCode[] ams = SharedSecrets.getJavaLangAccess().getEnumConstantsShared(ReturnCode.class);
        ams[0] = ams[1]; // don't do this !!
        System.out.println(EnumSet.allOf(ReturnCode.class));
    }
}

enum ReturnCode{

    TINY,BOB;

    public ReturnCode[] getValues() {
        return SharedSecrets.getJavaLangAccess().getEnumConstantsShared(ReturnCode .class);
    }

}
