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
   * @param type
   * @param ip
   * @param deviceID
   * @param telephone
   * @return 0:表示正常，1：RiskControllConfig.HUA_KUAI, 2: RiskControllConfig.WAIT_FOR, 3: RiskControllConfig.REJECT
   */
  int analysis(EventType type, String ip, String deviceID, String telephone);
}
