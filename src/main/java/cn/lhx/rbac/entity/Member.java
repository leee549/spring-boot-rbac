package cn.lhx.rbac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lee549
 * @date 2020/4/14 13:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("member")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String code;
    private Integer status;
    private String name;
    private String email;

}
