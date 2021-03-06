package org.study.grabyou.service.impl;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.Impl.UserEventLogService;
import org.study.grabyou.utils.EventFactory;

/**
 * UserEventLogService εζ΅
 *
 * @author zjx
 * @since 2021/11/9 δΈε10:31
 */
@SpringBootTest
class UserEventLogServiceTest {

  @Test
  void insertUserEvent() throws IOException, InterruptedException {
    for (int i = 0; i < 10; i ++) {
      UserEventLogService
          .insertUserEvent(EventFactory.build("zhangsan", EventType.REGISTER, "192.168.0.2", "Mac Book Pro", "12345678910"));
      UserEventLogService.insertUserEvent(EventFactory.build("zhangsan", EventType.LOGIN, "192.168.0.2", "Win10", "12345678910"));
      UserEventLogService.insertUserEvent(EventFactory.build("zhangsan", EventType.VERIFY, "192.168.0.2", "CentOS", "12345678910"));
    }
    Thread.sleep(3000);
  }
}