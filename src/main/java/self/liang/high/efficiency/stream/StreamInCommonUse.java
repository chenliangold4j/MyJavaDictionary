package self.liang.high.efficiency.stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamInCommonUse {


//            +--------------------+       +------+   +------+   +---+   +-------+
//            | stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
//            +--------------------+       +------+   +------+   +---+   +-------+

    public static void main(String[] args) {

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
// 获取空字符串的数量
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
        String collect = strings.stream().collect(Collectors.joining(","));
        System.out.println(collect);

    }

    public void test1() {
        List<String> list = new ArrayList<>();
        Random random = new Random();
        random.ints().limit(100).forEach(num -> {
            System.out.println(num);
            list.add(Integer.toString(num));
        });

        List<String> arrs = list.stream().filter(str -> {
            return Integer.parseInt(str) > 0;
        }).collect(Collectors.toList());

        System.out.println(Arrays.toString(arrs.toArray()));
    }
}
