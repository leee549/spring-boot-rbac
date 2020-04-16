package cn.lhx.rbac.service;

import cn.lhx.rbac.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/14 13:22
 */
public interface MemberService extends IService<Member> {
    void getCode(String to);

    Map<String,Object> addMember(Member member);
}
