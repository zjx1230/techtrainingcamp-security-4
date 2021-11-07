package org.study.grabyou.entity;

import java.util.Date;
import lombok.Data;

/**
 * 事件的抽象，用于风控分析
 *
 * @author 张嘉鑫
 * @since 2021/11/5 上午11:29
 */
@Data
public abstract class Event {

  /**
   * 事件id
   */
  private String id;

  /**
   * 操作时间
   */
  private Date operateTime;

  /**
   * 手机号码
   */
  private String telephoneNumber;

  /**
   * 操作的IP
   */
  private String operateIp;

  /**
   * 操作的设备ID
   */
  private String operateDeviceID;

  /**
   * 事件评分
   */
  private int score;

  /****** 暂时未使用到只是用于扩展使用 *****/

  /**
   * 手机号段
   */
  private String mobileSeg;

  /**
   * 手机归属地和运营商
   */
  private String mobileIpArea;

  /**
   * 操作ip归属地和运营商
   */
  private String operateIpArea;
}
