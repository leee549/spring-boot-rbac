package cn.lhx.rbac.service;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lee549
 * @date 2020/4/6 11:11
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 分页
     * @param page 分页条件
     * @param permission 模糊查询条件
     * @return
     */
    Map<String, Object> listPage(Page<Permission> page, Permission permission);


    Set<String> queryAllExpression();

    Set<String> queryExpressionByEmployeeId(Long id);
}
