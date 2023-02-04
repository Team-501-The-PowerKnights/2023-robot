/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package riolog;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class PKLogger implements Logger {

   private final ch.qos.logback.classic.Logger logger;

   private static long warnCount = 0;

   public long getWarnCount() {
      return warnCount;
   }

   private static long errorCount = 0;

   public long getErrorCount() {
      return errorCount;
   }

   PKLogger(ch.qos.logback.classic.Logger logger) {
      this.logger = logger;
   }

   @Override
   public String getName() {
      return logger.getName();
   }

   @Override
   public boolean isTraceEnabled() {
      return logger.isTraceEnabled();
   }

   @Override
   public void trace(String msg) {
      logger.trace(msg);
   }

   @Override
   public void trace(String format, Object arg) {
      logger.trace(format, arg);
   }

   @Override
   public void trace(String format, Object arg1, Object arg2) {
      logger.trace(format, arg1, arg2);
   }

   @Override
   public void trace(String format, Object... arguments) {
      logger.trace(format, arguments);
   }

   @Override
   public void trace(String msg, Throwable t) {
      logger.trace(msg, t);
   }

   @Override
   public boolean isTraceEnabled(Marker marker) {
      return logger.isTraceEnabled(marker);
   }

   @Override
   public void trace(Marker marker, String msg) {
      logger.trace(marker, msg);
   }

   @Override
   public void trace(Marker marker, String format, Object arg) {
      logger.trace(marker, format, arg);
   }

   @Override
   public void trace(Marker marker, String format, Object arg1, Object arg2) {
      logger.trace(marker, format, arg1, arg2);
   }

   @Override
   public void trace(Marker marker, String format, Object... argArray) {
      logger.trace(marker, format, argArray);
   }

   @Override
   public void trace(Marker marker, String msg, Throwable t) {
      logger.trace(marker, msg, t);
   }

   @Override
   public boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   @Override
   public void debug(String msg) {
      logger.debug(msg);
   }

   @Override
   public void debug(String format, Object arg) {
      logger.debug(format, arg);
   }

   @Override
   public void debug(String format, Object arg1, Object arg2) {
      logger.debug(format, arg1, arg2);
   }

   @Override
   public void debug(String format, Object... arguments) {
      logger.debug(format, arguments);
   }

   @Override
   public void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }

   @Override
   public boolean isDebugEnabled(Marker marker) {
      return logger.isDebugEnabled(marker);
   }

   @Override
   public void debug(Marker marker, String msg) {
      logger.debug(marker, msg);
   }

   @Override
   public void debug(Marker marker, String format, Object arg) {
      logger.debug(marker, format, arg);
   }

   @Override
   public void debug(Marker marker, String format, Object arg1, Object arg2) {
      logger.debug(marker, format, arg1, arg2);
   }

   @Override
   public void debug(Marker marker, String format, Object... arguments) {
      logger.debug(marker, format, arguments);
   }

   @Override
   public void debug(Marker marker, String msg, Throwable t) {
      logger.debug(marker, msg, t);
   }

   @Override
   public boolean isInfoEnabled() {
      return logger.isInfoEnabled();
   }

   @Override
   public void info(String msg) {
      logger.info(msg);
   }

   @Override
   public void info(String format, Object arg) {
      logger.info(format, arg);
   }

   @Override
   public void info(String format, Object arg1, Object arg2) {
      logger.info(format, arg1, arg2);
   }

   @Override
   public void info(String format, Object... arguments) {
      logger.info(format, arguments);
   }

   @Override
   public void info(String msg, Throwable t) {
      logger.info(msg, t);
   }

   @Override
   public boolean isInfoEnabled(Marker marker) {
      return logger.isInfoEnabled(marker);
   }

   @Override
   public void info(Marker marker, String msg) {
      logger.info(marker, msg);
   }

   @Override
   public void info(Marker marker, String format, Object arg) {
      logger.info(marker, format, arg);
   }

   @Override
   public void info(Marker marker, String format, Object arg1, Object arg2) {
      logger.info(marker, format, arg1, arg2);
   }

   @Override
   public void info(Marker marker, String format, Object... arguments) {
      logger.info(marker, format, arguments);
   }

   @Override
   public void info(Marker marker, String msg, Throwable t) {
      logger.info(marker, msg, t);
   }

   @Override
   public boolean isWarnEnabled() {
      return logger.isWarnEnabled();
   }

   @Override
   public void warn(String msg) {
      warnCount++;
      logger.warn(msg);
   }

   @Override
   public void warn(String format, Object arg) {
      warnCount++;
      logger.warn(format, arg);
   }

   @Override
   public void warn(String format, Object... arguments) {
      warnCount++;
      logger.warn(format, arguments);
   }

   @Override
   public void warn(String format, Object arg1, Object arg2) {
      warnCount++;
      logger.warn(format, arg1, arg2);
   }

   @Override
   public void warn(String msg, Throwable t) {
      warnCount++;
      logger.warn(msg, t);
   }

   @Override
   public boolean isWarnEnabled(Marker marker) {
      return logger.isWarnEnabled(marker);
   }

   @Override
   public void warn(Marker marker, String msg) {
      warnCount++;
      logger.warn(marker, msg);
   }

   @Override
   public void warn(Marker marker, String format, Object arg) {
      warnCount++;
      logger.warn(marker, format, arg);
   }

   @Override
   public void warn(Marker marker, String format, Object arg1, Object arg2) {
      warnCount++;
      logger.warn(marker, format, arg1, arg2);
   }

   @Override
   public void warn(Marker marker, String format, Object... arguments) {
      warnCount++;
      logger.warn(marker, format, arguments);
   }

   @Override
   public void warn(Marker marker, String msg, Throwable t) {
      warnCount++;
      logger.warn(marker, msg, t);
   }

   @Override
   public boolean isErrorEnabled() {
      return logger.isErrorEnabled();
   }

   @Override
   public void error(String msg) {
      errorCount++;
      logger.error(msg);
   }

   @Override
   public void error(String format, Object arg) {
      errorCount++;
      logger.error(format, arg);
   }

   @Override
   public void error(String format, Object arg1, Object arg2) {
      errorCount++;
      logger.error(format, arg1, arg2);
   }

   @Override
   public void error(String format, Object... arguments) {
      errorCount++;
      logger.error(format, arguments);
   }

   @Override
   public void error(String msg, Throwable t) {
      errorCount++;
      logger.error(msg, t);
   }

   @Override
   public boolean isErrorEnabled(Marker marker) {
      return logger.isErrorEnabled(marker);
   }

   @Override
   public void error(Marker marker, String msg) {
      errorCount++;
      logger.error(marker, msg);
   }

   @Override
   public void error(Marker marker, String format, Object arg) {
      errorCount++;
      logger.error(marker, format, arg);
   }

   @Override
   public void error(Marker marker, String format, Object arg1, Object arg2) {
      errorCount++;
      logger.error(marker, format, arg1, arg2);
   }

   @Override
   public void error(Marker marker, String format, Object... arguments) {
      errorCount++;
      logger.error(marker, format, arguments);
   }

   @Override
   public void error(Marker marker, String msg, Throwable t) {
      errorCount++;
      logger.error(marker, msg, t);
   }

}
