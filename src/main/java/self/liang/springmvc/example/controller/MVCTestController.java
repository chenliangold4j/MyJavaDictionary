package self.liang.springmvc.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.liang.springmvc.example.otherbean.MVCTestService;

@Controller
public class MVCTestController {

    @Autowired
    MVCTestService mvcTestService;

    @RequestMapping("/testSuccess")
    @ResponseBody
    public String test()
    {
        return mvcTestService.goSuccess();
    }

}
