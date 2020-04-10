package cn.lhx.rbac.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lee549
 * @date 2020/4/9 21:02
 */
@Configuration
public class RedisConfig {

  public final Logger log = LoggerFactory.getLogger(getClass());

  /**
   * redisTemplate模板提供给其他类对redis数据库进行操作
   *
   * @param redisConnectionFactory
   * @return
   */
  @Bean(name = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setStringSerializer(keySerializer());
    redisTemplate.setKeySerializer(keySerializer());
    redisTemplate.setHashKeySerializer(keySerializer());
    redisTemplate.setValueSerializer(fastJsonSerializer());
    redisTemplate.setHashValueSerializer(fastJsonSerializer());

    log.debug("自定义RedisTemplate加载完成");
    return redisTemplate;
  }

  /** * 声明序列化组件 */

  /** redis键序列化使用String序列化 */
  private RedisSerializer<String> keySerializer() {
    return new StringRedisSerializer();
  }

  /** value值序列化使用jackson 序列化器 */
  private RedisSerializer<Object> jacksonSerializer() {
    return new GenericJackson2JsonRedisSerializer();
  }

  /** redis value序列化 fastJson */
  private GenericFastJsonRedisSerializer fastJsonSerializer() {
    return new GenericFastJsonRedisSerializer();
  }

  @Bean
  public HashOperations<String, String, Object> hashOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForHash();
  }

  @Bean
  public ValueOperations<String, String> valueOperations(
      RedisTemplate<String, String> redisTemplate) {
    return redisTemplate.opsForValue();
  }

  @Bean
  public ListOperations<String, Object> listOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForList();
  }

  @Bean
  public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForSet();
  }

  @Bean
  public ZSetOperations<String, Object> zSetOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForZSet();
  }
}
