package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.core.enmus.ResultCode;
import cn.lhx.rbac.service.MailService;
import cn.lhx.rbac.util.FreemarkerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/13 21:58
 */
@Controller
public class SendMailController {
  @Resource MailService mailService;

  @GetMapping("/mail")
  public String toMail() {
    return "commons/mail";
  }

  @ResponseBody
  @PostMapping("/mail")
  public JsonResult<Object> sendMail(
      String to,
      String subject,
      @RequestParam(value = "context") String context,
      @RequestParam(value = "filePath", required = false) String filePath) {
    // if (ObjectUtil.isNotEmpty(filePath)) {
    mailService.sendAttachmentsMail(to, subject, context, filePath);
    // } else {
    //   mailService.sendHtmlMail(to, subject, context);
    // }

    return JsonResult.success(ResultCode.MAIL_SUCCESS.msg);
  }

  @ResponseBody
  @PostMapping("/mail/img")
  public JsonResult<Object> sendImgMail(
      String to,
      String subject,
      @RequestParam(value = "context") String context,
      @RequestParam(value = "filePath") String filePath)
      throws MessagingException {

    mailService.sendInlinResourceMail(to, subject, context, filePath);

    return JsonResult.success(ResultCode.MAIL_SUCCESS.msg);
  }

  @RequestMapping(value = "/test",method = {RequestMethod.GET})
  public void test(HttpServletResponse response) {
    try {
      Map<String, Object> map = new HashMap<>(2);
      map.put("msg", "html--生成图片测试");
      map.put("img", "\\file\\images\\5161f766-78ec-424c-897a-d72d7cd7f48f.jpg");
      FreemarkerUtils.turnImage("index.ftl", map, response);
    } catch (Exception e) {
      // log.error("异常", e.getMessage());
    }
  }
}
