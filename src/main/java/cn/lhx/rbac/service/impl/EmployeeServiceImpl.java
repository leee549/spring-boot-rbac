package cn.lhx.rbac.service.impl;

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
import java.util.HashMap;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/3/25 13:48
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {


    @Resource
    private EmployeeDao employeeDao;

    @Override
    public Map<String, Object> listPage(Page<Employee> page, Employee employee) {
        QueryWrapper<Employee> qw = new QueryWrapper<>();

        // 模糊搜索，以此类推
        if (StrUtil.isNotBlank(employee.getEmail())) {
            qw.lambda().eq(Employee::getEmail, employee.getEmail());
        }

        IPage<Employee> pageInfo = this.employeeDao.selectPage(page, qw);
        Map<String, Object> map = new HashMap<>();
        map.put("data",pageInfo.getRecords());
        map.put("total",pageInfo.getTotal());
        return map;
        // return PageUtil.toMap(pageInfo);
    }


}
