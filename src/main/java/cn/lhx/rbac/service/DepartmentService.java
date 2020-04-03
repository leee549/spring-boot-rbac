package cn.lhx.rbac.service;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Department;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/3 12:08
 */
public interface DepartmentService extends IService<Department> {
    Map<String,Object> listPage(Page<Department> page);
}
