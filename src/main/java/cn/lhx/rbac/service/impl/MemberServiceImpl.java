package cn.lhx.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.lhx.rbac.base.Constant;
import cn.lhx.rbac.dao.MemberDao;
import cn.lhx.rbac.entity.Member;
import cn.lhx.rbac.service.MemberService;
import cn.lhx.rbac.util.EmailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/14 13:23
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {
  @Resource MemberDao memberDao;
  @Resource EmailUtil emailUtil;

  @Override
  public void getCode(String to) {
    String code = RandomUtil.randomNumbers(5);
    QueryWrapper<Member> qw = new QueryWrapper<>();
    qw.lambda().eq(Member::getEmail, to);
    Member member = memberDao.selectOne(qw);
    if (ObjectUtil.isNotEmpty(member)) {
      member.setCode(code);
      memberDao.update(member, qw);
    } else {
      Member member1 = new Member();
      member1.setCode(code);
      member1.setEmail(to);
      memberDao.insert(member1);
    }
    emailUtil.sendMail(to, code);
  }

  @Override
  public Map<String, Object> addMember(Member member) {
    //查询当前邮箱的状态
    QueryWrapper<Member> qw = new QueryWrapper<>();
    qw.lambda().eq(Member::getEmail, member.getEmail());
    Member selectOne = memberDao.selectOne(qw);
    HashMap<String, Object> map = new HashMap<>(2);
    //非空且验证码正确
    if (ObjectUtil.isAllEmpty(selectOne)){
      map.put("msg", "验证码不正确!");
      map.put("code", Constant.FAIL);
      return map;
    }
    else if (ObjectUtil.isNotEmpty(selectOne)&&ObjectUtil.equal(member.getCode(), selectOne.getCode())) {
      selectOne.setStatus(1);
      selectOne.setName(member.getName());
      memberDao.update(selectOne,qw);
      map.put("msg", "注册成功!");
      map.put("code", Constant.SUCCESS);
    } else {
      map.put("msg", "验证码不正确!");
      map.put("code", Constant.FAIL);
    }
    //注册成功后更新激活码
    selectOne.setCode(RandomUtil.randomNumbers(5));
    memberDao.update(selectOne,qw);
    return map;
  }
}
