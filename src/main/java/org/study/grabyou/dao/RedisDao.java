package org.study.grabyou.dao;

import javax.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
/**
 * 操作Redis的接口
 *
 * @author zjx
 * @since 2021/11/7 下午4:05
 */
@Repository
public class RedisDao {

  @Resource(name = "redisTemplate")
  private RedisTemplate redisTemplate;

  /**
   * 将Lua脚本添加到缓存中
   * @param script
   * @param <T>
   * @return
   */
  public <T> T scriptLoad(final String script) {
    return (T) redisTemplate.execute(
        (RedisCallback<Object>) connection -> ((Jedis) connection.getNativeConnection()).scriptLoad(script));
  }


  /**
   * 对 Lua 脚本进行求值
   * @param sha
   * @param keycount
   * @param args
   * @param <T>
   * @return
   */
  public <T> T evalSha(final String sha, final int keycount, final String... args) {
    return (T) redisTemplate.execute((RedisCallback<Object>) connection -> ((Jedis) connection.getNativeConnection()).evalsha(sha, keycount, args));
  }
}
