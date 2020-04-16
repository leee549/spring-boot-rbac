package cn.lhx.rbac;

import cn.lhx.rbac.service.MailService;
import cn.lhx.rbac.util.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lee549
 * @date 2020/4/13 12:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {
    @Resource
    private MailService mailService;
    @Resource
    private EmailUtil emailUtil;
    @Test
    public void sendMail(){
        mailService.sendHtmlMail("1193106371@qq.com",
                "test",
                "test");
    }
    // @Test
    // public void sendMail1(){
    //     emailUtil.sendMail("729917798@qq.com");
    // }
//"729917798@qq.com"




}
