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
import self.liang.springmvc.example.dao.EmployeeG2Mapper;
import self.liang.springmvc.example.entity.Employee2;
import self.liang.springmvc.example.entity.EmployeeG2;
import self.liang.springmvc.example.entity.EmployeeG2Example;
import self.liang.springmvc.example.otherbean.MVCTestService;

import java.util.List;

@Controller
public class MVCTestController {

    @Autowired
    MVCTestService mvcTestService;

    @Autowired
    Employee2Mapper employee2Mapper;


    @Autowired
    EmployeeG2Mapper employeeG2Mapper;

    @RequestMapping("/testSuccess")
    @ResponseBody
    public String test() throws Exception {
        Employee2 employee = employee2Mapper.getEmpById(1);
        System.out.println(employee);
        return mvcTestService.goSuccess();
    }

    /**
     *
     * @return
     */
    @RequestMapping("/testMybatis")
    @ResponseBody
    public List<EmployeeG2> testMybatis() {
        EmployeeG2Example employeeG2Example = new EmployeeG2Example();
        EmployeeG2Example.Criteria criteria = employeeG2Example.createCriteria();
        criteria.andLastNameLike("%e%");
        criteria.andGenderEqualTo("1");
        EmployeeG2Example.Criteria criteria2 = employeeG2Example.createCriteria();
        criteria2.andEmailLike("%e%");
        employeeG2Example.or(criteria2);

        List<EmployeeG2> employeeG2List = employeeG2Mapper.selectByExample(employeeG2Example);
        return employeeG2List;
    }

}
