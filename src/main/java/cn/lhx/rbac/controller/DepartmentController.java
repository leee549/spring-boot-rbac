package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
     * 分页查询
     * @param page
     * @return
     */
    @ResponseBody
    @GetMapping("/dept")
    public JsonResult<Object> select(Page<Department> page){
        Map<String,Object> result = deptService.listPage(page);
        return JsonResult.success(result);
    }
}
