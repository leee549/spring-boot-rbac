package cn.lhx.rbac.util;

import cn.lhx.rbac.service.MailService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lee549
 * @date 2020/4/13 12:24
 */
@Component
public class EmailUtil {
  @Resource MailService mailService;



  public void sendMail(String to,String code) {

    mailService.sendHtmlMail(
        to,
        "注册",
        "<html><head></head><body><h1>这是一封激活邮件</h1><h3>"+"验证码为:"+code+
            "</h3></body></html>");

  }


  // "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:8080/RegisterDemo/ActiveServlet?code="
  //         + code
  //           + "'>http://localhost:8080/RegisterDemo/ActiveServlet?code="
  //                   + code
  //           + "</href></h3></body></html>"
}