package self.liang.springboot.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.liang.springboot.example.entity.Person;

@Controller
@RequestMapping("/Test")
public class LogTestController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    Person person;


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
}
