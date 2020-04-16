package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.entity.Member;
import cn.lhx.rbac.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/14 13:13
 */
@Controller
public class MemberController {
  @Resource MemberService memberService;

  @GetMapping("/toMember")
  public String toMember() {
    return "commons/member";
  }

  @ResponseBody
  @RequestMapping("/code")
  public JsonResult<Object> getCode(@RequestParam(value = "email") String to) {
    memberService.getCode(to);
    return JsonResult.success();
  }
  @ResponseBody
  @RequestMapping("/member")
  public JsonResult<Object> addMember(Member member) {
    Map<String, Object> map = memberService.addMember(member);
    return JsonResult.success(map);
  }
}
