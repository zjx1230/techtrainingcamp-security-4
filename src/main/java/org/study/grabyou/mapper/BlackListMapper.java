package org.study.grabyou.mapper;

import org.study.grabyou.entity.BlackRecord;

/**
 * 操作黑名单表
 *
 * @author zjx
 * @since 2021/11/10 上午10:30
 */
public interface BlackListMapper {

  void insertBlackRecord(BlackRecord blackRecord);
}
