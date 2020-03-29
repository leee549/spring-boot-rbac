package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/3/25 20:19
 */
@Controller
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Resource
    EmployeeService employeeService;

    @RequestMapping("/main")
    public String test() {
        return "list";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 查询分页
     *
     * @param page     分页参数
     * @param employee 搜索模糊信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(Page<Employee> page, Employee employee) {

        // 数据必须返回一个List集合
        Map<String, Object> result = employeeService.listPage(page, employee);
        return JsonResult.success(result);
    }
}
