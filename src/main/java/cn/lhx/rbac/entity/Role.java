package cn.lhx.rbac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lee549
 * @date 2020/3/25 11:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;
    private String sn;
    @TableField(exist = false)
    private List<Permission> permissions;
}
