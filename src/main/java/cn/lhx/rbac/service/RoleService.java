package cn.lhx.rbac.service;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lee549
 * @date 2020/4/6 9:52
 */
public interface RoleService extends IService<Role> {
    Map<String,Object> listPage(Page<Role>page, Role role );

    List<Role> getByEmpId(Long id);

    void saveOrUpdate(Role role, Long[] ids);

    List<Permission> getByRoleId(Long id);

    Set<String> querySnByEmployeeId(Long id);
}
