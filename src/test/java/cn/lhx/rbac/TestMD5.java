package cn.lhx.rbac;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lee549
 * @date 2020/4/8 21:17
 */
// @RunWith(SpringJUnit4ClassRunner.class)
public class TestMD5 {
  @Test
  public void testMd5() {
    String pwd = "1";
    String salt = "1450";
    String s = new Md5Hash(pwd,salt, 10000).toHex();
    System.out.println(s);
  }
}
