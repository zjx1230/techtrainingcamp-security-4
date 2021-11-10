package org.study.grabyou.service.impl;

import com.alibaba.fastjson.JSON;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.study.grabyou.entity.Event;

/**
 * 用户事件行为记录，用于未来的大数据分析以及人工智能模型训练，更好地完善风控系统
 *
 * @author zjx
 * @since 2021/11/9 上午10:06
 */
@Service
public class UserEventLogService {

  private static AtomicReference<Date> curTime = new AtomicReference<>(new Date());

  private static AtomicLong tomorrow = new AtomicLong(curTime.get().getTime() + (3600 * 24 * 1000L));

  private static final String directory = "userLog/";

  private static volatile BufferedWriter bufferedWriter;

  private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 16, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

  private static Logger logger = LoggerFactory.getLogger(UserEventLogService.class);

  @PostConstruct
  public void init() {
    File dir = new File(directory);
    if (!dir.exists()) {
      if (!dir.mkdirs()) {
        logger.error("创建目录失败: " + directory + "\n");
      }
    }
    judeFileExists(directory + new SimpleDateFormat("yyyy-MM-dd").format(curTime.get()) + "-userEvent");
  }

  // TODO 这段代码感觉可能有问题
  public static void insertUserEvent(Event event) throws IOException {
    initBufferedWriter();
    threadPool.execute(()-> {
      String content = JSON.toJSONString(event);
      try {
        if (event.getOperateTime().getTime() >= tomorrow.get()) {
          curTime.set(new Date());
          String filepath = new SimpleDateFormat("yyyy-MM-dd").format(curTime.get()) + "-userEvent";
          judeFileExists(directory + filepath);
          tomorrow.set(curTime.get().getTime() + (3600 * 24 * 1000L));
          synchronized (UserEventLogService.class) {
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedWriter = new BufferedWriter(new FileWriter(directory + filepath));
          }
        }

        synchronized (UserEventLogService.class) {
          bufferedWriter.write(content);
          bufferedWriter.newLine();
          bufferedWriter.flush();
        }
      } catch (IOException e) {
        e.printStackTrace();
        try {
          synchronized (UserEventLogService.class) {
            bufferedWriter.flush();
            bufferedWriter.close();
          }
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
  }

  private static void initBufferedWriter() throws IOException {
    if (bufferedWriter == null) {
      synchronized (UserEventLogService.class) {
        if (bufferedWriter == null) {
          String filepath = new SimpleDateFormat("yyyy-MM-dd").format(curTime.get()) + "-userEvent";
          bufferedWriter = new BufferedWriter(new FileWriter(directory + filepath));
        }
      }
    }
  }

  // 判断文件是否存在
  private static void judeFileExists(String filepath) {
    File file = new File(filepath);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
