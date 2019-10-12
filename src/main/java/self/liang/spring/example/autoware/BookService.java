package self.liang.spring.example.autoware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private  int id = 0;

    @Qualifier("bookDao")
    @Autowired(required =  false)//不必须
    BookDao bookDao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

