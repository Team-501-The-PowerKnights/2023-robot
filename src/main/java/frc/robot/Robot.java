/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.modules.led.LEDModuleFactory;
import frc.robot.preferences.PreferencesManager;
import frc.robot.telemetry.TelemetryManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKColor8Bit;

import riolog.Level;
import riolog.PKLogger;
import riolog.ProblemTracker;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(Robot.class.getName());

   // Capture the period at start (shouldn't ever change)
   private static double loopPeriod;

   // Flag for started/running autonomous part of match
   @SuppressWarnings("unused")
   private boolean autonomousRunning;
   // Flag for having run first autonomous loop
   private boolean autonomousFirstRun;
   // Flag for having completed autonomous part of match
   private static boolean autonomousComplete;

   // Flag for having started/running teleop part of match
   private boolean teleopRunning;
   // Flag for having run first teleop loop
   private boolean teleopFirstRun;
   // Flag for having completed teleop part of match
   private static boolean teleopComplete;

   private RobotContainer robotContainer;

   private Command autonomousCommand;

   /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
   @Override
   public void robotInit() {
      logger.info("initializing");

      // Wait until we get the configuration data from driver station
      waitForDriverStationData();

      // Initialize the dashboard to false for status
      SmartDashboard.putBoolean(TelemetryNames.Misc.initStatus, false);

      // Get the loop period set for robot
      loopPeriod = getPeriod();

      // Instantiate our RobotContainer. This will perform all our button bindings,
      // and put our autonomous chooser on the dashboard.
      robotContainer = new RobotContainer();

      // Initialize state variables
      autonomousRunning = false;
      autonomousFirstRun = false;
      autonomousComplete = false;
      teleopRunning = false;
      teleopFirstRun = false;
      teleopComplete = false;

      // Create the chooser for FMS connected override
      createFmsOverrideChooser();

      // Set up end game determiner
      endGameStarted = false;
      SmartDashboard.putBoolean(TelemetryNames.Misc.endGameStarted, endGameStarted);
      addPeriodic(endGameDeterminer, 2.0);

      // Set up the telemetry reporter
      addPeriodic(telemetryReporter, 5 * getLoopPeriod());

      // Put indication of initialization status on dash
      determineInitStatus();

      // WPILib default is to disable this now; but just in case ...
      LiveWindow.setEnabled(false);
      // But we want access to the CommandScheduler telemetry content
      // LiveWindow.disableAllTelemetry();
      // LiveWindow.enableTelemetry(CommandScheduler.getInstance());

      // Create the chooser for the Logger level
      createLoggerLevelChooser();

      logger.info("initialized");
   }

   /**
    * Holds the constructor until we receive notice the DriverStation (laptop)
    * is attached, as it holds the run-time configuration.
    **/
   private void waitForDriverStationData() {
      @SuppressWarnings("unused")
      long count = 0;
      // FIXME: This might not be right (there are handlers to install?)
      // while (DriverStation.isDSAttached()) { // isNewControlData()
      // if ((count % 100) == 0) {
      // logger.trace("Waiting ...");
      // }
      // try {
      // Thread.sleep(100);
      // } catch (InterruptedException ex) {
      // logger.error("exception for sleep: ", ex);
      // }
      // count++;
      // }
   }

   private void determineInitStatus() {
      // TODO: Make tri-color status when implemented
      long errorCount = ProblemTracker.getErrorCount();
      long warnCount = ProblemTracker.getWarnCount();
      logger.info("init status: errorCount={}, warnCount={}", errorCount, warnCount);
      // red for bad, green for good (so reverse sense)
      boolean status = !((errorCount != 0) || (warnCount != 0));
      SmartDashboard.putBoolean(TelemetryNames.Misc.initStatus, status);

      if (errorCount != 0) {
         LEDModuleFactory.getInstance().setColor(PKColor8Bit.redRGB);
      } else if (warnCount != 0) {
         LEDModuleFactory.getInstance().setColor(PKColor8Bit.yellowRGB);
      } else {
         LEDModuleFactory.getInstance().setColor(PKColor8Bit.greenRGB);
      }
      // TODO: Parse network tables for all status and do a roll-up
   }

   /**
    * This function is called every 20 ms, no matter the mode. Use this for items
    * like diagnostics that you want ran during disabled, autonomous, teleoperated
    * and test.
    *
    * <p>
    * This runs after the mode specific periodic functions, but before LiveWindow
    * and SmartDashboard integrated updating.
    */
   @Override
   public void robotPeriodic() {
      // Runs the Scheduler. This is responsible for polling buttons, adding
      // newly-scheduled commands, running already-scheduled commands, removing
      // finished or interrupted commands, and running subsystem periodic()
      // methods. This must be called from the robot's periodic block in order
      // for anything in the Command-based framework to work.
      CommandScheduler.getInstance().run();
   }

   /**
    * This function is called once each time the robot enters Disabled mode.
    */
   @Override
   public void disabledInit() {
      logger.info("initializing disabled");

      ModeFollowers.getInstance().initDisabled();

      if (isMatchComplete()) {
         logger.info("match complete");

         logFinalVisionData();

         logFinalPreferences();

         logMatchData();

         logErrorCounts();

         // for (IModule m : modules) {
         // m.disable();
         // }
         // for (ISensor s : sensors) {
         // s.disable();
         // }
         // for (ISubsystem s : subsystems) {
         // s.disable();
         // }
      }

      // (Re-)initialize end game state
      endGameStarted = false;
      SmartDashboard.putBoolean(TelemetryNames.Misc.endGameStarted, endGameStarted);

      // Should we be checking autonomous mode selection?
      autoModeCheckEnabled = !autonomousComplete;

      logger.info("initialized disabled");
   }

   /**
    * Log the data associated with the vision to the tail of the log file.
    **/
   private void logFinalVisionData() {
      logger.info("vision data:");
   }

   /**
    * Log the data associated with the preferences to the tail of the log file.
    **/
   private void logFinalPreferences() {
      logger.info("preferences:");
      PreferencesManager.getInstance().logPreferences(logger);
   }

   /**
    * Log the data associated with the match to the tail of the log file. This
    * allows us to easily determine whether it is a real match, and what match it
    * was.
    **/
   private void logMatchData() {
      logger.info("EventName:     {}", DriverStation.getEventName());
      logger.info("MatchType:     {}", DriverStation.getMatchType());
      logger.info("MatchNumber:   {}", DriverStation.getMatchNumber());
      logger.info("ReplayNumber:  {}", DriverStation.getReplayNumber());
      logger.info("Alliance:      {}", DriverStation.getAlliance());
      logger.info("Location:      {}", DriverStation.getLocation());
   }

   /**
    * Log the count of errors and warnings from the logger to the tail of the
    * log file.
    */
   private void logErrorCounts() {
      long errorCount = ProblemTracker.getErrorCount();
      long warnCount = ProblemTracker.getWarnCount();
      logger.info("error counts: errorCount={}, warnCount={}", errorCount, warnCount);
   }

   /**
    * This function is called periodically while the robot is in Disabled mode.
    */
   @Override
   public void disabledPeriodic() {
      // Has a "real" auto been selected yet?
      boolean realAutoSelected = robotContainer.isRealAutoSelected();
      SmartDashboard.putBoolean(TelemetryNames.Misc.realAuto, realAutoSelected);

      displayAutoSelectionStatus(realAutoSelected);

      Level level = loggerLevelChooser.getSelected();
      PKLogger.setLevel(level);
   }

   private boolean autoModeCheckEnabled = true;

   private long autoModeCheckDelay = (long) (20.0 / getPeriod());
   private long autoErrorOnPeriod = (long) (1.5 / getPeriod());
   private long autoErrorOffPeriod = (long) (0.75 / getPeriod());
   private long autoErrorCount;
   private boolean autoErrorOn = true;

   /**
    * Displays a flashing red in the LEDs if a real auto hasn't been
    * selected. Waits a bit for transitioning from green though (so
    * can see the state of start-up).
    *
    * @param realAutoSelected
    */
   private void displayAutoSelectionStatus(boolean realAutoSelected) {
      if (autoModeCheckEnabled && !robotContainer.isRealAutoSelected()) {
         if (autoModeCheckDelay > 0) {
            --autoModeCheckDelay;
         } else if (!realAutoSelected) {
            if (--autoErrorCount <= 0) {
               if (autoErrorOn) {
                  LEDModuleFactory.getInstance().setColor(PKColor8Bit.blackRGB);
                  autoErrorCount = autoErrorOffPeriod;
               } else {
                  LEDModuleFactory.getInstance().setColor(PKColor8Bit.redRGB);
                  autoErrorCount = autoErrorOnPeriod;
               }
               autoErrorOn = !autoErrorOn;
            }
         }
      } else {
         autoModeCheckEnabled = false;
         LEDModuleFactory.getInstance().setColor(PKColor8Bit.greenRGB);
      }
   }

   /**
    * This function is called once each time the robot exits Disabled mode.
    */
   @Override
   public void disabledExit() {
      logger.info("exiting disabled");

      ModeFollowers.getInstance().exitDisabled();

      logger.info("exited disable");
   }

   /**
    * This function is called once each time the robot enters Autonomous mode.
    */
   @Override
   public void autonomousInit() {
      logger.info("initializing autonomous");

      autonomousRunning = true;
      autonomousFirstRun = false;
      autonomousComplete = false;

      ModeFollowers.getInstance().initAutonomous();

      // schedule the autonomous command (example)
      autonomousCommand = robotContainer.getAutonomousCommand();
      if (autonomousCommand != null) {
         autonomousCommand.schedule();
      }

      logger.info("initialized autonomous");
   }

   /**
    * This function is called periodically while the robot is in Autonomous mode.
    */
   @Override
   public void autonomousPeriodic() {
      if (!autonomousFirstRun) {
         autonomousFirstRun = true;
         logger.info("first run of autonomous periodic");
      }
   }

   /**
    * This function is called once each time the robot exits Autonomous mode.
    */
   @Override
   public void autonomousExit() {
      logger.info("exiting autonomous");

      autonomousRunning = false;
      autonomousComplete = true;

      ModeFollowers.getInstance().exitAutonomous();

      logger.info("exited autonomous");
   }

   /**
    * This function is called once each time the robot enters Teleop mode.
    */
   @Override
   public void teleopInit() {
      logger.info("initializing teleop");

      teleopRunning = true;
      teleopFirstRun = false;
      teleopComplete = false;

      // FIXME: Should be / is already elsewhere?
      // This makes sure that the autonomous stops running when teleop starts
      // running.
      if (autonomousCommand != null) {
         autonomousCommand.cancel();
      }

      ModeFollowers.getInstance().initTeleop();

      logger.info("initialized teleop");
   }

   /**
    * This function is called periodically during Teleop mode.
    */
   @Override
   public void teleopPeriodic() {
      if (!teleopFirstRun) {
         teleopFirstRun = true;
         logger.info("first run of teleop periodic");
      }
   }

   /**
    * This function is called once each time the robot exits Teleop mode.
    */
   @Override
   public void teleopExit() {
      logger.info("exiting teleop");

      teleopRunning = false;
      teleopComplete = true;

      ModeFollowers.getInstance().exitTeleop();

      logger.info("exited teleop");
   }

   /**
    * This function is called once each time the robot enters Test mode.
    *
    * Note:
    * https://www.chiefdelphi.com/t/running-a-command-in-test-mode/418286/3
    * https://www.chiefdelphi.com/t/can-you-run-commands-in-test-methods-of-robot-java/349402
    */
   @Override
   public void testInit() {
      logger.info("initializing test");

      // Cancels all running commands at the start of test mode.
      CommandScheduler.getInstance().cancelAll();

      // We don't want the "packaged" LiveWindow, but our own testing
      LiveWindow.setEnabled(false);
      logger.info("disabled LiveWindow enabled = {}", LiveWindow.isEnabled());

      ModeFollowers.getInstance().initTest();

      /*
       * Put any code for Init here
       */

      logger.info("initialized test");
   }

   /**
    * This function is called periodically during Test mode.
    */
   @Override
   public void testPeriodic() {
   }

   /**
    * This function is called once each time the robot exits Test mode.
    */
   @Override
   public void testExit() {
      logger.info("exiting test");

      /*
       * Put any code for Exit here
       */

      ModeFollowers.getInstance().exitTest();

      logger.info("exited test");
   }

   // Chooser for overriding field connection in pit
   private static SendableChooser<Boolean> fmsOverrideChooser;

   // TODO: Comment
   // TODO: Move to 501 Robot?
   private static void createFmsOverrideChooser() {
      fmsOverrideChooser = new SendableChooser<>();

      fmsOverrideChooser.setDefaultOption("Use Real FMS Connect", Boolean.FALSE);
      fmsOverrideChooser.addOption("Override FMS Connect", Boolean.TRUE);

      SmartDashboard.putData("FMS Override", fmsOverrideChooser);
   }

   // TODO: Comment
   // TODO: Move to 501 Robot?
   static public boolean isFieldConnected() {
      if (DriverStation.isFMSAttached()) {
         return true;
      } else {
         return fmsOverrideChooser.getSelected();
      }
   }

   // TODO: Comment
   // TODO: Move to 501 Robot?
   static public double getLoopPeriod() {
      return loopPeriod;
   }

   // Flag for in end game of match
   private static boolean endGameStarted;
   // Periodic runnable to do the determining off main loop
   private Runnable endGameDeterminer = new Runnable() {

      @Override
      public void run() {
         // Have to have field connected, otherwise remaining seconds counts up
         // Have to be running teleop
         // Have to not have triggered the end game start yet
         if (isFieldConnected() && teleopRunning && !endGameStarted) {
            double remainingSeconds = DriverStation.getMatchTime();
            if (remainingSeconds <= 30) {
               endGameStarted = true;
               SmartDashboard.putBoolean(TelemetryNames.Misc.endGameStarted, endGameStarted);
            }
         }
      }

   };

   // TODO: Comment
   // TODO: Move to 501 Robot?
   static public boolean isEndGameStarted() {
      return endGameStarted;
   }

   // TODO: Comment
   // TODO: Move to 501 Robot?
   static public boolean isMatchComplete() {
      return (autonomousComplete && teleopComplete);
   }

   // Periodic runnable to do the reporting off main loop
   private Runnable telemetryReporter = new Runnable() {

      @Override
      public void run() {
         // Update the telemetry
         TelemetryManager.getInstance().sendTelemetry();
      }

   };

   // Chooser for overriding logger level
   private static SendableChooser<Level> loggerLevelChooser;

   // TODO: Comment
   // TODO: Move to 501 Robot?
   private static void createLoggerLevelChooser() {
      loggerLevelChooser = new SendableChooser<>();

      loggerLevelChooser.addOption("ERROR", Level.ERROR);
      loggerLevelChooser.addOption("WARN", Level.WARN);
      loggerLevelChooser.addOption("INFO", Level.INFO);
      loggerLevelChooser.setDefaultOption("DEBUG", Level.DEBUG);
      loggerLevelChooser.addOption("TRACE", Level.TRACE);

      SmartDashboard.putData("Logger Level", loggerLevelChooser);
   }

}
