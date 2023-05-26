/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package riolog;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;

/**
 *
 **/
public class PKLogger {

   // Logger setting for default level
   // @formatter:off
   // private static final Level defaultLevel = Level.INFO;
   private static final Level defaultLevel = Level.DEBUG;
   // private static final Level defaultLevel = Level.TRACE;
   // @formatter:on

   // Logger setting for message format/content
   // @formatter:off
   // "level" length format does not work for console :<
   // Includes line numbers
   private static final String pattern = "%date{HH:mm:ss.SSS} %-5level [%thread] %logger{10}[%3line] %msg%n";
   // Leaves out line numbers
   //private static final String pattern = "%date{HH:mm:ss.SSS} %-5level [%thread] %logger{10} %msg%n";
   // @formatter:on

   private static final String logMountPoint = "/media/sda1/";
   private static final String logDirName = "501logs/";
   private static final String logFileName = "logfile-";
   private static final String logFileExtension = ".log";
   private static final String logFile;
   private static final boolean useLogFile;

   private static final ch.qos.logback.classic.Logger rootLogger;

   static {
      // Log info about the configuration of the loggin subsystem
      LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
      StatusPrinter.print(lc);

      final File logDir = new File(logMountPoint + logDirName);
      useLogFile = logDir.exists();
      System.out.println("useLogFile=" + useLogFile);
      if (useLogFile) {
         final StringBuilder buf = new StringBuilder();
         buf.append(logMountPoint);
         buf.append(logDirName).append(logFileName);
         final int keepBufLen = buf.length();
         for (int i = 1; i <= 100; i++) {
            buf.setLength(keepBufLen);
            buf.append(String.format("%03d", i));
            buf.append(logFileExtension);
            System.out.println("[" + i + "]:" + buf.toString());
            final File logFile = new File(buf.toString());
            if (!logFile.exists()) {
               break;
            }
         }
         // If we get to 100; we are just going to re-use it regardless
         logFile = buf.toString();
      } else {
         logFile = "";
      }

      // Set the 'default' logging level on the root logger
      rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
      setLevel(rootLogger, defaultLevel);
   }

   public static Logger getLogger(String loggerName) {
      final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);

      final LoggerContext lc = logger.getLoggerContext();
      // we are not interested in auto-configuration
      // lc.reset(); // this breaks the depth of logging somehow

      /*
       * Can't share encoders, so each appender needs to have it's own
       */

      {
         final PatternLayoutEncoder ple = new PatternLayoutEncoder();
         ple.setContext(lc);
         ple.setPattern(pattern);
         ple.start();

         final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
         consoleAppender.setContext(lc);
         consoleAppender.setEncoder(ple);
         consoleAppender.start();
         //
         logger.addAppender(consoleAppender);
      }

      if (useLogFile) {
         final PatternLayoutEncoder ple = new PatternLayoutEncoder();
         ple.setContext(lc);
         ple.setPattern(pattern);
         ple.start();

         final FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
         fileAppender.setFile(logFile);
         fileAppender.setContext(lc);
         fileAppender.setEncoder(ple);
         fileAppender.setAppend(true);
         fileAppender.start();
         //
         logger.addAppender(fileAppender);
      }

      // setLevel(logger, defaultLevel); /* set if each logger has unique level */
      logger.setAdditive(false); /* set to true if root should log too */

      return logger;
   }

   /**
    * Sets the logging level on the specified logger.
    *
    * @param logger
    * @param level
    */
   public static void setLevel(Logger logger, Level level) {
      ((ch.qos.logback.classic.Logger) logger).setLevel(level.level);
   }

   /**
    * Sets the logging level on the 'root' logger.
    *
    * @param level
    */
   public static void setLevel(Level level) {
      if (rootLogger.getLevel().toInt() != level.level.toInt()) {
         rootLogger.setLevel(level.level);
      }
   }

}
