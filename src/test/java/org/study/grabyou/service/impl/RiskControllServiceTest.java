package org.study.grabyou.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.study.grabyou.enums.EventType;

/**
 * RiskControllService 单测
 *
 * @author zjx
 * @since 2021/11/8 上午10:40
 */
@SpringBootTest
class RiskControllServiceTest {

  @Autowired
  private RiskControllService riskControllService;

  private static Logger logger = LoggerFactory.getLogger(RiskControllServiceTest.class);

  @Test
  void analysis() throws InterruptedException {
    // 频繁注册
    AtomicInteger decisionType = new AtomicInteger();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 100; i ++) {
      executorService.execute(()-> {
        decisionType.set(riskControllService.analysis("zhangsan", EventType.REGISTER, "127.0.0.1", "Chrown", null));
      });
    }
    Thread.sleep(3000);
    assertNotEquals(decisionType.get(), 0);
    assertEquals(decisionType.get(), 3);
  }
}