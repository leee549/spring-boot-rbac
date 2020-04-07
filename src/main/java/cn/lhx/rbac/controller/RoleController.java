package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.RoleDao;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.PermissionService;
import cn.lhx.rbac.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/6 9:52
 */
@Controller
public class RoleController {
  @Resource RoleService roleService;
  @Resource PermissionService permService;

  @RequestMapping("role/list")
  public String list() {
    return "role/list";
  }

  @GetMapping("/role")
  public String toInput() {
    return "role/input";
  }

    /**
     * 分页查询
     * @param page
     * @param role
     * @return
     */
  @ResponseBody
  @GetMapping("/roles")
  public JsonResult<Object> select(Page<Role> page, Role role) {
    Map<String, Object> result = roleService.listPage(page, role);
    return JsonResult.success(result);
  }

  /**
   * 更新/保存
   * @param role
   * @return
   */


  @ResponseBody
  @RequestMapping("/role/saveOrUpdate")
  public JsonResult<Object> saveOrUpdate(
          Role role, @RequestParam(value = "ids", required = false) Long[] ids) {
    roleService.saveOrUpdate(role, ids);
    return JsonResult.success();
  }

  /**
   * 删除
   * @param id 主键id
   * @return
   */
  @ResponseBody
  @DeleteMapping("role/del/{id}")
  public JsonResult<Object> del(@PathVariable("id") Long id) {
    boolean result = this.roleService.removeById(id);
    return JsonResult.success(result);
  }

  /**
   * 批量删除
   * @param ids 主键
   * @return
   */
  @ResponseBody
  @DeleteMapping("role/del/roles")
  public JsonResult<Object> delList(@RequestParam("ids") List<Long> ids) {
    boolean result = this.roleService.removeByIds(ids);
    return JsonResult.success(result);
  }
  @ResponseBody
  @GetMapping("/role/input")
  public JsonResult<Object> input(){
    List<Permission> list = permService.list();
    Map<String, Object> result = new HashMap<>(2);
    result.put("perms",list);
    return JsonResult.success(result);
  }
  @ResponseBody
  @GetMapping("role/getPerms")
  public JsonResult<Object> getPerms(@RequestParam("id") Long id){
    Map<String, Object> result = new HashMap<>(3);
    List<Permission> rolePerms = permService.getByRoleId(id);
    result.put("rolePerms", rolePerms);
    return JsonResult.success(result);
  }


}
