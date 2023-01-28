// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.DoubleArrayTopic;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.config.VersionInfo;
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

   private Command m_autonomousCommand;

   private RobotContainer m_robotContainer;

   private StringPublisher configPub;
   private StringSubscriber configSub;

   /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
   @Override
   public void robotInit() {
      // Instantiate our RobotContainer. This will perform all our button bindings,
      // and put our autonomous chooser on the dashboard.
      m_robotContainer = new RobotContainer();

      logger.info("Hellow, World (2023)!");
      System.out.println("Hello, World (2023)!");

      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      NetworkTable table = inst.getTable("501Prefs");

      StringTopic configTopic = table.getStringTopic("config");

      configPub = configTopic.publish();
      configPub.set(VersionInfo.version);

      configTopic.setPersistent(true);
      configTopic.setRetained(true);

      configPub.close();

      DoubleTopic drivePID_PTopic = table.getDoubleTopic("Drive/P");
      DoubleEntry drivePID_PEntry = drivePID_PTopic.getEntry(0.0);
      drivePID_PEntry.set(0.0);
      DoubleTopic drivePID_ITopic = table.getDoubleTopic("Drive/I");
      DoubleEntry drivePID_IEntry = drivePID_ITopic.getEntry(0.0);
      drivePID_IEntry.set(0.0);
      DoubleTopic drivePID_DTopic = table.getDoubleTopic("Drive/D");
      DoubleEntry drivePID_DEntry = drivePID_DTopic.getEntry(0.0);
      drivePID_DEntry.set(0.0);
      DoubleTopic drivePID_FTopic = table.getDoubleTopic("Drive/F");
      DoubleEntry drivePID_FEntry = drivePID_FTopic.getEntry(0.0);
      drivePID_FEntry.set(0.0);

      drivePID_PTopic.setPersistent(true);
      drivePID_PTopic.setRetained(true);
      drivePID_ITopic.setPersistent(true);
      drivePID_ITopic.setRetained(true);
      drivePID_DTopic.setPersistent(true);
      drivePID_DTopic.setRetained(true);
      drivePID_FTopic.setPersistent(true);
      drivePID_FTopic.setRetained(true);

      configSub = configTopic.subscribe("");
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

   /** This function is called once each time the robot enters Disabled mode. */
   @Override
   public void disabledInit() {
   }

   @Override
   public void disabledPeriodic() {
   }

   /**
    * This autonomous runs the autonomous command selected by your
    * {@link RobotContainer} class.
    */
   @Override
   public void autonomousInit() {
      m_autonomousCommand = m_robotContainer.getAutonomousCommand();

      // schedule the autonomous command (example)
      if (m_autonomousCommand != null) {
         m_autonomousCommand.schedule();
      }
   }

   /** This function is called periodically during autonomous. */
   @Override
   public void autonomousPeriodic() {
   }

   @Override
   public void teleopInit() {
      // This makes sure that the autonomous stops running when teleop starts
      // running. If you want the autonomous to continue until interrupted by
      // another command, remove this line or comment it out.
      if (m_autonomousCommand != null) {
         m_autonomousCommand.cancel();
      }

      logger.info("config={}", configSub.get());
   }

   /** This function is called periodically during operator control. */
   @Override
   public void teleopPeriodic() {
   }

   @Override
   public void testInit() {
      // Cancels all running commands at the start of test mode.
      CommandScheduler.getInstance().cancelAll();
   }

   /** This function is called periodically during test mode. */
   @Override
   public void testPeriodic() {
   }

   /** This function is called once when the robot is first started up. */
   @Override
   public void simulationInit() {
   }

   /** This function is called periodically whilst in simulation. */
   @Override
   public void simulationPeriodic() {
   }

}
