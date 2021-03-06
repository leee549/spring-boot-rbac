package cn.lhx.rbac.config;

import cn.lhx.rbac.cache.ShiroCacheManager;
import cn.lhx.rbac.cache.session.MySessionManager;
import cn.lhx.rbac.cache.session.SessionDao;
import cn.lhx.rbac.security.shiro.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/4/8 13:27
 */
@Configuration
public class ShiroConfig {

  /**
   * 3.shiroFilter shiro过滤器
   *
   * @return
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager);
    Map<String, String> filters = new LinkedHashMap<>();
    filters.put("/login", "anon");
    filters.put("/auth/login", "anon");
    filters.put("/captcha", "anon");
    filters.put("/static/**", "anon");
    filters.put("/logout", "anon");
    filters.put("/**", "authc");
    // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
    bean.setLoginUrl("/login");
    // 登录成功后要跳转的链接
    bean.setSuccessUrl("/emp/list");
    // 未授权界面;
    bean.setUnauthorizedUrl("/unauthorized");
    bean.setFilterChainDefinitionMap(filters);
    return bean;
  }

  /**
   * 2.SecurityManager 安全管理器
   *
   * @return
   */
  @Bean
  public DefaultWebSecurityManager securityManager(
      ShiroCacheManager shiroCacheManager, DefaultSessionManager sessionManager) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(myRealm());
    securityManager.setCacheManager(shiroCacheManager);
    securityManager.setSessionManager(sessionManager);
    // 关闭shiro的session
    // DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    // DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
    // evaluator.setSessionStorageEnabled(false);
    // subjectDAO.setSessionStorageEvaluator(evaluator);
    // securityManager.setSubjectDAO(subjectDAO);
    return securityManager;
  }

  /**
   * 1.创建myRealm
   *
   * @return
   */
  @Bean(name = "myRealm")
  public MyRealm myRealm() {
    MyRealm myRealm = new MyRealm();
    // 给 Realm 注入密文匹配Bean
    myRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
    // myRealm.setAuthenticationCachingEnabled(true);
    myRealm.setAuthorizationCachingEnabled(true);
    myRealm.setCachingEnabled(true);
    return myRealm;
  }

  /**
   * shiro 缓存管理器
   *
   * @return
   */
  @Bean
  public ShiroCacheManager shiroCacheManager(RedisTemplate<String, Object> redisTemplate) {
    ShiroCacheManager shiroCacheManager = new ShiroCacheManager();
    shiroCacheManager.setTemplate(redisTemplate);
    return shiroCacheManager;
  }

  /** Shiro 自己的Session管理器 关了shiroHttpSession */
  @Bean
  public MySessionManager sessionManager(SessionDao sessionDao) {
    MySessionManager sessionManager = new MySessionManager();
    sessionManager.setSessionDAO(sessionDao);
    sessionManager.setGlobalSessionTimeout(1800000);
    return sessionManager;
  }

  @Bean
  public SessionDao sessionDao(RedisTemplate<String, Object> redisTemplate2) {
    SessionDao sessionDao = new SessionDao();
    sessionDao.setTemplate(redisTemplate2);
    return sessionDao;
  }
  /**
   * 凭证匹配器
   *
   * @return
   */
  @Bean(name = "hashedCredentialsMatcher")
  public HashedCredentialsMatcher getHashedCredentialsMatcher() {
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
    hashedCredentialsMatcher.setHashIterations(10000);
    // 哈希是Hex 编码的话为true，base64的话为false
    hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
    return hashedCredentialsMatcher;
  }

  /**
   * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions), 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
   * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
   */
  @Bean
  public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }
  /** 开启aop注解支持 @requirePermission */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
        new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }

  @Bean(name = "lifecycleBeanPostProcessor")
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }
}
