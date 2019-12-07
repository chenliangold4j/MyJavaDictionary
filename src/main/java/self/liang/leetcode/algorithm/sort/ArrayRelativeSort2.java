package self.liang.leetcode.algorithm.sort;

import java.util.Arrays;

/**
 * 效率不行，看看别人的。
 *
 * 天秀。。好他妈的吊。。和提示完美契合。
 */
public class ArrayRelativeSort2 {

    public static void main(String[] args) {
        ArrayRelativeSort2 arrayRelativeSort = new ArrayRelativeSort2();
        int[] arr1 = new int[]{28, 6, 22, 8, 29, 17,1,6,8,11,24,22};
        int[] arr2 = new int[]{22, 28, 8, 6};

        int[] result = arrayRelativeSort.relativeSortArray(arr1, arr2);

        System.out.println(Arrays.toString(result));
    }


    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] nums = new int[30];
        int[] res = new int[arr1.length];
        //遍历arr1,统计每个元素的数量
        for (int i : arr1) {
            nums[i]++;
        }
        System.out.println(Arrays.toString(nums));
        //遍历arr2,处理arr2中出现的元素
        int index = 0;
        for (int i : arr2) {
            while (nums[i]>0){
                res[index++] = i;
                nums[i]--;
            }
        }
        System.out.println(Arrays.toString(res));
        System.out.println(Arrays.toString(nums));
        //遍历nums,处理剩下arr2中未出现的元素
        for (int i = 0; i < nums.length; i++) {
            while (nums[i]>0){
                res[index++] = i;
                nums[i]--;
            }
        }
        return res;
    }
}
