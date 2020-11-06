package self.liang.leetcode.algorithm.arr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 给你一个整数数组 arr 。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排序。
 * <p>
 * 如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。
 * <p>
 * 请你返回排序后的数组。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：arr = [0,1,2,3,4,5,6,7,8]
 * 输出：[0,1,2,4,8,3,5,6,7]
 * 解释：[0] 是唯一一个有 0 个 1 的数。
 * [1,2,4,8] 都有 1 个 1 。
 * [3,5,6] 有 2 个 1 。
 * [7] 有 3 个 1 。
 * 按照 1 的个数排序得到的结果数组为 [0,1,2,4,8,3,5,6,7]
 * 示例 2：
 * <p>
 * 输入：arr = [1024,512,256,128,64,32,16,8,4,2,1]
 * 输出：[1,2,4,8,16,32,64,128,256,512,1024]
 * 解释：数组中所有整数二进制下都只有 1 个 1 ，所以你需要按照数值大小将它们排序。
 * 示例 3：
 * <p>
 * 输入：arr = [10000,10000]
 * 输出：[10000,10000]
 * 示例 4：
 * <p>
 * 输入：arr = [2,3,5,7,11,13,17,19]
 * 输出：[2,3,5,17,7,11,13,19]
 * 示例 5：
 * <p>
 * 输入：arr = [10,100,1000,10000]
 * 输出：[10,100,10000,1000]
 * <p>
 */
public class Solution2 {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 5, 7, 11, 13, 17, 19};
    }

    public static int[] sortByBits(int[] arr) {

        List<NumberData> list = new ArrayList<>();
        for (int n : arr) {
            NumberData numberData = new NumberData(n);
            list.add(numberData);
        }

        list.sort(new NumberData(0));
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = list.get(i).num;
        }
        return result;
    }

    static class NumberData implements Comparator<NumberData> {

        int num;
        int count1;

        NumberData(int num) {
            String string = Integer.toBinaryString(num);
            this.num = num;
            this.count1 = countStringNumber1(string);
        }

        private int countStringNumber1(String s) {
            int result = 0;
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if (c == '1') {
                    result++;
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return String.valueOf(num);
        }

        @Override
        public int compare(NumberData o1, NumberData o2) {

            if (o1.count1 > o2.count1) return 1;
            else if (o1.count1 < o2.count1) return -1;
            else if (o1.count1 == o2.count1) {
                if (o1.num > o2.num) return 1;
                else if (o1.num < o2.num) return -1;
                else if (o1.num == o2.num) return 0;
            }
            return 0;
        }
    }

//    循环并使用 Integer.bitCount 计算数字中1的个数，乘以10000000（题目中不会大于 10^4）然后加上原数字，
//    放入数组 map 中，并对 map 进行排序，最后 % 10000000 获取原来的数组，填充到原数组返回即可。


    public int[] sortByBits2(int[] arr) {
        int[] map = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            map[i] = Integer.bitCount(arr[i]) * 10000000 + arr[i];
        }
        Arrays.sort(map);
        for (int i = 0; i < map.length; i++) {
            map[i] = map[i] % 10000000;
        }
        return map;
    }


}
