package cn.lhx.rbac.cache;

import lombok.Setter;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lee549
 * @date 2020/4/10 10:53
 */
@Setter
public class ShiroCacheManager extends AbstractCacheManager {
  private RedisTemplate<String,Object> template;
  /**
   * 当shiro中需要缓存数据 身份 权限 都会需要一个cacheManager CacheManager 核心工作之一:创建一个缓存对象
   *
   * @param s 权限信息的标识
   * @return
   * @throws CacheException
   */

  @Override
  protected Cache createCache(String s) throws CacheException {
    System.out.println("标识:" + s);
    ShiroCache cache = new ShiroCache(s);
    cache.setTemplate(template);
    return cache;
  }
}
