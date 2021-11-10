package org.study.grabyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.entity.BlackRecord;
import org.study.grabyou.mapper.BlackListMapper;

/**
 * 黑名单的相关操作
 *
 * @author zjx
 * @since 2021/11/9 上午10:01
 */
@Service
public class BlackListService {

  @Autowired
  private BlackListMapper blackListMapper;

  public void addBlackRecord(BlackRecord blackRecord) {
    blackListMapper.insertBlackRecord(blackRecord);
  }
}
