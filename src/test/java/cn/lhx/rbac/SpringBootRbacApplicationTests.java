package cn.lhx.rbac;

import cn.lhx.rbac.dao.EmployeeDao;
import cn.lhx.rbac.entity.Employee;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootRbacApplicationTests {
    @Resource
    EmployeeDao employeeDao;

    @Test
    void contextLoads() {
        Employee employee = employeeDao.selectById(1);
        System.out.println(employee);
    }
    // @Test
    // void listAll(){
    //     IPage<Employee> emps = employeeDao.selectPage(new Page<>(1,2),null);
    //     emps.forEach(emp->System.out::println);
    //
    // }


}
