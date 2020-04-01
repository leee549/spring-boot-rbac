package cn.lhx.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.EmployeeDao;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.service.EmployeeService;
import cn.lhx.rbac.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/3/25 13:48
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee>
    implements EmployeeService {

  @Resource private EmployeeDao employeeDao;

  /**
   * 不能这样
   *
   * @param employee
   * @return
   */
  private boolean isNotEmpty(Employee employee) {

    return StrUtil.isNotBlank(employee.getEmail())
        || StrUtil.isNotBlank(employee.getName())
        // 这个为空上面不为空的话它也会加入sql判断
        || ObjectUtil.isNotEmpty(employee.getDeptId());
  }

  @Override
  public Map<String, Object> listPage(Page<Employee> page, Employee employee) {
    QueryWrapper<Employee> qw = new QueryWrapper<>();

    // 模糊搜索，以此类推
    if (StrUtil.isNotBlank(employee.getName())) {
      qw.lambda()
          .like(Employee::getName, employee.getName())
          .or()
          .like(Employee::getEmail, employee.getEmail());
    }
    // 部门
    else if (ObjectUtil.isNotNull(employee.getDeptId())){
       qw.eq("dept_id", employee.getDeptId());
    }
    IPage<Employee> pageInfo = this.employeeDao.selectPage(page, qw);
    return PageUtil.toMap(pageInfo);
  }
}
