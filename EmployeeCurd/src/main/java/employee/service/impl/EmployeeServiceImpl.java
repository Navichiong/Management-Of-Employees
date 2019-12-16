package employee.service.impl;

import employee.dao.EmployeeMapper;
import employee.pojo.Employee;
import employee.pojo.EmployeeExample;
import employee.service.EmployeeService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public List<Employee> handleGetEmps() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Override
    public int handleAddEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    @Override
    public boolean handleCheckEmpnameUsed(String empname) {

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpnameEqualTo(empname);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }

    @Override
    public Employee handleGetEmp(Integer id) {

        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int handleUpdateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public int handleDeleteEmp(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int handleDeleteEmps(List<Integer> ids) {

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return employeeMapper.deleteByExample(example);
    }

}
