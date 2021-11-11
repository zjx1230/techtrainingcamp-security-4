package org.study.grabyou.service.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.dao.RedisDao;
import org.study.grabyou.entity.BlackRecord;
import org.study.grabyou.entity.Event;
import org.study.grabyou.mapper.BlackListMapper;

/**
 * 黑名单的相关操作
 *
 * @author zjx
 * @since 2021/11/9 上午10:01
 */
@Service
public class BlackListService {

  @Autowired
  private BlackListMapper blackListMapper;

  @Autowired
  private RedisDao redisDao;

  private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16, 1,
      TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

  /**
   * 添加黑名单
   * @param blackRecord
   */
  public void addBlackRecord(BlackRecord blackRecord) {
    redisDao.addBlackListRecord(blackRecord);
    threadPoolExecutor.execute(()-> {
      blackListMapper.insertBlackRecord(blackRecord);
    });
  }

  /**
   * 判断是否在黑名单中
   * @param e
   * @return
   */
  public boolean isInBlackList(Event e) {
    if (redisDao.containBlackList(e)) {
      return true;
    }
    BlackRecord blackRecord = blackListMapper.findBlackRecord(e);
    return blackRecord != null;
  }
}
