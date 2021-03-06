package org.study.grabyou.service;

import org.study.grabyou.enums.EventType;

/**
 * 风控服务接口
 *
 * @author zjx
 * @since 2021/11/7 下午3:47
 */
public interface IRiskControllService {

  /**
   * 风控分析接口
   * @param userName
   * @param type
   * @param ip
   * @param deviceID
   * @param telephone 如果没有可以填null/空字符
   * @return -1: 表示出错，0:表示正常，1：RiskControllConfig.HUA_KUAI, 2: RiskControllConfig.WAIT_FOR, 3: RiskControllConfig.REJECT
   */
  int analysis(String userName, EventType type, String ip, String deviceID, String telephone);
}
