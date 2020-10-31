package self.liang.leetcode.algorithm.collection;

import java.util.*;

/**
 * 设计一个支持在平均 时间复杂度 O(1) 下， 执行以下操作的数据结构。
 * <p>
 * 注意: 允许出现重复元素。
 * <p>
 * insert(val)：向集合中插入元素 val。
 * remove(val)：当 val 存在时，从集合中移除一个 val。
 * getRandom：从现有集合中随机获取一个元素。每个元素被返回的概率应该与其在集合中的数量呈线性相关。
 * 示例:
 * <p>
 * // 初始化一个空的集合。
 * RandomizedCollection collection = new RandomizedCollection();
 * <p>
 * // 向集合中插入 1 。返回 true 表示集合不包含 1 。
 * collection.insert(1);
 * <p>
 * // 向集合中插入另一个 1 。返回 false 表示集合包含 1 。集合现在包含 [1,1] 。
 * collection.insert(1);
 * <p>
 * // 向集合中插入 2 ，返回 true 。集合现在包含 [1,1,2] 。
 * collection.insert(2);
 * <p>
 * // getRandom 应当有 2/3 的概率返回 1 ，1/3 的概率返回 2 。
 * collection.getRandom();
 * <p>
 * // 从集合中删除 1 ，返回 true 。集合现在包含 [1,2] 。
 * collection.remove(1);
 * <p>
 * // getRandom 应有相同概率返回 1 和 2 。
 * collection.getRandom();
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/insert-delete-getrandom-o1-duplicates-allowed
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RandomizedCollection {
    //    这里边的最大技巧点在于，删除是利用位置交换+pop列表最后一位
    int n;//当前集合大小
    HashMap<Integer, Set<Integer>> map;
    ArrayList<Integer> list;
    Random random;

    /**
     * Initialize your data structure here.
     */
    public RandomizedCollection() {
        this.random = new Random();
        this.map = new HashMap();
        this.n = 0;
        this.list = new ArrayList<>();
    }

    /**
     * Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
     */
    public boolean insert(int val) {
        Set set = map.get(val);
        if (set == null) set = new HashSet<>();
        set.add(n);//添加索引
        list.add(val);
        map.put(val, set);
        n++;
        return set.size() == 1;
    }

    /**
     * Removes a value from the collection. Returns true if the collection contained the specified element.
     */
    public boolean remove(int val) {
        if (map.containsKey(val)) {
            int lastIndex = n - 1;//得到最后值索引
            Set lastset = map.get(list.get(lastIndex));
            Set set = map.get(val);
            int currIndex = (int) set.iterator().next();//得到当前值索引
            //进行删除操作
            swap(list, currIndex, lastIndex);
            list.remove(n - 1);//将其在列表中删除
            set.remove(currIndex);//删除原值
            if (set.size() == 0) map.remove(val);//在图中删除
            //修改最后一个值的索引
            lastset.remove(n - 1);
            lastset.add(currIndex);
            n--;
        } else {
            return false;
        }
        return true;
    }

    /**
     * Get a random element from the collection.
     */
    public int getRandom() {
        return list.get(random.nextInt(n));
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void main(String[] args) {
        RandomizedCollection randomizedCollection = new RandomizedCollection();
        randomizedCollection.insert(1);
        randomizedCollection.insert(2);
        randomizedCollection.insert(1);
        randomizedCollection.insert(3);
        randomizedCollection.insert(5);
        randomizedCollection.remove(3);
    }
}
