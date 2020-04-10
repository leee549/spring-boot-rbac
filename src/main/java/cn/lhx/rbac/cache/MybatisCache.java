package cn.lhx.rbac.cache;

import cn.lhx.rbac.util.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 在mybatis完成查询后将数据缓存起来，当查询数据时先查缓存
 *
 * @author lee549
 * @date 2020/4/9 22:02
 */
public class MybatisCache implements Cache {

  /** //@id:当前mapper 的namespace */
  private String id;

  private RedisTemplate<String, Object> template;

  private final ReadWriteLock lock = new ReentrantReadWriteLock();

  public MybatisCache() {}

  /** @param id mybatis 创建cache实例时传入的 */
  public MybatisCache(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }

  /**
   * :当执行查询后，将结果缓存起来
   *
   * @param key 此次查询的标识，包含sql语句 （string）
   * @param value 查询的结构 User 、List<user>
   */
  @Override
  public void putObject(Object key, Object value) {
    System.out.println("缓存数据key:" + key + "value:" + value);
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    // 将查询结果存入redis,缓存备用
    template.opsForValue().set(key.toString(), value);
  }

  @Override
  public Object getObject(Object key) {
    System.out.println("检查缓存 key:" + key.getClass());
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    // 通过sql从redis检查缓存是否可用
    Object cache = template.opsForValue().get(key.toString());
    if (cache != null) {
      return cache;
    } else {
      System.out.println("检查缓存,但未命中");
      return null;
    }
  }

  /**
   * 删除某一个缓存数据
   *
   * @param key
   * @return
   */
  @Override
  public Object removeObject(Object key) {
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    template.delete(key.toString());
    return null;
  }

  /** 当某一个mapper 触发了写操作,该mapper下的所有缓存都删除; */
  @Override
  public void clear() {
    System.out.println("namespace:" + this.getId() + "发生了写操作清空所有相关缓存");
    template = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    // 获取当前mapper下所有缓存的key
    Set<String> keys = template.keys("*" + this.getId() + "*");
    // 删除这些key
    template.delete(keys);
  }

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return this.lock;
  }
}
