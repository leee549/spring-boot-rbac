package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.RoleDao;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/6 9:52
 */
@Controller
public class RoleController {
  @Resource RoleService roleService;

  @RequestMapping("role/list")
  public String list() {
    return "role/list";
  }

  @GetMapping("role/input")
  public String input() {
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
  @RequestMapping("role/saveOrUpdate")
  public JsonResult<Object> saveOrUpdate(Role role){
    boolean result = this.roleService.saveOrUpdate(role);
    return JsonResult.success(result);
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


}
