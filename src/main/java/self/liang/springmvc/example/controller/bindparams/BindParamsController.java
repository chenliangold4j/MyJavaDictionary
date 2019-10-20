package self.liang.springmvc.example.controller.bindparams;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 探究springMVC数据绑定逻辑
 *      OncePerRequestFilter:的doFilter方法
 *              先判断请求是不是http请求
 *              再判断要不要执行这个filter
 *              doFilterInternal(httpRequest, httpResponse, filterChain);
 *
 *
 *
 *
 */

@Controller
@RequestMapping("/bindparams")
public class BindParamsController {

    @Autowired
    Car car;

    @ResponseBody
    @RequestMapping("/register")
    public String bindtest1(String account,String username,String password,Integer age){
        System.out.println(account+":"+username+":"+password+":"+age);
        return age.toString();
    }

    @ResponseBody
    @RequestMapping("/testPojo")
    public Car testPojo(UserBind userBind){
        System.out.println(userBind);
        System.out.println(car);
        return car;
    }


    @ResponseBody
    @RequestMapping("/testPojoPlus")
    public String testPojoPlus(UserBind userBind){
        System.out.println(userBind);
        return userBind.toString();
    }

    @ResponseBody
    @RequestMapping("/bindDate")
    public String bindDate(Date date){
        System.out.println(date);
        return date.toString();
    }



}
