package employee.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import employee.pojo.Department;
import employee.pojo.Return;
import employee.service.DepartmentService;

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
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // 部门删除
    @ResponseBody
    @RequestMapping(value = "/deleteDept/{deptIds}", method = RequestMethod.DELETE)
    public Return deleteDept(@PathVariable(value = "deptIds") String deptIds) {

        int i;

        if (deptIds.contains("-")) {
            String[] strings = deptIds.split("-");
            List<Integer> list = new LinkedList<>();
            for (String s : strings) {
                list.add(Integer.parseInt(s));
            }
            i = departmentService.deleteMultiDept(list);

        } else {
            i = departmentService.deleteDeptById(Integer.parseInt(deptIds));
        }

        if (i > 0){
            return Return.success();
        }else{
            return Return.error();
        }
    }

    // 部门更新
    @ResponseBody
    @RequestMapping(value = "/updateDept/{deptId}", method = RequestMethod.PUT)
    public Return updateDept(@Valid Department department, BindingResult result) {

        Map<String, Object> map = new HashMap<>();
        if (result.hasErrors()) {

            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Return.error().addData("errorMsg", map);
        } else {

            long l = departmentService.handleGetCountByDeptName(department.getDeptName());
            if (l > 0) {
                map.put("uniqueDept", "部门已存在");
                return Return.error().addData("errorMsg", map);
            } else {
                departmentService.handleUpdateDept(department);
                return Return.success();
            }
        }
    }

    //根据ID获取部门信息
    @RequestMapping(value = "/getDeptById")
    @ResponseBody
    public Return getDeptById(@RequestParam(value = "deptId") Integer deptId) {

        Department department = departmentService.handleGetDeptById(deptId);
        if (department != null) {
            return Return.success().addData("dept", department);
        } else {
            return Return.error();
        }

    }

    // 获取所有部门
    @ResponseBody
    @RequestMapping(value = "/getDepts")
    public Return getDepts() {
        List<Department> departments = departmentService.handleGetDepts();
        return Return.success().addData("depts", departments);
    }

    // 分页查询部门信息
    @RequestMapping(value = "/getDeptsByPageNum")
    @ResponseBody
    public Return getDeptsBypageNum(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        PageHelper.startPage(pageNum, 5);
        List<Department> departments = departmentService.handleGetDepts();
        PageInfo<Department> pageInfo = new PageInfo<>(departments, 5);
        return Return.success().addData("pageInfo", pageInfo);
    }

    // 部门添加
    @ResponseBody
    @RequestMapping(value = "/addDept", method = RequestMethod.POST)
    public Return addDept(@Valid Department department, BindingResult result) {

        Map<String, Object> map = new HashMap<>();
        if (result.hasErrors()) {

            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Return.error().addData("errorMsg", map);
        } else {

            long l = departmentService.handleGetCountByDeptName(department.getDeptName());
            if (l > 0) {
                map.put("uniqueDept", "部门已存在");
                return Return.error().addData("errorMsg", map);
            } else {
                departmentService.handleAddDepartment(department);
                return Return.success();
            }
        }
    }
}
