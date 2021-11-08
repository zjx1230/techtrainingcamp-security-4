package org.study.grabyou.entity;

import java.util.Date;
import lombok.Data;
import org.study.grabyou.enums.EventType;

/**
 * 事件的抽象，用于风控分析
 *
 * @author 张嘉鑫
 * @since 2021/11/5 上午11:29
 */
@Data
public class Event {

  /**
   * 事件id
   */
  private String id;

  /**
   * 事件类型
   */
  private EventType eventType;

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

  /**
   * 运行的惩罚力度 0:默认是无，
   */
  private int DecisionType;

  /****** 暂时未使用到只是先列着 *****/

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
