package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.grabyou.enums.DimensionType;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.Impl.BlackListService;
import org.study.grabyou.utils.BlackRecordFactory;

/**
 * 测试首页
 *
 * @author zjx
 * @since 2021/11/5 上午10:41
 */
@RestController
public class IndexController {

  @Autowired
  private BlackListService blackListService;

  @GetMapping("/index")
  public String index() {
    blackListService.addBlackRecord(BlackRecordFactory.build("zhangsan", DimensionType.DEVICE_ID.getValue(),
        EventType.REGISTER.getValue(), "chrown", "注册次数过多"));
    return "Hello, 字节！";
  }
}
