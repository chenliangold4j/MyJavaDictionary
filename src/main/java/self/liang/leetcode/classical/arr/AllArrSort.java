package self.liang.leetcode.classical.arr;

import java.util.Arrays;

public class AllArrSort {


    public static void main(String[] args) {
        int[] arr = new int[]{23, 11, -121, 1231, 44, -123123, -1, 23, 0, 55};
        System.out.println(Arrays.toString(sort(arr)));
        arr = new int[]{23, 11, -121, 1231, 44, -123123, -1, 23, 0, 55};
        System.out.println(Arrays.toString(mergeSort(arr, 0, arr.length - 1)));

        arr = new int[]{23, 11, -121, 1231, 44, -123123, -1, 23, 0, 55};
        System.out.println(Arrays.toString(quickSort(arr, 0, arr.length - 1)));
    }

    //1.简单冒泡
    public static int[] sort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    //swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
    //2. 归并排序

    /**
     * 将左边的排序，将右边的排序，将排序的合并；
     */
    public static int[] mergeSort(int[] arr, int low, int high) {
        int mid = (low + high) / 2;
//        System.out.println(low + " - " + high);
        if (low >= high) {
            return arr;
        }

        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high);
        return arr;
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];

        int i = low;
        int j = mid + 1;
        int k = 0; //临时数组下标
        //将两部分中其中一部分完全放入临时数组，然后再将剩余放入临时数组
        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= high) {
            temp[k++] = arr[j++];
        }
        // 把新数组中的数覆盖原数组
        for (int x = 0; x < temp.length; x++) {
            arr[x + low] = temp[x];
        }
    }

    //3.快速排序
    public static int[] quickSort(int[] arr, int low, int high) {

        //如果low等于high，即数组只有一个元素，直接返回
        if (low >= high) {
            return arr;
        }
        //设置最左边的元素为基准值
        int key = arr[low];
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i = low;
        int j = high;
        while (i < j) {
            //j向左移，直到遇到比key小的值
            while (arr[j] >= key && i < j) {
                j--;
            }
            //i向右移，直到遇到比key大的值
            while (arr[i] <= key && i < j) {
                i++;
            }
            //i和j指向的元素交换
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[low] = arr[i];
        arr[i] = key;
        quickSort(arr, low, i - 1);
        quickSort(arr, i + 1, high);
        return arr;
    }

    // 4 堆排序



    /**
     * 创建堆，
     *
     * @param arr 待排序列
     */
    private static void heapSort(int[] arr) {
        //创建堆
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }

        //调整堆结构+交换堆顶元素与末尾元素
        for (int i = arr.length - 1; i > 0; i--) {
            //将堆顶元素与末尾元素进行交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            //重新对堆进行调整
            adjustHeap(arr, 0, i);
        }
    }

    /**
     * 调整堆
     *
     * @param arr    待排序列
     * @param parent 父节点
     * @param length 待排序列尾元素索引
     */
    private static void adjustHeap(int[] arr, int parent, int length) {
        //将temp作为父节点
        int temp = arr[parent];
        //左孩子
        int lChild = 2 * parent + 1;

        while (lChild < length) {
            //右孩子
            int rChild = lChild + 1;
            // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if (rChild < length && arr[lChild] < arr[rChild]) {
                lChild++;
            }

            // 如果父结点的值已经大于孩子结点的值，则直接结束
            if (temp >= arr[lChild]) {
                break;
            }

            // 把孩子结点的值赋给父结点
            arr[parent] = arr[lChild];

            //选取孩子结点的左孩子结点,继续向下筛选
            parent = lChild;
            lChild = 2 * lChild + 1;
        }
        arr[parent] = temp;
    }

}
