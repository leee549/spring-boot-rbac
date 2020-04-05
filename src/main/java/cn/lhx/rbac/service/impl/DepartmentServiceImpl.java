package cn.lhx.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.DepartmentDao;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.service.DepartmentService;
import cn.lhx.rbac.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/3 12:07
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DepartmentService {
    @Resource DepartmentDao deptDao;
    @Override
    public Map<String, Object> listPage(Page<Department> page,Department dept) {
        QueryWrapper<Department> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dept.getName())) {
        qw.lambda()
                .like(Department::getName,dept.getName())
                .or()
                .like(Department::getSn,dept.getSn());
        }else if(ObjectUtil.isNotNull(dept.getId())) {
            qw.eq("id",dept.getId());
        }
        IPage<Department> pageInfo = deptDao.selectPage(page, qw);

        return PageUtil.toMap(pageInfo);
    }
}
