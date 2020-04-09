package cn.lhx.rbac.dao;

import cn.lhx.rbac.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author lee549
 * @date 2020/3/25 12:01
 */
// @CacheNamespace(implementation = MybatisCache.class, eviction = MybatisCache.class)
public interface DepartmentDao extends BaseMapper<Department> {}
