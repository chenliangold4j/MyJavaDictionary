package self.liang.spring.example.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public void insert(){
        String sql ="insert into user(username,age) values(?,?)";
        String uuid = UUID.randomUUID().toString().substring(0,5);
        jdbcTemplate.update(sql,uuid,19);
    }
}
