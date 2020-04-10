package cn.lhx.rbac.cache;

import cn.lhx.rbac.util.SpringContextUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * 完成每个用户的权限信息的redis 缓存
 *
 * @author lee549
 * @date 2020/4/10 10:49
 */
@Getter
@Setter
public class ShiroCache implements Cache {
  // 缓存权限的标识

  private String name;

  private RedisTemplate<String, Object> template;

  public ShiroCache() {}

  public ShiroCache(String name) {
    this.name = name;
  }
  // 主要逻辑

  /**
   * todo：检查缓存，以用户凭证为key
   *
   * @param o =key=用户凭证 ~=用户名
   * @return
   * @throws CacheException
   */
  @Override
  public Object get(Object o) throws CacheException {
    System.out.println("权限信息检查缓存");
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    Object data = template.opsForValue().get(o.toString());
    if (data == null) {
      System.out.println("权限信息检查缓存 未命中");
      return null;
    }
    System.out.println("权限信息检查缓存 命中");
    return data;
  }

  /**
   * todo: 从数据库查询出该用户的权限信息 存入redis缓存
   *
   * @param o =key=用户凭证 ~=用户名
   * @param o2 权限信息
   * @return
   * @throws CacheException
   */
  @Override
  public Object put(Object o, Object o2) throws CacheException {
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    template.opsForValue().set(o.toString(), o2);
    return null;
  }

  /**
   * todo: 移除某个用户的权限信息的缓存，用户subject.logout();
   *
   * @param o
   * @return
   * @throws CacheException
   */
  @Override
  public Object remove(Object o) throws CacheException {
    System.out.println(o + "的权限信息缓存被删除");
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    template.delete(o.toString());
    return null;
  }

  @Override
  public void clear() throws CacheException {}

  @Override
  public int size() {
    return 0;
  }

  @Override
  public Set keys() {
    return null;
  }

  @Override
  public Collection values() {
    return null;
  }
}
