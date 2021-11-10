package org.study.grabyou.dao;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.study.grabyou.entity.BlackRecord;
import org.study.grabyou.entity.Event;
import org.study.grabyou.enums.DimensionType;
import org.study.grabyou.enums.EventType;
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

  private static final String REGISTER_BLACKLIST_USERNAME = "register:blackList:username";

  private static final String REGISTER_BLACKLIST_IP = "register:blackList:ip";

  private static final String REGISTER_BLACKLIST_DEVICE = "register:blackList:device";

  private static final String REGISTER_BLACKLIST_TELEPHONE = "register:blackList:telephone";

  private static final String LOGIN_BLACKLIST_USERNAME = "login:blackList:username";

  private static final String LOGIN_BLACKLIST_IP = "login:blackList:ip";

  private static final String LOGIN_BLACKLIST_DEVICE = "login:blackList:device";

  private static final String LOGIN_BLACKLIST_TELEPHONE = "login:blackList:telephone";

  private static final String VERIFYCODE_BLACKLIST_USERNAME = "verifycode:blackList:username";

  private static final String VERIFYCODE_BLACKLIST_IP = "verifycode:blackList:ip";

  private static final String VERIFYCODE_BLACKLIST_DEVICE = "verifycode:blackList:device";

  private static final String VERIFYCODE_BLACKLIST_TELEPHONE = "verifycode:blackList:telephone";

  public void addBlackListRecord(BlackRecord blackRecord) {
    if (blackRecord.getType() == EventType.REGISTER.getValue()) {
      if (!StringUtils.isEmpty(blackRecord.getUsername())) redisTemplate.opsForSet().add(REGISTER_BLACKLIST_USERNAME, blackRecord.getUsername());
      if (blackRecord.getDimension() == DimensionType.TELEPHONE.getValue()) {
        redisTemplate.opsForSet().add(REGISTER_BLACKLIST_TELEPHONE, blackRecord.getValue());
      }
      if (blackRecord.getDimension() == DimensionType.IP.getValue()) {
        redisTemplate.opsForSet().add(REGISTER_BLACKLIST_IP, blackRecord.getValue());
      }
      if (blackRecord.getDimension() == DimensionType.DEVICE_ID.getValue()) {
        redisTemplate.opsForSet().add(REGISTER_BLACKLIST_DEVICE, blackRecord.getValue());
      }
    }

    if (blackRecord.getType() == EventType.LOGIN.getValue()) {
      if (!StringUtils.isEmpty(blackRecord.getUsername())) redisTemplate.opsForSet().add(LOGIN_BLACKLIST_USERNAME, blackRecord.getUsername());
      if (blackRecord.getDimension() == DimensionType.TELEPHONE.getValue()) {
        redisTemplate.opsForSet().add(LOGIN_BLACKLIST_TELEPHONE, blackRecord.getValue());
      }
      if (blackRecord.getDimension() == DimensionType.IP.getValue()) {
        redisTemplate.opsForSet().add(LOGIN_BLACKLIST_IP, blackRecord.getValue());
      }
      if (blackRecord.getDimension() == DimensionType.DEVICE_ID.getValue()) {
        redisTemplate.opsForSet().add(LOGIN_BLACKLIST_DEVICE, blackRecord.getValue());
      }
    }

    if (blackRecord.getType() == EventType.VERIFY.getValue()) {
      if (!StringUtils.isEmpty(blackRecord.getUsername())) redisTemplate.opsForSet().add(VERIFYCODE_BLACKLIST_USERNAME, blackRecord.getUsername());
      if (blackRecord.getDimension() == DimensionType.TELEPHONE.getValue()) {
        redisTemplate.opsForSet().add(VERIFYCODE_BLACKLIST_TELEPHONE, blackRecord.getValue());
      }
      if (blackRecord.getDimension() == DimensionType.IP.getValue()) {
        redisTemplate.opsForSet().add(VERIFYCODE_BLACKLIST_IP, blackRecord.getValue());
      }
      if (blackRecord.getDimension() == DimensionType.DEVICE_ID.getValue()) {
        redisTemplate.opsForSet().add(VERIFYCODE_BLACKLIST_DEVICE, blackRecord.getValue());
      }
    }
  }

  public boolean containBlackList(Event event) {
    boolean res = false;
    if (event.getEventType().getValue() == EventType.REGISTER.getValue()) {
      if (!StringUtils.isEmpty(event.getTelephoneNumber())) {
        res = redisTemplate.opsForSet().members(REGISTER_BLACKLIST_TELEPHONE).contains(event.getTelephoneNumber());
        if (res) return res;
      }
      if (!StringUtils.isEmpty(event.getUserName())) {
        res = redisTemplate.opsForSet().members(REGISTER_BLACKLIST_USERNAME).contains(event.getUserName());
        if (res) return res;
      }
      res = redisTemplate.opsForSet().members(REGISTER_BLACKLIST_IP).contains(event.getOperateIp());
      if (res) return res;
      res = redisTemplate.opsForSet().members(REGISTER_BLACKLIST_DEVICE).contains(event.getOperateDeviceID());
      if (res) return res;
    }

    if (event.getEventType().getValue() == EventType.LOGIN.getValue()) {
      if (!StringUtils.isEmpty(event.getTelephoneNumber())) {
        res = redisTemplate.opsForSet().members(LOGIN_BLACKLIST_TELEPHONE).contains(event.getTelephoneNumber());
        if (res) return res;
      }
      if (!StringUtils.isEmpty(event.getUserName())) {
        res = redisTemplate.opsForSet().members(LOGIN_BLACKLIST_USERNAME).contains(event.getUserName());
        if (res) return res;
      }
      res = redisTemplate.opsForSet().members(LOGIN_BLACKLIST_IP).contains(event.getOperateIp());
      if (res) return res;
      res = redisTemplate.opsForSet().members(LOGIN_BLACKLIST_DEVICE).contains(event.getOperateDeviceID());
      if (res) return res;
    }

    if (event.getEventType().getValue() == EventType.VERIFY.getValue()) {
      if (!StringUtils.isEmpty(event.getTelephoneNumber())) {
        res = redisTemplate.opsForSet().members(VERIFYCODE_BLACKLIST_TELEPHONE).contains(event.getTelephoneNumber());
        if (res) return res;
      }
      if (!StringUtils.isEmpty(event.getUserName())) {
        res = redisTemplate.opsForSet().members(VERIFYCODE_BLACKLIST_USERNAME).contains(event.getUserName());
        if (res) return res;
      }
      res = redisTemplate.opsForSet().members(VERIFYCODE_BLACKLIST_IP).contains(event.getOperateIp());
      if (res) return res;
      res = redisTemplate.opsForSet().members(VERIFYCODE_BLACKLIST_DEVICE).contains(event.getOperateDeviceID());
      if (res) return res;
    }
    return res;
  }

  /**
   * 从blackRecord提取出了key,作为Redis的key
   * @param blackRecord
   * @return
   */
//  private String getKeyFromBlackRecord(BlackRecord blackRecord) {
//    if (blackRecord.getType() == EventType.REGISTER.getValue()) {
//      return "register:blackList:" + blackRecord.getUsername();
//    }
//  }

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
