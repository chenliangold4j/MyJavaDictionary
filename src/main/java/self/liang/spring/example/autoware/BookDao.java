package self.liang.spring.example.autoware;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
    private int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void selectByid(){
        System.out.println("查询到sss"+id);
    }
}
