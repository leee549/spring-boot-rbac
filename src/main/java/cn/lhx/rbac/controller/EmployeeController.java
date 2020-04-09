package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.DepartmentService;
import cn.lhx.rbac.service.EmployeeService;
import cn.lhx.rbac.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/3/25 20:19
 */
@Controller
public class EmployeeController {
  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
  @Resource EmployeeService employeeService;
  @Resource DepartmentService departmentService;
  @Resource RoleService roleService;

  @RequiresPermissions("employee:list")
  @GetMapping("emp/list")
  public String list() {
    return "emp/list";
  }

  @RequiresPermissions("employee:input")
  @GetMapping("/emp")
  public String toInput() {
    return "/emp/input";
  }
  /**
   * 查询分页
   *
   * @param page 分页参数 搜索模糊信息
   * @param
   * @return
   */
  @RequiresPermissions("employee:list")
  @ResponseBody
  @GetMapping("/select")
  public JsonResult<Object> select(Page<Employee> page, Employee employee) {

    // 数据必须返回一个List集合
    Map<String, Object> result = employeeService.listPage(page, employee);
    return JsonResult.success(result);
  }

  @RequiresPermissions("employee:input")
  @ResponseBody
  @GetMapping("/emp/input")
  public JsonResult<Object> input() {

    List<Department> listDept = departmentService.list();
    List<Role> listRole = roleService.list();
    Map<String, Object> result = new HashMap<>(3);
    result.put("dept", listDept);
    result.put("role", listRole);
    return JsonResult.success(result);
  }

  @RequiresPermissions("employee:input")
  @ResponseBody
  @GetMapping("/emp/getRoles")
  public JsonResult<Object> getRoles(@RequestParam("id") Long id) {
    Map<String, Object> result = new HashMap<>(3);
    List<Role> empRoles = roleService.getByEmpId(id);
    result.put("empRoles", empRoles);

    return JsonResult.success(result);
  }

  /**
   * 删除
   *
   * @param id 主键id
   * @return
   */
  @RequiresPermissions("employee:delete")
  @ResponseBody
  @DeleteMapping("/del/")
  public JsonResult<Object> del(@RequestParam("id") Long id) {
    boolean result = this.employeeService.removeById(id);
    return JsonResult.success(result);
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @RequiresPermissions("employee:delete")
  @ResponseBody
  @DeleteMapping("/del/emps")
  public JsonResult<Object> delList(@RequestParam("ids") List<Long> ids) {
    boolean result = this.employeeService.removeByIds(ids);
    return JsonResult.success(result);
  }

  /**
   * 提交
   *
   * @param employee 提交的实体
   * @return
   */
  @RequiresPermissions("employee:saveOrUpdate")
  @ResponseBody
  @RequestMapping("/saveOrUpdate")
  public JsonResult<Object> saveOrUpdate(
      Employee employee, @RequestParam(value = "ids", required = false) Long[] ids) {
    employeeService.saveOrUpdate(employee, ids);
    return JsonResult.success();
  }
}
