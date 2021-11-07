package org.study.grabyou.service.impl;

import java.io.File;
import java.net.URISyntaxException;
import javax.annotation.PostConstruct;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.IRiskControllService;

/**
 * 风控服务接口
 *
 * @author zjx
 * @since 2021/11/7 下午3:54
 */
@Service
public class RiskControllService implements IRiskControllService {

  @Override
  public void analysis(EventType type, String ip, String deviceID, String telephone) {
    // TODO this.kieSession.execute(object);
  }

}
