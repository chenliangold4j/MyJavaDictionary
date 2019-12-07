package self.liang.leetcode.algorithm.string;

/**
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 *      因此返回 INT_MIN (−231) 。
 */
public class StringToNumber {

    char max = '9', min = '0', plus = '+', minus = '-';

    public static void main(String[] args) {
        StringToNumber stringToNumber = new StringToNumber();

        System.out.println(stringToNumber.isExceed(214748364,8,true));

        System.out.println(stringToNumber.myAtoi("-91283472332"));
        System.out.println(stringToNumber.myAtoi("2147483648"));

//        int i = 912834723;//这里注意了。-912834723*10 = -538412638的坑。
//        System.out.println(0-i);
//        System.out.println((0-i)*10);
//        long test = (0-i)*10;
//        System.out.println(test);
//        System.out.println(912834723*10);
//        //当超过一定程度，乘法就无效了。不能用乘法判断。
//
//        // 解决方案一，用long计算
//        long j = 912834723;
//        System.out.println(0-j);
//        System.out.println((0-j)*10);

        //方案二；

    }

    public int myAtoi(String str) {

        str = str.trim();
        char[] chars = str.toCharArray();
        if(chars.length ==0)return 0;
        boolean isPositive = true;
        int index = 0;
        if (chars[0] == plus) {
            index++;
        }
        if (chars[0] == minus) {
            index++;
            isPositive = false;
        }
        return handleNumber(chars,index,isPositive);
    }

    private boolean isExceed(int number, int addNumber,boolean isPositive) {

        //因为乘法判断无效
//        if (isPositive) {
//            if (number * 10 > Integer.MAX_VALUE) return true;
//            else return false;
//        } else {
//            if ((0 - number) * 10 < Integer.MIN_VALUE) return true;
//            else return false;
//        }
        long num = number;
        if(isPositive){
            if (num * 10+addNumber > Integer.MAX_VALUE) return true;
            else return false;
        }else{
            if ((0 - num) * 10-addNumber < Integer.MIN_VALUE) return true;
            else return false;
        }
    }

    private boolean isNumber(char ch) {
        if (ch >= min && ch <= max) return true;
        return false;
    }

    public int handleNumber(char[] chars, int index, boolean isPositive) {
        int result = 0;
        for (int i = index; i < chars.length; i++) {
            if (isNumber(chars[i])) {
                if (isExceed(result,chars[i]-'0', isPositive)) {
                        return isPositive ? Integer.MAX_VALUE:Integer.MIN_VALUE;
                }
                result = (chars[i]-'0')+result*10;
            }else{
               break;
            }
        }
        if(!isPositive){
            result = 0-result;
        }
        return result;
    }

}
