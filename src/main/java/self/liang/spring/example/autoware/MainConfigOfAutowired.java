package self.liang.spring.example.autoware;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("self.liang.spring.example.autoware")
public class MainConfigOfAutowired {

    @Primary//未指定bean.则会装备这个bean
    @Bean
    BookDao bookDao2(){
        BookDao bookDao = new BookDao();
        bookDao.setId(2);
        return bookDao;
    }

    @Primary
    @Bean
    BookService bookService2(){
        BookService bookService = new BookService();
        bookService.setId(2);
        return bookService;
    }

    @Bean
    Boss boss2(@Autowired  Secretary secretary){
        System.out.print("boss2init:::");secretary.handleEmail();
        return new Boss(secretary);
    }
}
