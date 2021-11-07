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

  private StatelessKieSession kieSession;

  private KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

  private static Logger logger = LoggerFactory.getLogger(RiskControllService.class);

  /**
   * drools全局服务变量
   */
  private void setGlobal() {
    // TODO
  }

  @Override
  public void analysis(EventType type, String ip, String deviceID, String telephone) {
    // TODO this.kieSession.execute(object);
  }

  /**
   * 规则集上线
   * @param packageName
   */
  public void addPackage(String packageName) {
    try {
      File path = new File(this.getClass().getClassLoader().getResource(packageName).toURI().getPath());
      if (path.isDirectory()) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        File[] files = path.listFiles();
        for (File file : files) {
          if (file.getName().endsWith(".drl")) {
            kbuilder.add(ResourceFactory.newClassPathResource(packageName + "/" + file.getName()), ResourceType.DRL);
            if (kbuilder.hasErrors()) {
              logger.error("Unable to compile drl. " + packageName + file.getName());
              return;
            } else {
              String ruleName = file.getName().replace(".drl", "");
              if (kbase.getRule(packageName, ruleName) != null) {
                logger.info("update rule: " + packageName + "." + ruleName);
              } else {
                logger.info("add rule: " + packageName + "." + ruleName);
              }
            }
          }
        }

        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        kieSession = kbase.newStatelessKieSession();
        setGlobal();
        printRules();
      }
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /**
   * 打印规则
   */
  public void printRules() {
    logger.info("print rule: -----------------------");
    kbase.getKnowledgePackages().forEach(knowledgePackage ->
        knowledgePackage.getRules().forEach(rule ->
            logger.info("print rule: " + knowledgePackage.getName() + "." + rule.getName())));
    logger.info("print rule: -----------------------");
  }

  @PostConstruct
  public void init() {
    addPackage("rules");
  }

}
