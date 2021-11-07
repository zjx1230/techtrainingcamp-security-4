package org.study.grabyou.enums;

/**
 * 时间范围
 *
 * @author zjx
 * @since 2021/11/7 下午10:25
 */
public enum TimeRange {
  SECOND("second"),
  MINUTE("minute"),
  HOUR("hour");

  private String value;

  private TimeRange(String v) {
    value = v;
  }
}
