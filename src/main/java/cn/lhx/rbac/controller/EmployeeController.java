package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

  @GetMapping("/list")
  public String list() {
    return "emp/list";
  }

  @RequestMapping("/toLogin")
  public String toLogin() {
    return "login";
  }

  /**
   * 查询分页
   *
   * @param page 分页参数
   * @param employee 搜索模糊信息
   * @return
   */
  @ResponseBody
  @GetMapping("/select")
  public JsonResult<Object> select(Page<Employee> page, Employee employee) {

    // 数据必须返回一个List集合
    Map<String, Object> result = employeeService.listPage(page, employee);
    return JsonResult.success(result);
  }

  @ResponseBody
  @DeleteMapping("/del/{id}")
  public JsonResult<Object> del(@PathVariable("id") Long id) {
    boolean result = this.employeeService.removeById(id);
    return JsonResult.success(result);
  }

  @ResponseBody
  @DeleteMapping("/del/emps")
  public JsonResult<Object> delList(@RequestParam("ids") List<Long> ids) {
    boolean result = this.employeeService.removeByIds(ids);
    return JsonResult.success(result);
  }

  @ResponseBody
  @RequestMapping("/saveOrUpdate")
  public JsonResult<Object> saveOrUpdate(Employee employee){
    boolean result = employeeService.saveOrUpdate(employee);
    return JsonResult.success(result);
  }
  @GetMapping("/emp")
  public String toInput(){
    return "emp/input";
  }
  @ResponseBody
  @GetMapping("/emp/{id}")
  public JsonResult<Object> getEmp(@PathVariable Long id){
    Employee emp = employeeService.getById(id);
    return JsonResult.success(emp);
  }
}
