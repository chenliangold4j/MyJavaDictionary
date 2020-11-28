package self.liang.leetcode.algorithm.tree;

public class ReversePairs {
    /**
     * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
     * 你需要返回给定数组中的重要翻转对的数量。
     * 示例 1:
     * 输入: [1,3,2,3,1]
     * 输出: 2
     * 示例 2:
     * 输入: [2,4,3,5,1]
     * 输出: 3
     * 注意:
     * 给定数组的长度不会超过50000。
     * 输入数组中的所有数字都在32位整数的表示范围内。
     */


    public static void main(String[] args) {
//        []
        int[] arr = new int[]{1, 3, 2, 3, 1};
        System.out.println(reversePairs(arr));
        int[] arr2 = new int[]{2, 4, 3, 5, 1};
        System.out.println(reversePairs(arr2));
        int[] arr3 = new int[]{2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647};
        System.out.println(reversePairs(arr3));
    }


    public static int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return myReversePairsByMergeSort(nums, 0, nums.length - 1);
    }


    public static int myReversePairsByMergeSort(int[] nums, int left, int right) {
        if (left == right) return 0;
        int mid = (left + right) / 2;
        int resultLeft = myReversePairsByMergeSort(nums, left, mid);
        int resultRight = myReversePairsByMergeSort(nums, mid + 1, right);

        int result = resultLeft + resultRight;

        int i = left;
        int j = mid + 1;
        while (i <= mid) {
//            给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
            while (j < right && (long) nums[i] > 2 * (long) nums[j]) {
                j++;
            }
            result += (j - mid - 1);
            i++;
        }
        int[] temp = new int[right - left + 1];
        i = left;
        j = mid + 1;
        int k = 0; //临时数组下标
        //将两部分中其中一部分完全放入临时数组，然后再将剩余放入临时数组
        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= right) {
            temp[k++] = nums[j++];
        }
        // 把新数组中的数覆盖原数组
        for (int x = 0; x < temp.length; x++) {
            nums[x + left] = temp[x];
        }
        return result;
    }


    //超时
//    public static int reversePairs(int[] nums) {
//        int count = 0;
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                long in1 = nums[i];
//                long in2 = nums[j];
//                long in3 = in2 * 2;
//                if (in1 > in3) {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }

//    public static int reversePairs(int[] nums) {
//        if (nums.length == 0) {
//            return 0;
//        }
//        return reversePairsRecursive(nums, 0, nums.length - 1);
//    }
//
//    public static int reversePairsRecursive(int[] nums, int left, int right) {
//        if (left == right) {
//            return 0;
//        } else {
//            int mid = (left + right) / 2;
//            int n1 = reversePairsRecursive(nums, left, mid);
//            int n2 = reversePairsRecursive(nums, mid + 1, right);
//            int ret = n1 + n2;
//
//            // 首先统计下标对的数量
//            int i = left;
//            int j = mid + 1;
//            while (i <= mid) {
//                while (j <= right && (long) nums[i] > 2 * (long) nums[j]) {
//                    j++;
//                }
//                ret += j - mid - 1;
//                i++;
//            }
//
//            // 随后合并两个排序数组
//            int[] sorted = new int[right - left + 1];
//            int p1 = left, p2 = mid + 1;
//            int p = 0;
//            while (p1 <= mid || p2 <= right) {
//                if (p1 > mid) {
//                    sorted[p++] = nums[p2++];
//                } else if (p2 > right) {
//                    sorted[p++] = nums[p1++];
//                } else {
//                    if (nums[p1] < nums[p2]) {
//                        sorted[p++] = nums[p1++];
//                    } else {
//                        sorted[p++] = nums[p2++];
//                    }
//                }
//            }
//            for (int k = 0; k < sorted.length; k++) {
//                nums[left + k] = sorted[k];
//            }
//            return ret;
//        }
//    }


}
