package org.study.grabyou.utils;

import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Event;
import org.study.grabyou.enums.EventType;

/**
 * 事件生产工厂
 *
 * @author zjx
 * @since 2021/11/7 下午3:57
 */
@Component
public class EventFactory {
  
  public static Event build(String userName,EventType eventType, String ip, String deviceID, String telephone) {
    Event event = new Event();
    event.setUserName(userName);
    event.setOperateTime(new Date());
    event.setId(UUID.randomUUID().toString());
    event.setEventType(eventType);
    event.setOperateIp(ip);
    event.setTelephoneNumber(telephone);
    event.setOperateDeviceID(deviceID);
    return event;
  }
}
