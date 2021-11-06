package org.study.grabyou.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试首页
 *
 * @author zjx
 * @since 2021/11/5 上午10:41
 */
@RestController
public class IndexController {

  @GetMapping("/index")
  public String index() {
    return "Hello, 字节！";
  }
}
