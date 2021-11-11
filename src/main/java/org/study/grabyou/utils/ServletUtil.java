package org.study.grabyou.utils;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ServletUtil {

  /**
   * 获取 HttpServletRequest 对象
   *
   * @return HttpServletRequest
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    return attrs.getRequest();
  }


  /**
   * 获取 HttpSession 对象
   *
   * @return HttpSession
   */
  public static HttpSession getSession() {
    HttpSession session = getRequest().getSession();
    return session;
  }


  /**
   * 获得完全的设备ID，即 User-Agent
   * @return 设备ID
   */
  public static String getFullDeviceID() {
    return getRequest().getHeader("User-Agent");
  }


  /**
   * 获得简版的设备ID
   * @return 设备ID
   */
  public static String getDeviceID() {
    String agentString = getRequest().getHeader("User-Agent");
    UserAgent userAgent = UserAgent.parseUserAgentString(agentString);
    OperatingSystem operatingSystem = userAgent.getOperatingSystem(); // 操作系统信息
    eu.bitwalker.useragentutils.DeviceType deviceType = operatingSystem.getDeviceType(); // 设备类型

    switch (deviceType) {
      case COMPUTER:
        return "PC";
      case TABLET: {
        if (agentString.contains("Android")) {
          return "Android Pad";
        }
        if (agentString.contains("iOS")) {
          return "iPad";
        }
        return "Unknown";
      }
      case MOBILE: {
        if (agentString.contains("Android")) {
          return "Android";
        }
        if (agentString.contains("iOS")) {
          return "IOS";
        }
        return "Unknown";
      }
      default:
        return "Unknown";
    }
  }


  /**
   * 获取ip
   * @return String 类型的 IP 地址
   */
  public static String getIp() {
    HttpServletRequest request = getRequest();
    List<String> ipHeadList = Stream
        .of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
            "X-Real-IP").collect(
            Collectors.toList());
    for (String ipHead : ipHeadList) {
      if (checkIP(request.getHeader(ipHead))) {
        return request.getHeader(ipHead).split(",")[0];
      }
    }
    return "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) ? "127.0.0.1"
        : request.getRemoteAddr();

  }

  /**
   * 检查ip存在
   */
  public static boolean checkIP(String ip) {
    return !(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip));
  }

}
