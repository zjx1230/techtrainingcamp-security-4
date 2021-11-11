package org.study.grabyou.enums;

/**
 * 指标类型
 *
 * @author zjx
 * @since 2021/11/7 下午10:27
 */
public enum DimensionType {
  TELEPHONE(0),
  IP(1),
  DEVICE_ID(2),
  USERNAME(3);

  private int value;

  private DimensionType(int v) {
    value = v;
  }

  public int getValue() {
    return value;
  }
}
