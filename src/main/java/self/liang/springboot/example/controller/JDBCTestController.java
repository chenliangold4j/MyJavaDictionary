package self.liang.springboot.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.liang.springboot.example.entity.Employee;
import self.liang.springboot.example.mapper.Employee2Mapper;
import self.liang.springboot.example.mapper.EmployeeMapper;

import java.util.List;
import java.util.Map;

@Controller
public class JDBCTestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    Employee2Mapper employee2Mapper;

    @ResponseBody
    @RequestMapping("/Query")
    public Map<String,Object> map(){
        List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT  * from employee");
        return list.get(0);
    }
//
    @ResponseBody
    @RequestMapping("/getEmp")
    public Employee getEmp(){
        Employee employee = employeeMapper.getById(1);
        return employee;
    }

    @ResponseBody
    @RequestMapping("/getEmp2")
    public Employee getEmp2(){
        Employee employee = employee2Mapper.getEmpById(1);
        return employee;
    }
}
