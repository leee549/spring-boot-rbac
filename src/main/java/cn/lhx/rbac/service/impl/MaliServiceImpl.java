package cn.lhx.rbac.service.impl;

import cn.lhx.rbac.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author lee549
 * @date 2020/4/13 12:34
 */
@Service
public class MaliServiceImpl implements MailService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  /** Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用 */
  @Resource
  private JavaMailSender mailSender;

  /** 配置中的发送人 */
  @Value("${spring.mail.from}")
  private String from;

  /**
   * 简单文本邮件
   *
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   */
  @Override
  public void sendSimpleMail(String to, String subject, String content) {
    //创建SimpleMailMessage对象
    SimpleMailMessage message = new SimpleMailMessage();
    //邮件发送人
    message.setFrom(from);
    //邮件接收人
    message.setTo(to);
    //邮件主题
    message.setSubject(subject);
    //邮件内容
    message.setText(content);
    //发送邮件
    mailSender.send(message);
  }
  /**
   * 发送HTML邮件
   *
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   */
  @Override
  public void sendHtmlMail(String to, String subject, String content) {
    //获取MimeMessage对象
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper messageHelper;
    try {
      messageHelper = new MimeMessageHelper(message, true);
      //邮件发送人
      messageHelper.setFrom(from);
      //邮件接收人
      messageHelper.setTo(to);
      //邮件主题
      message.setSubject(subject);
      //邮件内容，html格式
      messageHelper.setText(content, true);
      //发送
      mailSender.send(message);
      //日志信息
      logger.info("邮件已经发送。");
    } catch (MessagingException e) {
      logger.error("发送邮件时发生异常！", e);
    }
  }

  /**
   * 发送带附件的邮件
   *
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   * @param filePath 附件
   */
  @Override
  public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);

      //true指的是html邮件，false指的是普通文本
      helper.setText(content, true);

      FileSystemResource file = new FileSystemResource(new File(filePath));
      String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
      helper.addAttachment(fileName, file);
      mailSender.send(message);
      //日志信息
      logger.info("邮件已经发送。");
    } catch (MessagingException e) {
      logger.error("发送邮件时发生异常！", e);
    }
  }

  //发送带图片的邮件
  @Override
  public void sendInlinResourceMail(String to, String subject, String content, String filePath) throws MessagingException {
    MimeMessage message=mailSender.createMimeMessage();

    MimeMessageHelper helper=new MimeMessageHelper(message,true);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(content,true);
    helper.setFrom(from);

    //String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
    FileSystemResource res=new FileSystemResource(new File(filePath));
    helper.addInline(fileName,res);


    mailSender.send(message);
  }

}
