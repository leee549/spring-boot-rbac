package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/3 22:02
 */
@Controller
public class DepartmentController {
    @Resource
    DepartmentService deptService;

    /**
     * 跳转
     * @return
     */
    @GetMapping("dept/list")
    public String list(){
        return "dept/list";
    }
    @GetMapping("dept/input")
    public String input(){
        return "dept/input";
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @ResponseBody
    @GetMapping("/depts")
    public JsonResult<Object> select(Page<Department> page,Department dept){
        Map<String,Object> result = deptService.listPage(page,dept);
        return JsonResult.success(result);
    }

    /**
     *
     * @param department 部门
     * @return
     */
    @ResponseBody
    @RequestMapping("dept/saveOrUpdate")
    public JsonResult<Object> saveOrUpdate(Department department){
        boolean result = deptService.saveOrUpdate(department);
        return JsonResult.success(result);
    }
    /**
     * 删除
     * @param id 主键id
     * @return
     */
    @ResponseBody
    @DeleteMapping("dept/del/{id}")
    public JsonResult<Object> del(@PathVariable("id") Long id) {
        boolean result = this.deptService.removeById(id);
        return JsonResult.success(result);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ResponseBody
    @DeleteMapping("dept/del/depts")
    public JsonResult<Object> delList(@RequestParam("ids") List<Long> ids) {
        boolean result = this.deptService.removeByIds(ids);
        return JsonResult.success(result);
    }

}
