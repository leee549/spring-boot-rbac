package cn.lhx.rbac.dao;

import cn.lhx.rbac.entity.Permission;
import cn.lhx.rbac.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author lee549
 * @date 2020/3/25 12:01
 */
public interface RoleDao extends BaseMapper<Role> {

    List<Role> selectByEmpId( Long id);

    void deleteRelation(@Param("roleId")Long id);

    void insertRelation(@Param("roleId")Long id, @Param("permissionId") Long permissionId);

    List<Permission> getByRoleId(Long id);

    Set<String> querySnByEmployeeId(Long id);
}
