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
//        Content-Type: application/x-www-form-urlencoded
//        username=%E5%9C%B0%E6%96%B9&account=test2323&password=213&age=11&address.city=px&address.province=province&cars%5B0%5D.name=wuling&cars%5B0%5D.price=50000&cars%5B1%5D.name=benchi&cars%5B1%5D.price=5000000&carMap%5B%22one%22%5D.name=baoma&carMap%5B%22one%22%5D.price=78945612
//        carMap["one"].name = baoma 传map
//        cars[1].name = "baoma"传list
//        address.city=px  传bean
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
//        Content-Type: application/x-www-form-urlencoded
//
//        date=2019-10-11
        System.out.println(date);
        return date.toString();
    }



}
