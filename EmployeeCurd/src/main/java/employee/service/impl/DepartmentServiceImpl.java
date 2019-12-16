package employee.service.impl;

import employee.dao.DepartmentMapper;
import employee.dao.EmployeeMapper;
import employee.pojo.Department;
import employee.pojo.DepartmentExample;
import employee.pojo.EmployeeExample;
import employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Department> handleGetDepts() {
        return departmentMapper.selectByExample(null);
    }

    @Override
    public long handleGetCountByDeptName(String deptName) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDeptNameEqualTo(deptName);
        return departmentMapper.countByExample(example);
    }

    @Override
    public Integer handleAddDepartment(Department department) {
        return departmentMapper.insert(department);
    }

    @Override
    public Department handleGetDeptById(Integer deptId) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDepIdEqualTo(deptId);
        return departmentMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public int handleUpdateDept(Department department) {

        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDepIdEqualTo(department.getDepId());

        return departmentMapper.updateByPrimaryKey(department);
    }

    @Transactional
    @Override
    public int deleteDeptById(Integer deptId) {

        // 获取每个部门下的员工数
        EmployeeExample emp_example1 = new EmployeeExample();
        EmployeeExample.Criteria emp_criteria = emp_example1.createCriteria();
        emp_criteria.andDIdEqualTo(deptId);
        long emps = employeeMapper.countByExample(emp_example1);

        // 删除对应部门的员工
        EmployeeExample emp_example2 = new EmployeeExample();
        EmployeeExample.Criteria emp_criteria2 = emp_example2.createCriteria();
        emp_criteria2.andDIdEqualTo(deptId);
        long delEmpsCount = employeeMapper.deleteByExample(emp_example2);

        if (delEmpsCount != emps) {
            throw new RuntimeException("删除失败");
        }
        return departmentMapper.deleteByPrimaryKey(deptId);
    }

    @Transactional
    @Override
    public int deleteMultiDept(List<Integer> deptIds) {
        try {

            for (Integer deptId : deptIds) {
                this.deleteDeptById(deptId);
            }

            return 1;

        } catch (RuntimeException e) {
            return 0;
        }
    }
}
