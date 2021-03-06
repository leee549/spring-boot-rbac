package cn.lhx.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.PermissionDao;
import cn.lhx.rbac.dao.RoleDao;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.RoleService;
import cn.lhx.rbac.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author lee549
 * @date 2020/4/6 9:53
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
  @Resource RoleDao roleDao;


  @Override
  public List<Role> getByEmpId(Long id) {
    return roleDao.selectByEmpId(id);
  }

  @Override
  public Map<String, Object> listPage(Page<Role> page, Role role) {
    QueryWrapper<Role> qw = new QueryWrapper<>();
    if (StrUtil.isNotBlank(role.getName())) {
      qw.lambda().like(Role::getName, role.getName()).or().like(Role::getSn, role.getSn());
    } else if (ObjectUtil.isNotNull(role.getId())) {
      qw.lambda().eq(Role::getId, role.getId());
    }
    IPage<Role> pageInfo = roleDao.selectPage(page, qw);
    return PageUtil.toMap(pageInfo);
  }

  @Override
  public Set<String> querySnByEmployeeId(Long id) {
    return roleDao.querySnByEmployeeId(id);
  }

  @Override
  public List<Permission> getByRoleId(Long id) {
    return roleDao.getByRoleId(id);
  }

  @Override
  public void saveOrUpdate(Role role, Long[] ids) {
    this.saveOrUpdate(role);
    // 删除旧联系
    roleDao.deleteRelation(role.getId());
    if (ids != null) {
      Stream.of(ids).forEach(e -> roleDao.insertRelation(role.getId(), e));
    }
  }
}
