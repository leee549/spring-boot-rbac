package cn.lhx.rbac.dao;

import cn.lhx.rbac.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * @author lee549
 * @date 2020/3/25 12:01
 */
public interface PermissionDao extends BaseMapper<Permission> {

    Set<String> selectByEmployeeId(Long id);
}
