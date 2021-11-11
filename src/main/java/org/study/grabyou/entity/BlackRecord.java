package org.study.grabyou.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 要插入的黑名单记录
 *
 * @author zjx
 * @since 2021/11/10 上午10:23
 */
@Data
public class BlackRecord implements Serializable {

  /**
   * 记录id
   */
  private long id;

  /**
   * 用户名
   */
  private String username;

  /**
   * 维度, 0表示手机号，1表示IP,2表示设备ID，3表示用户名
   */
  private int dimension;

  /**
   * 类型, 0表示注册，1表示登录，2表示验证码
   */
  private int type;

  /**
   * 值，对应的名称(手机号\IP\设备ID)
   */
  private String value;

  /**
   * 时间
   */
  private Date time;

  /**
   * 详情,进入黑名单的原因
   */
  private String reason;
}
