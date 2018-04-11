package com.neriudon.example.utils.log;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performance logger.
 *
 */
public class PerformanceLogger {

  private static final PerformanceLogger instance = new PerformanceLogger();

  private static final Logger logger = LoggerFactory.getLogger("PerformanceLog");

  private static final String TOTAL_PROCESSING_TIME = "TOTAL";

  private static final String METHOD_PROCESSING_TIME = "METHOD";

  private final int nanosToMicros = 1000;

  private PerformanceLogger() {}

  public static PerformanceLogger getLog() {
    return instance;
  }

  private void trace(String format, Object... args) {
    logger.trace(format, args);
  }

  /**
   * <p>
   * Ourput performance log.
   * </p>
   * the log includes...
   * <li>log for what</li>
   * <li>processing time(micro seconds)</li>
   * 
   * @param startNanoTime processing start time(ns).
   * @param endNanoTime processing end time(ns).
   * 
   */
  public void trace(long startNanoTime, long endNanoTime) {
    trace("{}\t{}", TOTAL_PROCESSING_TIME, (endNanoTime - startNanoTime) / nanosToMicros);
  }

  /**
   * <p>
   * Ourput performance log for methods.
   * </p>
   * the log includes...
   * <li>log for what</li>
   * <li>processing time(micro seconds)</li>
   * <li>method's return type</li>
   * <li>class name</li>
   * <li>method name</li>
   * <li>method parameters</li>
   * 
   * @param startNanoTime processing start time(ns).
   * @param endNanoTime processing end time(ns).
   * @param signature method signature.
   */
  public void trace(long startNanoTime, long endNanoTime, MethodSignature signature) {
    trace("{}\t{}\t{} {}.{}{}", METHOD_PROCESSING_TIME,
        (endNanoTime - startNanoTime) / nanosToMicros, signature.getReturnType().getSimpleName(),
        signature.getMethod().getDeclaringClass().getName(), signature.getMethod().getName(),
        Arrays.stream(signature.getParameterTypes()).map(c -> c.getSimpleName())
            .collect(Collectors.joining(",", "(", ")")));
  }
}
