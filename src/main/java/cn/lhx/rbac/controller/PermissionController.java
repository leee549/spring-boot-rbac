package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/6 11:11
 */
@Controller
public class PermissionController {
  @Resource PermissionService permissionService;

    @RequestMapping("perm/list")
    public String list() {
        return "permission/list";
    }

    @GetMapping("perm/input")
    public String input() {
        return "permission/input";
    }

    @ResponseBody
    @GetMapping("/perms")
    public JsonResult<Object> select(Page<Permission> page, Permission permission) {
        Map<String, Object> result = permissionService.listPage(page, permission);
        return JsonResult.success(result);
    }


    /**
     * 更新/保存
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("perm/saveOrUpdate")
    public JsonResult<Object> saveOrUpdate(Permission permission){
        boolean result = this.permissionService.saveOrUpdate(permission);
        return JsonResult.success(result);
    }

    /**
     * 删除
     * @param id 主键id
     * @return
     */
    @ResponseBody
    @DeleteMapping("perm/del/{id}")
    public JsonResult<Object> del(@PathVariable("id") Long id) {
        boolean result = this.permissionService.removeById(id);
        return JsonResult.success(result);
    }

    /**
     * 批量删除
     * @param ids 主键
     * @return
     */
    @ResponseBody
    @DeleteMapping("perm/del/perms")
    public JsonResult<Object> delList(@RequestParam("ids") List<Long> ids) {
        boolean result = this.permissionService.removeByIds(ids);
        return JsonResult.success(result);
    }
}
