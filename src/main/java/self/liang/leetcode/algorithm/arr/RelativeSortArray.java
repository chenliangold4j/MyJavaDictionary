package self.liang.leetcode.algorithm.arr;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RelativeSortArray {
    //    给你两个数组，arr1 和 arr2，
//
//    arr2 中的元素各不相同
//    arr2 中的每个元素都出现在 arr1 中
//    对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
//
//             
//
//    示例：
//
//    输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
//    输出：[2,2,2,1,4,3,3,9,6,7,19]
//             
//
//    提示：
//
//    arr1.length, arr2.length <= 1000
//            0 <= arr1[i], arr2[i] <= 1000
//    arr2 中的元素 arr2[i] 各不相同
//    arr2 中的每个元素 arr2[i] 都出现在 arr1 中
//
    public int[] relativeSortArray(int[] arr1, int[] arr2) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr2.length; i++) {
            map.put(arr2[i], i);
        }
        // Comparator must be generic, so we have to do this
        Integer[] array = new Integer[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            array[i] = arr1[i];
        }
        Arrays.sort(array, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                if (map.containsKey(a) && map.containsKey(b)) {
                    return map.get(a) - map.get(b);
                }
                // a < b
                if (map.containsKey(a)) {
                    return -1;
                }
                // b < a
                if (map.containsKey(b)) {
                    return 1;
                }
                return a - b;
            }
        });
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = array[i].intValue();
        }
        return arr1;
    }
}
