package cn.lhx.rbac.service;

import javax.mail.MessagingException;

/**
 * @author lee549
 * @date 2020/4/13 12:34
 */
public interface MailService {
  /**
   * 发送文本邮件
   *
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   */
  void sendSimpleMail(String to, String subject, String content);

  /**
   * 发送HTML邮件
   *
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   */
   void sendHtmlMail(String to, String subject, String content);

  /**
   * 发送带附件的邮件
   *
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   * @param filePath 附件
   */
   void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送带图片邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    void sendInlinResourceMail(String to,String subject,String content,String filePath) throws MessagingException;
}
