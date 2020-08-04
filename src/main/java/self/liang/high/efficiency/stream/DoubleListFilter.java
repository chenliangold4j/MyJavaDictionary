package self.liang.high.efficiency.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DoubleListFilter {

    public static void main(String[] args) {
        demo01();
    }

    public static void demo01() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 1; i < 9; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", "张三丰" + i);
            list.add(map);
        }
        Stream<Map<String, Object>> s1 = list.stream();
        list.stream().forEach(map -> System.out.println(map));

        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        for (int i = 1; i < 5; i++) {
            Map<String, Object> map2 = new HashMap<>();
            map2.put("id", i);
            map2.put("grade", i + 60);
            list2.add(map2);
        }
        list2.stream().forEach(s -> System.out.println(s));
/**
 *  List<Map<Object, Object>> resultList = oneList.stream().map(map -> twoList.stream()
 *                 .filter(m -> Objects.equals(m.get("id"), map.get("id")))
 *                 .findFirst().map(m -> {
 *                     map.putAll(m);
 *                     map.put("grade",90);
 *                     return map;
 *                 }).orElse(null))
 *                 .filter(Objects::nonNull).collect(Collectors.toList());
 */
      /* List<Map<String, Object>> resultList2 = list.stream().map(m->{
                    m.put("grade",0);
                    for (int i=0;i<list2.size();i++){
                        if(m.get("id").equals(list2.get(i).get("id"))){
                            m.put("grade",list2.get(i).get("grade"));
                            break;
                        }
                    }
                    return m;
                }).collect(Collectors.toList());*/
        List<Map<String, Object>> resultList2 = list.stream().map(m -> {
            m.put("grade", 0);
            list2.stream().filter(m2 -> Objects.equals(m.get("id"), m2.get("id"))).forEach(s -> m.put("grade", s.get("grade")));
            return m;
        }).collect(Collectors.toList());
        resultList2.stream().forEach(s -> System.out.println(s));
    }

}
