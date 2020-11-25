package self.liang;

import java.util.HashSet;
import java.util.Random;

public class TestMain {
    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            set.add(random.nextInt());
        }

        for (Integer i : set) {
            System.out.println(i.intValue());
        }
    }
}
