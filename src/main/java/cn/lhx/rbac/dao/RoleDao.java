package cn.lhx.rbac.dao;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lee549
 * @date 2020/3/25 12:01
 */
public interface RoleDao extends BaseMapper<Role> {

    List<Role> selectByEmpId( Long id);

    void deleteRelation(@Param("roleId")Long id);

    void insertRelation(@Param("roleId")Long id, @Param("permissionId") Long permissionId);
}
