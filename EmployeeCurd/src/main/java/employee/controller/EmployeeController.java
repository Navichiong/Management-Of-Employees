package employee.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import employee.pojo.Employee;
import employee.pojo.Return;
import employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ResponseBody
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public Return deleteEmp(@PathVariable("ids") String ids) {

        if (ids != null) {
            if (ids.contains("-")) {
                List<Integer> list = new LinkedList<>();
                String[] strings = ids.split("-");
                for (int i = 0; i < strings.length; i++) {
                    list.add(Integer.parseInt(strings[i]));
                }
                employeeService.handleDeleteEmps(list);
            } else {
                employeeService.handleDeleteEmp(Integer.parseInt(ids));
            }
            return Return.success();
        }
        return Return.error();
    }

    @ResponseBody
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.PUT)
    public Return updateEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            Map<String, Object> map = new HashMap<>();
            System.out.println("错误信息如下：");
            for (FieldError fieldError : fieldErrors) {
                System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Return.error().addData("fieldErrors", map);
        } else {
            employeeService.handleUpdateEmp(employee);
            return Return.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/emp/{id}")
    public Return getEmp(@PathVariable(value = "id") Integer id) {
        Employee employee = employeeService.handleGetEmp(id);
        return Return.success().addData("emp", employee);
    }

    @RequestMapping(value = "/checkEmpnameUsed")
    @ResponseBody
    public Return checkEmpnameUsed(@RequestParam("empname") String empname) {

        boolean flag = employeeService.handleCheckEmpnameUsed(empname);
        if (flag) {
            return Return.success();
        } else {
            return Return.error();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public Return addEmp(@Valid Employee employee, BindingResult result) {

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            Map<String, Object> map = new HashMap<>();
            System.out.println("错误信息如下：");
            for (FieldError fieldError : fieldErrors) {
                System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Return.error().addData("fieldErrors", map);
        } else {
            int i = employeeService.handleAddEmp(employee);
            System.out.println("==============>"+i);
            return Return.success();
        }
    }


    //   分页显示员工数据
    @RequestMapping(value = "/getEmps")
    @ResponseBody
    public Return getEmps(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        System.out.println(pageNum);
        // 引入PageHelper插件
        PageHelper.startPage(pageNum, 5);

        List<Employee> employees = employeeService.handleGetEmps();
        // 连续显示5页
        PageInfo<Employee> pageInfo = new PageInfo<>(employees, 5);

        return Return.success().addData("pageInfo", pageInfo);

    }



/*
    @RequestMapping(value = "/getEmps")
    public String getEmps(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {

//        引入PageHelper插件
        PageHelper.startPage(pageNum, 5);

        List<Employee> employees = employeeService.handleGetEmps();
//        连续显示5页
        PageInfo<Employee> pageInfo = new PageInfo<>(employees, 5);

        model.addAttribute("pageInfo", pageInfo);


        return "list";
    }*/
}
