package org.study.grabyou.dao;

import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

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

  
}
