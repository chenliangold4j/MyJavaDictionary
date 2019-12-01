package self.liang.springboot.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.liang.springboot.example.collection.ioc.ColKeeper;
import self.liang.springboot.example.entity.Person;

import javax.sql.DataSource;

@Controller
@RequestMapping("/Test")
public class LogTestController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;


    @Autowired
    Person person;

    @Autowired
    ColKeeper colKeeper;

    @RequestMapping("/a")
    @ResponseBody
    public String test1() {
        logger.error("my error test");
        logger.warn("my warn test");
        logger.info("info test");//默认info
        logger.debug("debug test");
        logger.trace("trace");
        return logger.getClass().toString();
    }

    @RequestMapping("/b")
    @ResponseBody
    public Person test2() {
        return person;
    }


    @RequestMapping("/c")
    @ResponseBody
    public String test3() {
        logger.info("注入的集合数量："+ colKeeper.getVipTypes().size());
        logger.info(colKeeper.getVipTypes().get(0).getClass().toString());
        logger.info(colKeeper.getVipTypes().get(1).getClass().toString());
        return "test";
    }

    @RequestMapping("/d")
    @ResponseBody
    public String test4() {
        System.out.println(dataSource.getClass().toString());
        return logger.getClass().toString();
    }
}
