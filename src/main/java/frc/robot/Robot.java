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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(Robot.class.getName());

   // Capture the period at start (shouldn't ever change)
   @SuppressWarnings("unused")
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

   @SuppressWarnings("unused")
   private RobotContainer robotContainer;

   private Command m_autonomousCommand;

   /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
   @Override
   public void robotInit() {
      logger.info("initializing");

      // Get the loop period set for robot
      loopPeriod = getPeriod();

      // Wait until we get the configuration data from driver station
      waitForDriverStationData();

      // Initialize the dashboard to false for status
      SmartDashboard.putBoolean(TelemetryNames.Misc.initStatus, false);

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

      logger.info("initialized");
   }

   /**
    * Holds the constructor until we receive notice the DriverStation (laptop)
    * is attached, as it holds the run-time configuration.
    **/
   private void waitForDriverStationData() {
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

      logger.info("initialized disabled");
   }

   /**
    * This function is called periodically while the robot is in Disabled mode.
    */
   @Override
   public void disabledPeriodic() {
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
      // running. If you want the autonomous to continue until interrupted by
      // another command, remove this line or comment it out.
      if (m_autonomousCommand != null) {
         m_autonomousCommand.cancel();
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
    */
   @Override
   public void testInit() {
      logger.info("initializing test");

      // FIXME: Should be / is already elsewhere?
      // Cancels all running commands at the start of test mode.
      CommandScheduler.getInstance().cancelAll();

      ModeFollowers.getInstance().initTest();

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
}
