package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.entity.Employee;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lee549
 * @date 2020/4/4 19:52
 */
@Controller
public class LoginController {

  @GetMapping("/login")
  public String toLogin() {
    return "login";
  }

  @ResponseBody
  @PostMapping("/auth/login")
  public JsonResult<Object> login(String vercode, Employee employee, HttpServletRequest request) {
    System.out.println(request.getSession());
    if (!CaptchaUtil.ver(vercode, request)) {
       System.out.println(request.getSession().getAttribute("captcha") + "==========");
      // 清除session中的验证码
      CaptchaUtil.clear(request);
      return JsonResult.error("输入的验证不正确！");
    }
    Subject subject = SecurityUtils.getSubject();
    System.out.println(subject.getSession().getAttribute("captcha")+"shiro====");
    UsernamePasswordToken token =
        new UsernamePasswordToken(employee.getName(), employee.getPassword(), false);
    subject.login(token);
    return JsonResult.success();
  }

  @RequestMapping("/logout")
  public String logout() {
    SecurityUtils.getSubject().logout();
    return "redirect:/login";
  }

  @ResponseBody
  @GetMapping("/captcha")
  public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {

    // 使用gif验证码
    GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
    CaptchaUtil.out(gifCaptcha, request, response);
    // 验证码存入session
    // request.getSession().setAttribute("captcha1", gifCaptcha.text().toLowerCase());
    // 中文类型
    //        ChineseCaptcha captcha = new ChineseCaptcha(130, 48);
    //        CaptchaUtil.out(captcha,request,response);

    // 中文gif类型
    //        ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48);
    //        CaptchaUtil.out(captcha,request,response);
    // 算术类型
    //        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
    //        captcha.setLen(3);  // 几位数运算，默认是两位
    //        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
    //        captcha.text();  // 获取运算的结果：5
    //
    //        CaptchaUtil.out(captcha,request, response);
  }
}
