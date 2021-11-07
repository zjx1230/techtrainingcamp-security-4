package org.study.grabyou.enums;

/**
 * 指标类型
 *
 * @author zjx
 * @since 2021/11/7 下午10:27
 */
public enum DimensionType {
  TELEPHONE("telephoneNumber"),
  IP("ip"),
  DEVICE_ID("device_id");

  private String value;

  private DimensionType(String v) {
    value = v;
  }
}
