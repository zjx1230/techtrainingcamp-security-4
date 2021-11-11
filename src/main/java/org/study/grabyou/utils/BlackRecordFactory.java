package org.study.grabyou.utils;

import java.util.Date;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.BlackRecord;

/**
 * 生产黑名单记录工厂
 *
 * @author zjx
 * @since 2021/11/10 上午10:47
 */
@Component
public class BlackRecordFactory {

  public static BlackRecord build(String userName, int dimension, int type, String device,
      String reason) {
    BlackRecord blackRecord = new BlackRecord();
    blackRecord.setUsername(userName);
    blackRecord.setTime(new Date());
    blackRecord.setDimension(dimension);
    blackRecord.setReason(reason);
    blackRecord.setType(type);
    blackRecord.setValue(device);

    return blackRecord;
  }
}
