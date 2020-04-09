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
import java.util.stream.Stream;

/**
 * @author lee549
 * @date 2020/3/25 13:48
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee>
    implements EmployeeService {

  @Resource private EmployeeDao employeeDao;

  @Override
  public void deleteRelation(Long id) {
    employeeDao.deleteRelation(id);
  }

  @Override
  public void insertRelation(Long id, Long ids) {
    employeeDao.insertRelation(id, ids);
  }

  // @Override
  // public Map<String, Object> listPage(Page<Employee> page, Employee employee) {
  //   QueryWrapper<Employee> qw = new QueryWrapper<>();
  //
  //   // 模糊搜索，以此类推
  //   if (StrUtil.isNotBlank(employee.getName())) {
  //     qw.lambda()
  //         .like(Employee::getName, employee.getName())
  //         .or()
  //         .like(Employee::getEmail, employee.getEmail());
  //   }
  //   // 部门
  //   else if (ObjectUtil.isNotNull(employee.getDeptId())){
  //      qw.eq("dept_id", employee.getDeptId());
  //   }
  //   IPage<Employee> pageInfo = this.employeeDao.selectPage(page, qw);
  //   return PageUtil.toMap(pageInfo);
  // }
  @Override
  public Map<String, Object> listPage(Page<Employee> page, Employee employee) {
    QueryWrapper<Employee> qw = new QueryWrapper<>();

    if (StrUtil.isNotBlank(employee.getName())) {
      qw.like("e.name", employee.getName()).or().like("e.email", employee.getEmail());
    }
    // 部门
    else if (ObjectUtil.isNotNull(employee.getDeptId())) {
      qw.eq("dept_id", employee.getDeptId());
    }
    IPage<Employee> pageInfo = employeeDao.empList(page, qw);

    return PageUtil.toMap(pageInfo);
  }

  @Override
  public Employee selectByName(String username) {
    QueryWrapper<Employee> qw = new QueryWrapper<>();
    qw.eq("name",username);
    return employeeDao.selectOne(qw);
  }

  @Override
  public void saveOrUpdate(Employee employee, Long[] ids) {
    this.saveOrUpdate(employee);
    // 没勾选管理员才操作
    if (!employee.getAdmin()) {
      // 删除旧联系
      employeeDao.deleteRelation(employee.getId());
      if (ids != null) {
        Stream.of(ids).forEach(e -> employeeDao.insertRelation(employee.getId(), e));
      }
    }
  }
}
