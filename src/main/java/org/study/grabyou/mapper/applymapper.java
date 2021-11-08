package org.study.grabyou.mapper;

import org.study.grabyou.bean.applybean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface applymapper {
  void addApply(applybean applyBean);

  applybean getApply(applybean search);
}
