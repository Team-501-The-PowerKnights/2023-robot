// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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

   // Need to WPILib wrapper classes to function w/ Drive class
   private WPI_VictorSPX leftFront;
   private WPI_VictorSPX leftRear;
   private WPI_VictorSPX rightFront;
   private WPI_VictorSPX rightRear;

   private DifferentialDrive drive;

   private Joystick driverStick;

   private PowerDistribution powerD;

   private PneumaticHub pneumH;
   private static final int gripperSolenoidChannel = 0;
   private Solenoid gripperSolenoid;

   /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
   @Override
   public void robotInit() {
      // Instantiate our RobotContainer. This will perform all our button bindings,
      // and put our autonomous chooser on the dashboard.
      m_robotContainer = new RobotContainer();

      powerD = new PowerDistribution(1, ModuleType.kRev);

      // Instantiate and enable
      pneumH = new PneumaticHub(2);
      pneumH.enableCompressorDigital();
      // Instantiate solenoid and set to false(?) as initial state
      gripperSolenoid = pneumH.makeSolenoid(gripperSolenoidChannel);
      gripperSolenoid.set(false);

      // Instantiation and factory default-ing motors (can't persist due to timing)
      leftFront = new WPI_VictorSPX(11);
      checkError(leftFront.configFactoryDefault(), "LF restore factory defaults {}");
      leftRear = new WPI_VictorSPX(12);
      checkError(leftRear.configFactoryDefault(), "LR restore factory defaults {}");
      rightFront = new WPI_VictorSPX(13);
      checkError(rightFront.configFactoryDefault(), "RF restore factory defaults {}");
      rightRear = new WPI_VictorSPX(14);
      checkError(rightRear.configFactoryDefault(), "RR restore factory defaults {}");

      // Following mode (Rear follows Front)
      leftRear.follow(leftFront, FollowerType.PercentOutput);

      // Following mode (Rear follows Front)
      rightFront.setInverted(true);
      rightRear.follow(rightFront, FollowerType.PercentOutput);

      drive = new DifferentialDrive(leftFront, rightFront);

      driverStick = new Joystick(0);
   }

   // last error (not the same as kOk)
   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private ErrorCode lastError;

   private void checkError(ErrorCode error, String message) {
      if (error != ErrorCode.OK) {
         lastError = error;
         logger.error(message, error);
      }
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

   /** This function is called once each time the robot enters DisSabled mode. */
   @Override
   public void disabledInit() {
      // leftFront.set(VictorSPXControlMode.PercentOutput, 0);
      // // leftRear.set(VictorSPXControlMode.PercentOutput, 0);
      // rightFront.set(VictorSPXControlMode.PercentOutput, 0);
      // // rightRear.set(VictorSPXControlMode.PercentOutput, 0);
      drive.arcadeDrive(0, 0);
   }

   @Override
   public void disabledPeriodic() {
   }

   @Override
   public void disabledExit() {
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
   public void autonomousExit() {
   }

   @Override
   public void teleopInit() {
      // This makes sure that the autonomous stops running when teleop starts
      // running. If you want the autonomous to continue until interrupted by
      // another command, remove this line or comment it out.
      if (m_autonomousCommand != null) {
         m_autonomousCommand.cancel();
      }
   }

   /** This function is called periodically during operator control. */
   @Override
   public void teleopPeriodic() {
      // drive.arcadeDrive(-driverStick.getY(), -driverStick.getX());

      drive.arcadeDrive(-driverStick.getRawAxis(1) * 0.6,
            -driverStick.getRawAxis(4) * 0.6);
   }

   @Override
   public void teleopExit() {
   }

   @Override
   public void testInit() {
      // Want our programmed interaction with robot.
      LiveWindow.setEnabled(false);
      // Cancels all running commands at the start of test mode.
      CommandScheduler.getInstance().cancelAll();
   }

   /** This function is called periodically during test mode. */
   @Override
   public void testPeriodic() {
   }

   @Override
   public void testExit() {
   }

}
