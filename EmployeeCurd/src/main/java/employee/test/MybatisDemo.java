package employee.test;

import employee.dao.DepartmentMapper;
import employee.dao.EmployeeMapper;
import employee.pojo.Department;
import employee.pojo.Employee;
import employee.pojo.EmployeeExample;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class MybatisDemo {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSessionTemplate template;

    @Test
    public void test() {
        System.out.println(departmentMapper);
        departmentMapper.insertSelective(new Department(1, "后端开发"));
        departmentMapper.insertSelective(new Department(2, "前端开发"));
        departmentMapper.insertSelective(new Department(3, "运维"));
        departmentMapper.insertSelective(new Department(4, "测试"));
    }

    @Test
    public void test2() {
        if (employeeMapper != null) {

        }
    }

    @Test
    public void test3() {
        if (template != null) {
            EmployeeMapper mapper = template.getMapper(EmployeeMapper.class);
            for (int i = 0; i < 1000; i++) {
                String uuid = UUID.randomUUID().toString().substring(0, 5) + i;
                mapper.insertSelective(new Employee(null, uuid, uuid + "@gdut.com", 1, 1));
            }
            System.out.println("批量插入完成");
        }
    }

    @Test
    public void test4(){
        List<Employee> employees = employeeMapper.selectByExample(null);
        System.out.println(employees.size());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void test5(){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andDIdEqualTo(4);

        int i = employeeMapper.updateByExample(null, example);
        System.out.println(i);
    }
}
