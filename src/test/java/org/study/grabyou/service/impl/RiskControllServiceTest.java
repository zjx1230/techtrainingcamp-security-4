package org.study.grabyou.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.Impl.RiskControllService;

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
    ExecutorService executorService = Executors.newFixedThreadPool(20);
    for (int i = 0; i < 100; i ++) {
      executorService.execute(()-> {
        riskControllService.analysis("zhangsan6", EventType.REGISTER, "127.0.0.4", "pad", null);
      });
    }
    Thread.sleep(3000);
//    System.out.println(riskControllService.analysis("zhangsan6", EventType.REGISTER, "127.0.0.4", "pad", null));
    assertNotEquals(decisionType.get(), 0);
    assertEquals(decisionType.get(), 3);

    int t = 0;
    // 一秒内注册25次
    for (int i = 0; i < 25; i ++) {
      t = riskControllService.analysis("zhangsan", EventType.REGISTER, "127.0.0.1", "Chrown", null);
    }

    assertEquals(t, 3);
    int decision = 0;
    // 注册设备绑定数限制
    String[] devices = new String[] {"Chrown", "Mac", "Sari", "linux", "centos", "ubuntu", "RedHat", "aaaa", "bbbb", "cccc0"};
    for (int i = 0; i < 10; i ++) {
      decision = riskControllService.analysis("zhangsan", EventType.REGISTER, "127.0.0.1", devices[i], "123456");
    }
    Thread.sleep(3000);
    assertNotEquals(decision, 0);
    assertEquals(decision, 3);
    // 黑名单测试
    decision = 0;
    // zhangsan\Chrown 已知的黑名单
    decision = riskControllService.analysis("zhangsan", EventType.REGISTER, "127.0.0.1", "adass", null);
    assertNotEquals(decision, 0);
    assertEquals(decision, 3);
    decision = 0;
    decision = riskControllService.analysis("zdsda", EventType.REGISTER, "fsfs", "Chrown", "dfs");
    assertNotEquals(decision, 0);
    assertEquals(decision, 3);
    decision = 0;
    decision = riskControllService.analysis("zdsda", EventType.REGISTER, "fsfs", "dfsfd", "dfs");
    assertEquals(decision, 0);
  }
}