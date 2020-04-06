package cn.lhx.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.PermissionDao;
import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.PermissionService;
import cn.lhx.rbac.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/6 11:11
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission>
    implements PermissionService {
  @Resource PermissionDao permissionDao;

  @Override
  public Map<String, Object> listPage(Page<Permission> page, Permission permission) {
    QueryWrapper<Permission> qw = new QueryWrapper<>();
    if (StrUtil.isNotBlank(permission.getName())) {
      qw.lambda()
          .like(Permission::getName, permission.getName())
          .or()
          .like(Permission::getExpression, permission.getExpression());
    } else if (ObjectUtil.isNotNull(permission.getId())) {
      qw.lambda().eq(Permission::getId, permission.getId());
    }

    IPage<Permission> pageInfo = permissionDao.selectPage(page, qw);

    return PageUtil.toMap(pageInfo);
  }
}
