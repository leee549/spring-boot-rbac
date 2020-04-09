package cn.lhx.rbac.core.enmus;

import lombok.AllArgsConstructor;

/**
 * @author lee549
 * @date 2020/4/9 10:57
 */
@AllArgsConstructor
public enum ResultCode {
    //成功
    SUCCESS(200,"成功"),
    // 操作失败
    FAIL(201, "接口请求失败"),
    // 系统异常
    SYSTEM_ERROR(202, "系统异常"),
    // 认证异常
    NOT_SING_IN(203, "用户未登录或身份异常"),
    // 账号或密码错误
    NOT_EXIST(203, "账号或密码错误"),
    //账户不存在
    ACC_NOT_EXIST(203,"账号不存在"),
    // 密码错误
    PWD_NOT_EXIST(203,"密码错误"),
    // 参数异常
    ARG_ERROR(400, "参数错误"),
    // 权限不足
    UN_AUTHORIZED(401, "权限不足");

    /** 取值 */
    public final Integer val;

    /** 取描述 */
    public final String msg;
}
