package self.liang.high.efficiency.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class StreamInCommonUse {


//            +--------------------+       +------+   +------+   +---+   +-------+
//            | stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
//            +--------------------+       +------+   +------+   +---+   +-------+

    public static void main(String[] args) {
        Integer[] nums2 = {1, 2, 3};
        Integer[] nums3 = {3, 4};
        List<Integer> nums2List = Arrays.asList(nums2);
        List<Integer> nums3List = Arrays.asList(nums3);

        //使用2个map嵌套过滤
        //有二箱鸡蛋，每箱5个，现在要把鸡蛋加工成煎蛋，然后分给学生。
        //
        //map做的事情：把二箱鸡蛋分别加工成煎蛋，还是放成原来的两箱，分给2组学生；
        //
        //flatMap做的事情：把二箱鸡蛋分别加工成煎蛋，然后放到一起【10个煎蛋】，分给10个学生；
        List<int[]> res2 = nums2List.stream().flatMap(i -> nums3List.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());

        List<Stream<int[]>> collect = nums2List.stream().map(i -> nums3List.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());


        res2.forEach(item ->{
            System.out.println(Arrays.toString(item));
        });

    }

    public void test4(){

//        flatMap 中间操作:
//
//        可用 Stream 替换值，并将多个 Stream 流合并成一个 Stream 流。
        List<Integer> list = (List<Integer>) Stream.of(Arrays.asList(1, 2, 3, 4, 5, 6), Arrays.asList(8, 9, 10, 11, 12))
                .flatMap(Collection::stream).collect(Collectors.toList());

        for (int i = 0, length = list.size(); i < length; i++) {
            System.out.println(list.get(i));
        }


        //扁平化流
        //找出数组中唯一的字符
        String[] strArray = {"hello", "world"};

        //具体实现
        List<String> res = Arrays.stream(strArray)
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(res);

        //TODO 案例
        System.out.println("--------------------------------");
        //Demo1:给定数组，返回数组平方和(直接使用映射)
        //[1,2,3,4]=>[1,4,9,16]
        Integer[] nums1 = {1, 2, 3, 4};
        List<Integer> nums1List = Arrays.asList(nums1);
        List<Integer> res1 = nums1List.stream().map(i -> i * i).collect(Collectors.toList());
        System.out.println(res1);

        System.out.println("--------------------------------");
        //Demo2:给定两数组，返回数组对
        //[1,2,3],[3,4]=>[1,3],[1,4],[2,3],[2,4],[3,3],[3,4]
        Integer[] nums2 = {1, 2, 3};
        Integer[] nums3 = {3, 4};
        List<Integer> nums2List = Arrays.asList(nums2);
        List<Integer> nums3List = Arrays.asList(nums3);

        //使用2个map嵌套过滤
        List<int[]> res2 = nums2List.stream().flatMap(i -> nums3List.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
        System.out.println(res2.size());

        System.out.println("--------------------------------");
        //Demo3:针对Demo2和Demo1组合返回总和能被3整除的数对
        //(2,4)和(3,3)是满足条件的
        List<int[]> res3 = nums2List.stream().flatMap(i -> nums3List.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j})).collect(Collectors.toList());
        System.out.println(res3.size());

    }

    public void test3(){

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");

        int asInt = strings.parallelStream().mapToInt(String::length).max().getAsInt();
        System.out.println(asInt);

        List<String> list = Arrays.asList("Geeks", "GFG",
                "GeeksforGeeks", "gfg");

        // Using Stream flatMapToDouble(Function mapper)
        // to get length of all strings present in list
        list.stream().flatMapToDouble(str
                -> DoubleStream.of(str.length()))
                .forEach(System.out::println);

    }

    public void test2() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
// 获取空字符串的数量
        Random random = new Random();
        long count = strings.parallelStream().filter(string -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return string.isEmpty();
        }).count();
        System.out.println(count);
        String collect = String.join(",", strings);
        System.out.println(collect);
    }

    public void test1() {
        List<String> list = new ArrayList<>();
        Random random = new Random();
        random.ints().limit(100).forEach(num -> {
            System.out.println(num);
            list.add(Integer.toString(num));
        });

        System.out.println(Arrays.toString(list.stream().filter(str -> Integer.parseInt(str) > 0).toArray()));
    }
}
