package cn.lhx.rbac.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 重写
 *
 * @author lee549
 * @date 2020/3/26 10:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {

    /**
     * 默认第1页
     */
    private Long page = 1L;

    /**
     * 默认每页10条数据
     */
    private Long limit = 10L;
    // /**
    //  * 模糊查询条件 keyword：邮箱用户名
    //  *             deptId
    //  */
    // private String keyword;
    //
    // private Long deptId;

    /************************* 重写 setCurrent 和 setSize ************************************/

    @Override
    public long getCurrent() {
        return this.getPage();
    }

    @Override
    public long getSize() {
        return this.getLimit();
    }
}
