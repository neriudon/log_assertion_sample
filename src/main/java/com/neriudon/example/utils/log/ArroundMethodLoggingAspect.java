package com.neriudon.example.utils.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Output performance log for specified methods.
 *
 */
public class ArroundMethodLoggingAspect {

  private static final PerformanceLogger logger = PerformanceLogger.getLog();

  public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
    // set start processing time
    long startTime = System.nanoTime();
    try {
      return joinPoint.proceed();
    } finally {
      // output time and method detail
      logger.trace(startTime, System.nanoTime(), (MethodSignature) joinPoint.getSignature());
    }
  }
}
