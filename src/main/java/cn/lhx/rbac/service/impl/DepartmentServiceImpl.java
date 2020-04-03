package cn.lhx.rbac.service.impl;

import cn.lhx.rbac.base.Page;
import cn.lhx.rbac.dao.DepartmentDao;
import cn.lhx.rbac.entity.Department;
import cn.lhx.rbac.service.DepartmentService;
import cn.lhx.rbac.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    public Map<String, Object> listPage(Page<Department> page) {
        IPage<Department> pageInfo = deptDao.selectPage(page, null);

        return PageUtil.toMap(pageInfo);
    }
}
