package cn.lhx.rbac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lee549
 * @date 2020/3/25 11:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String email;
    private Integer age;
    private Integer sex;
    private Boolean admin;
    @TableField(value = "dept_id")
    private Long deptId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth;
    @TableField(exist = false)
    private Department dept;
}
