package cn.lhx.rbac.service;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/3/25 13:48
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 分页接口
     *
     * @param page     分页参数
     * @param employee 模糊条件
     * @return 分页结果集
     */
    Map<String, Object> listPage(Page<Employee> page, Employee employee);




}
