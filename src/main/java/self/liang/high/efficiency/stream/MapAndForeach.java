package self.liang.high.efficiency.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapAndForeach {

    public static void main(String[] args) {

        List<Entity> datas = new ArrayList<>();
        datas.add(new Entity(1,"name1"));
        datas.add(new Entity(2,"name2"));
        datas.add(new Entity(3,"name3"));

        datas.stream().map(e->{
            e.setName("test1");
            return e;
        });
        // 用map的e修改无效
        System.out.println(datas);

        datas.stream().forEach(e->{
            e.setName("test1");
        });
        // 用foreach修改可以
        System.out.println(datas);

        datas.add(new Entity(1,"name1"));
        datas.add(new Entity(2,"name2"));
        datas.add(new Entity(3,"name3"));

        List<Entity> test2 = datas.stream().map(e -> {
            e.setName("test2");
            return e;
        }).collect(Collectors.toList());

        System.out.println(test2);
    }

   static class Entity{
        int id;
        String name;

       @Override
       public String toString() {
           return "Entity{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   '}';
       }

       public Entity(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
