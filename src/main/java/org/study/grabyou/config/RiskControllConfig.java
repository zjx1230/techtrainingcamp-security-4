package org.study.grabyou.config;

import org.springframework.context.annotation.Configuration;

/**
 * 风控相关的一些配置参数
 * LOW、MID、HIGH代表惩罚力度
 * LOW: 触发滑块验证
 * MID: 过一段时间
 * HIGH: 拦截
 *
 * @author 张嘉鑫
 * @since 2021/11/7 下午3:29
 */
@Configuration("riskControll")
public class RiskControllConfig {

  /**
   * 计算指定时间段内(一秒钟、一分钟、一小时、ALL)
   * 计算指定维度(手机号、IP、设备ID)
   * 某操作(注册、登录、验证码)的频数
   */
  public final static String COUNT_LUA_SCRIPT = "if tonumber(ARGV[1])>0 then " +
      "redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]);" +
      "redis.call('EXPIRE',KEYS[1],ARGV[2]);" +
      "end;" +
      "redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4]);" +
      "return redis.call('ZCOUNT',KEYS[1],ARGV[5],ARGV[6]);";

  /**
   * 计算账号绑定设备数量
   */
  public final static String DEVICE_LIMIT_LUA_SCRIPT = "redis.call('INCR', KEYS[1]);" +
      "return redis.call('GET', KEYS[1]);";

  /**
   * 每秒操作数阈值：LOW
   */
  public final static int LOW_OPERATE_TIMES_BY_SECONDS = 3;

  /**
   * 每分钟操作数阈值：LOW
   */
  public final static int LOW_OPERATE_TIMES_BY_MINUTE = 10;

  /**
   * 每小时操作数阈值：LOW
   */
  public final static int LOW_OPERATE_TIMES_BY_HOUR = 20;

  /**
   * 每秒操作数阈值：MID
   */
  public final static int MID_OPERATE_TIMES_BY_SECONDS = 5;

  /**
   * 每分钟操作数阈值：MID
   */
  public final static int MID_OPERATE_TIMES_BY_MINUTE = 30;

  /**
   * 每小时操作数阈值：MID
   */
  public final static int MID_OPERATE_TIMES_BY_HOUR = 40;

  /**
   * 每秒操作数阈值：HIGH
   */
  public final static int HIGH_OPERATE_TIMES_BY_SECONDS = 10;

  /**
   * 每分钟操作数阈值：HIGH
   */
  public final static int HIGH_OPERATE_TIMES_BY_MINUTE = 60;

  /**
   * 每小时操作数阈值：HIGH
   */
  public final static int HIGH_OPERATE_TIMES_BY_HOUR = 100;

  /**
   * 账号绑定的设备数阈值: LOW
   */
  public final static int LOW_ACCOUNT_BIND_DEVICES = 3;

  /**
   * 账号绑定的设备数阈值: MID
   */
  public final static int MID_ACCOUNT_BIND_DEVICES = 5;

  /**
   * 账号绑定的设备数阈值: HIGH
   */
  public final static int HIGH_ACCOUNT_BIND_DEVICES = 10;

  /**
   * Redis默认保存用户的过期时间为7天
   */
  public final static int EXPIRE_TIME = 7 * 24 * 3600;

  /**
   * 滑块
   */
  public final static int HUA_KUAI = 1;

  /**
   * 等待一段时间
   */
  public final static int WAIT_FOR = 2;

  /**
   * 拦截
   */
  public final static int REJECT = 3;
}
