package org.study.grabyou.mapper;

import org.study.grabyou.entity.ApplyBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyMapper {

  void addApply(ApplyBean applyBean);

  ApplyBean getApply(ApplyBean search);
}
