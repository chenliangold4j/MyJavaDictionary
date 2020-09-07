package self.liang.high.efficiency.stream;

import com.google.common.collect.Lists;
import com.mongodb.assertions.Assertions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class heibng {
    public static void main(String[] args) {

        long id = 121212L;
        Integer d = null;
        System.out.println(id == d);

    }

    private void test1() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Stream<Integer> another = Stream.of(4, 5, 6);
        Stream<Integer> third = Stream.of(7, 8, 9);
        Stream<Integer> more = Stream.of(0);
        Stream<Integer> concat = Stream.of(stream, another, third, more).
                flatMap(integerStream -> integerStream);
        List<Integer> collect = concat.distinct().collect(Collectors.toList());
        System.out.println(collect);
    }
}
