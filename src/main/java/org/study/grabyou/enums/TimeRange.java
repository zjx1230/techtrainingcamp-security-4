package org.study.grabyou.enums;

import java.util.Date;

/**
 * 时间范围
 *
 * @author zjx
 * @since 2021/11/7 下午10:25
 */
public enum TimeRange {
  LAST_SECOND,
  LAST_MINUTE,
  LAST_HOUR;

  public Date getMinTime(Date now) {
    return new Date(now.getTime() - getTimeDiff());
  }

  public Date getMaxTime(Date now) {
    return now;
  }

  public long getTimeDiff() {
    long timeDiff;
    switch (this) {
      case LAST_MINUTE:
        timeDiff = 60 * 1000L;
        break;
      case LAST_HOUR:
        timeDiff = 3600 * 1000L;
        break;
      default:
        timeDiff = 1000L;
        break;
    }
    return timeDiff;
  }
}
