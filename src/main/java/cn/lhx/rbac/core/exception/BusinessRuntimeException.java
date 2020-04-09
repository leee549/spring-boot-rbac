package cn.lhx.rbac.core.exception;

import cn.lhx.rbac.core.enmus.ResultCode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lee549
 * @date 2020/4/9 10:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class BusinessRuntimeException extends RuntimeException implements Serializable {
  /** 结果码 */
  private String code;

  /** 结果码描述 */
  private String msg;

  /** 结果码枚举 */
  private ResultCode resultCode;

  // public BusinessRuntimeException(ResultCode resultCode) {
  //
  // }

}