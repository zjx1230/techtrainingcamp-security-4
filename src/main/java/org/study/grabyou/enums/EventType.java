package org.study.grabyou.enums;

/**
 * 事件类型
 *
 * @author zjx
 * @since 2021/11/7 下午4:02
 */
public enum EventType {
  REGISTER(0),
  LOGIN(1),
  VERIFY(2);

  private int value;

  private EventType(int v) {
    value = v;
  }

  public int getValue() {
    return value;
  }
}
