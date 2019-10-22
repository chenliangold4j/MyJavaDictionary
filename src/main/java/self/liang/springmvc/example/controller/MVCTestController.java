package self.liang.springmvc.example.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.liang.mybatis.example.base.Employee;
import self.liang.mybatis.example.base.dao.EmployeeMapper;
import self.liang.springmvc.example.ConsoleAllBean;
import self.liang.springmvc.example.dao.Employee2Mapper;
import self.liang.springmvc.example.entity.Employee2;
import self.liang.springmvc.example.otherbean.MVCTestService;

@Controller
public class MVCTestController {

    @Autowired
    MVCTestService mvcTestService;

    @Autowired
    Employee2Mapper employee2Mapper;

    @RequestMapping("/testSuccess")
    @ResponseBody
    public String test() throws Exception {
        Employee2 employee = employee2Mapper.getEmpById(1);
        System.out.println(employee);
        return mvcTestService.goSuccess();
    }


//    @RequestMapping("/testMybatis")
//    @ResponseBody
//    public Employee testMybatis() {
//        Employee employee = employeeMapper.getEmpById(1);
//        return employee;
//    }

}
