package org.study.grabyou.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.config.RiskControllConfig;
import org.study.grabyou.dao.RedisDao;
import org.study.grabyou.entity.Event;
import org.study.grabyou.enums.DimensionType;
import org.study.grabyou.enums.TimeRange;

/**
 * 维度指标计算
 *
 * @author zjx
 * @since 2021/11/7 下午8:50
 */
@Service
public class DimensionService {

  private static Logger logger = LoggerFactory.getLogger(DimensionService.class);

  @Autowired
  private RedisDao redisDao;

  private String countSha;

  private String deviceLimitSha;

  /**
   * 计算频数
   * @param event
   * @param dimensionType
   * @param timeRange
   * @return
   */
  public int calculateNum(Event event, DimensionType dimensionType, TimeRange timeRange) {
    // TODO
    return 0;
  }

  /**
   * 运行Count Lua脚本进行计数，添加行为数据并获取结果
   */
  private Long runCountSha(String key, String remMaxScore, String expire, String score, String value, String queryMinScore, String queryMaxScore) {
    if (countSha == null) {
      countSha = redisDao.scriptLoad(RiskControllConfig.COUNT_LUA_SCRIPT);
    }
    return redisDao.evalSha(countSha, 1, new String[]{key, remMaxScore, expire, score, value, queryMinScore, queryMaxScore});
  }
}
