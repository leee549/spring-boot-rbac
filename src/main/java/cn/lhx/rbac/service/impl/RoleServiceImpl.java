package cn.lhx.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.RoleDao;
import cn.lhx.rbac.entity.Role;
import cn.lhx.rbac.service.RoleService;
import cn.lhx.rbac.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/6 9:53
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Resource RoleDao roleDao;
    @Override
    public Map<String, Object> listPage(Page<Role> page, Role role) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(role.getName())){
            qw.lambda()
                    .like(Role::getName,role.getName())
                    .or()
                    .like(Role::getSn,role.getSn());
        }else if (ObjectUtil.isNotNull(role.getId())){
            qw.lambda()
                    .eq(Role::getId,role.getId());
        }
        IPage<Role> pageInfo = roleDao.selectPage(page, qw);
        return PageUtil.toMap(pageInfo);
    }
}
