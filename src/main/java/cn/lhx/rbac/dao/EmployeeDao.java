package cn.lhx.rbac.dao;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.entity.Employee;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * @author lee549
 * @date 2020/3/25 12:01
 */
// @CacheNamespace(implementation = MybatisCache.class,eviction = MybatisCache.class)
public interface EmployeeDao extends BaseMapper<Employee> {
  /**
   * 分页模糊查询
   *
   * @param page
   * @param queryWrapper
   * @return
   */
  IPage<Employee> empList(
      Page<Employee> page, @Param(Constants.WRAPPER) Wrapper<Employee> queryWrapper);

  void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long ids);

  void deleteRelation(@Param("employeeId") Long employeeId);
}
