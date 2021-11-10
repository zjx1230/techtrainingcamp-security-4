package org.study.grabyou.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.security.auth.login.Configuration;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
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
   * 计算账号的设备绑定数量
   * @param userId
   * @param deviceId
   * @return
   */
  public int calculateBindNum(String userId, String deviceId) {
    if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(deviceId)) {
      logger.error("参数错误");
      return 0;
    }

    String key = userId + ":" + deviceId;
    Long res = runBindLimitSha(key);
    return res == null ? 0 : res.intValue();
  }

  /**
   * 计算频数
   * @param event
   * @param dimensionType
   * @param timeRange
   * @return
   */
  public int calculateNum(Event event, DimensionType dimensionType, TimeRange timeRange) {
    if (event == null || dimensionType == null || timeRange == null) {
      logger.error("参数错误");
      return 0;
    }

    if (dimensionType == DimensionType.TELEPHONE && StringUtils.isEmpty(event.getTelephoneNumber())) {
      return 0;
    }

    Date operateTime = event.getOperateTime();
    String key = String.join(":", event.getEventType().name().toLowerCase(),
                                            dimensionType.name().toLowerCase(),
                                            timeRange.name().toLowerCase());
    String remMaxScore = dateScore(new Date(operateTime.getTime() - RiskControllConfig.EXPIRE_TIME * 1000L));
    Long res = runCountSha(key, remMaxScore, dateScore(operateTime),
        String.valueOf(operateTime.getTime()), dateScore(timeRange.getMinTime(operateTime)), dateScore(timeRange.getMaxTime(operateTime)));
    return res == null ? 0 : res.intValue();
  }

  /**
   * 运行Count Lua脚本进行计数，添加行为数据并获取结果
   */
  private Long runCountSha(String key, String remMaxScore, String score, String value, String queryMinScore, String queryMaxScore) {
    if (countSha == null) {
      countSha = redisDao.scriptLoad(RiskControllConfig.COUNT_LUA_SCRIPT);
    }
    return redisDao.evalSha(countSha, 1, new String[]{key, remMaxScore, String.valueOf(RiskControllConfig.EXPIRE_TIME), score, value, queryMinScore, queryMaxScore});
  }

  /**
   * 运行bindLimit Lua脚本进行计数，自增计算绑定设备数量
   */
  private Long runBindLimitSha(String key) {
    if (deviceLimitSha == null) {
      deviceLimitSha = redisDao.scriptLoad(RiskControllConfig.DEVICE_LIMIT_LUA_SCRIPT);
    }
    return Long.valueOf(redisDao.evalSha(deviceLimitSha, 1, new String[]{key}));
  }

  /**
   * 计算zset的score
   *
   * @param date
   * @return
   */
  private String dateScore(Date date) {
    return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
  }
}
