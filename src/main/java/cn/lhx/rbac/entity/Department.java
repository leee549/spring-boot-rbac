package cn.lhx.rbac.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lee549
 * @date 2020/3/25 11:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {
    private Long id;
    private String name;
    private String sn;

}
