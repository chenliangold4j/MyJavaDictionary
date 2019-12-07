package self.liang.leetcode.algorithm.sort;

import java.util.Arrays;

/**
 * 给你两个数组，arr1 和 arr2，
 * <p>
 * arr2 中的元素各不相同
 * arr2 中的每个元素都出现在 arr1 中
 * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。
 * 未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
 * <p>
 *  
 * 示例：
 * <p>
 * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
 * 输出：[2,2,2,1,4,3,3,9,6,7,19]
 *  
 */
public class ArrayRelativeSort {

    public static void main(String[] args) {
        ArrayRelativeSort arrayRelativeSort = new ArrayRelativeSort();
        int[] arr1 = new int[]{28, 6, 22, 8, 44, 17};
        int[] arr2 = new int[]{22, 28, 8, 6};

        int[] result = arrayRelativeSort.relativeSortArray(arr1, arr2);

        System.out.println(Arrays.toString(result));
    }

    //第一次没有对未出现的元素做处理。
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int arr1Index = 0;//记录到排序的位置，下一次遍历从这里开始。
        for (int i = 0; i < arr2.length; i++) {
            for (int j = arr1Index; j < arr1.length; j++) {
                if (arr1[j] == arr2[i]) {
                    int tmp = arr1[arr1Index];
                    arr1[arr1Index] = arr1[j];
                    arr1[j] = tmp;
                    arr1Index++;
                }
            }
        }

        int temp;
        for (int i = arr1Index; i < arr1.length; i++) {
            for (int j = arr1Index; j < arr1.length - 1 - (i-arr1Index); j++) {
                if (arr1[j] > arr1[j + 1]) {
                    temp = arr1[j];
                    arr1[j] = arr1[j + 1];
                    arr1[j + 1] = temp;
                }
            }
        }

        return arr1;
    }

    public static void bubbleSort(int[] values) {
        int temp;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length - 1 - i; j++) {
                //减i原因：内层循环，每循环完一趟就在数组末产生一个最大数，即最大数就不用比较了。
                if (values[j] > values[j + 1]) {
                    temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
    }

}
