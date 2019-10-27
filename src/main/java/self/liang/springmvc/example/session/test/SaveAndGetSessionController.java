package self.liang.springmvc.example.session.test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/session")
public class SaveAndGetSessionController {


    @RequestMapping("/save")
    @ResponseBody
    public String saveSession(HttpSession httpSession,String name){
        httpSession.setAttribute("name",name);
        return "saved";
    }


    @RequestMapping("/get")
    @ResponseBody
    public String getSession(HttpSession httpSession){
        return   httpSession.getAttribute("name").toString();
    }
}
