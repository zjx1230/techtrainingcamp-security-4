package org.study.grabyou.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.study.grabyou.enums.DimensionType;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.Impl.BlackListService;
import org.study.grabyou.utils.BlackRecordFactory;
import org.study.grabyou.utils.EventFactory;

/**
 * 测试插入一条黑名单记录
 *
 * @author zjx
 * @since 2021/11/10 上午11:10
 */
@SpringBootTest
@MapperScan("org.study.grabyou.mapper")
class BlackListServiceTest {

  @Autowired
  private BlackListService blackListService;

  @Test
  void addBlackRecord() {
    blackListService.addBlackRecord(BlackRecordFactory.build("zhangsan", DimensionType.DEVICE_ID.getValue(),
        EventType.REGISTER.getValue(), "chrown", "注册次数过多"));
  }

  @Test
  void isInBlackList() {
    boolean res = blackListService.isInBlackList(EventFactory.build("aaa", EventType.REGISTER, "dad", "das", "Das"));
    assertEquals(res, false);
  }
}