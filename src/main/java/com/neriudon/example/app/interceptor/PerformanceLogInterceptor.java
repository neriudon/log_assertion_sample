package com.neriudon.example.app.interceptor;

import com.neriudon.example.utils.log.PerformanceLogger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor to output performance log.
 */
public class PerformanceLogInterceptor extends HandlerInterceptorAdapter {

  private static final PerformanceLogger logger = PerformanceLogger.getLog();

  private long startNanoTime;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // set start time
    startNanoTime = System.nanoTime();
    return super.preHandle(request, response, handler);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    // output log
    logger.trace(startNanoTime, System.nanoTime());
  }
}
