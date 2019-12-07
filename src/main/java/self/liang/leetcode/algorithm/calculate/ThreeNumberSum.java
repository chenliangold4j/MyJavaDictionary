package self.liang.leetcode.algorithm.calculate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，
 * 使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
public class ThreeNumberSum {

    public static void main(String[] args) {
        ThreeNumberSum threeNumberSum = new ThreeNumberSum();
        int[] nums = {0,0,0,0};
        System.out.println(threeNumberSum.threeSum(nums));
    }

    //自己写的话，首先是去重。。一种直接去重，一种是排序。  排序之后，只需要判断到小于0
    //然后遍历相加判断。
    //在两数确定的情况下，只有一个数能使得相加为0
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);


        return null;
    }

    private int count;

}
