// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;
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

   @SuppressWarnings("unused")
   private RobotContainer m_robotContainer;

   private PowerDistribution powerD;

   private PneumaticHub pneumH;

   /*
    * Driver Stuff
    */
   private Joystick driverStick;

   private CANSparkMax leftFront;
   private CANSparkMax leftRear;
   private CANSparkMax rightFront;
   private CANSparkMax rightRear;

   private DifferentialDrive drive;

   /*
    * Operator Stuff
    */
   private Joystick operatorStick;

   private boolean highArmRotateButtonPressed;
   private boolean midArmRotateButtonPressed;
   private boolean lowArmRotateButtonPressed;

   private final double highArmSetPoint = 30;
   private final double midArmSetPoint = 20;
   private final double lowArmSetPoint = 10;

   private CANSparkMax armRotate;
   private SparkMaxPIDController armRotatePID;
   private RelativeEncoder armRotateEncoder;
   public double rotateP, rotateI, rotateD, rotateIzone, rotateFF, rotateMaxOutput, rotateMinOutput, rotateTarget;
   public boolean rotatePIDDisable;

   private CANSparkMax armExtend;
   private SparkMaxPIDController armExtendPID;
   // private AbsoluteEncoder armExtendEncoder;
   private RelativeEncoder armExtendEncoder;
   public double extendP, extendI, extendD, extendIzone, extendFF, extendMaxOutput, extendMinOutput, extendTarget;
   public boolean extendPIDDisable;

   private TalonFX leftIngest;
   private TalonFX rightIngest;

   private static final int gripperSolenoidChannel = 0;
   private Solenoid gripperSolenoid;
   private boolean gripperOpen;

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
      pneumH.enableCompressorAnalog(80, 110);

      driverStick = new Joystick(0);

      // Instantiation and factory default-ing motors (can't persist due to timing)
      leftFront = new CANSparkMax(11, MotorType.kBrushless);
      checkError(leftFront.restoreFactoryDefaults(), "LF restore factory defaults {}");
      leftRear = new CANSparkMax(12, MotorType.kBrushless);
      checkError(leftRear.restoreFactoryDefaults(), "LF restore factory defaults {}");
      rightFront = new CANSparkMax(13, MotorType.kBrushless);
      checkError(rightFront.restoreFactoryDefaults(), "LF restore factory defaults {}");
      rightRear = new CANSparkMax(14, MotorType.kBrushless);
      checkError(rightRear.restoreFactoryDefaults(), "LF restore factory defaults {}");

      // Following mode (Rear follows Front)
      checkError(leftRear.follow(leftFront), "L setting following mode {}");

      // Inverted (Right from Left) and Following mode (Rear follows Front)
      rightFront.setInverted(true);
      checkError(rightRear.follow(rightFront), "R setting following mode {}");

      drive = new DifferentialDrive(leftFront, rightFront);

      operatorStick = new Joystick(1);

      /****************************************************************************
       * Arm Rotate Setup
       *******************************************************************/

      armRotate = new CANSparkMax(21, MotorType.kBrushless);
      checkError(armRotate.restoreFactoryDefaults(), "AR restore factory defaults {}");
      checkError(armRotate.setIdleMode(IdleMode.kBrake), "AR set idle mode to break {}");
      armRotatePID = armRotate.getPIDController();
      armRotateEncoder = armRotate.getEncoder();
      armRotateEncoder.setPosition(0);

      // PID coefficients
      rotateP = 0.2;
      rotateI = 1e-5;
      rotateD = 1;
      rotateIzone = 0;
      rotateFF = 0;
      rotateMaxOutput = 0.3;
      rotateMinOutput = -0.1;
      // STU:
      rotatePIDDisable = false;

      // set PID coefficients
      armRotatePID.setP(rotateP);
      armRotatePID.setI(rotateI);
      armRotatePID.setD(rotateD);
      armRotatePID.setIZone(rotateIzone);
      armRotatePID.setFF(rotateFF);
      armRotatePID.setOutputRange(rotateMinOutput, rotateMaxOutput);
      if (rotatePIDDisable) {
         armRotatePID.setReference(0.0, ControlType.kVoltage);
      } else {
         armRotatePID.setReference(0.0, ControlType.kPosition);
      }

      // display PID coefficients on SmartDashboard
      SmartDashboard.putBoolean("Arm Rot PID Enabled", rotatePIDDisable);
      SmartDashboard.putNumber("Arm Rot P Gain", rotateP);
      SmartDashboard.putNumber("Arm Rot I Gain", rotateI);
      SmartDashboard.putNumber("Arm Rot D Gain", rotateD);
      SmartDashboard.putNumber("Arm Rot I Zone", rotateIzone);
      SmartDashboard.putNumber("Arm Rot Feed Forward", rotateFF);
      SmartDashboard.putNumber("Arm Rot Max Output", rotateMaxOutput);
      SmartDashboard.putNumber("Arm Rot Min Output", rotateMinOutput);
      SmartDashboard.putNumber("Arm Rot Set Target", 0);
      SmartDashboard.putNumber("Arm Rot Feedback", armRotateEncoder.getPosition());

      // /****************************************************************************
      // * Arm Extend Setup
      // *******************************************************************/

      armExtend = new CANSparkMax(22, MotorType.kBrushless);
      checkError(armExtend.restoreFactoryDefaults(), "AE restore factory defaults {}");
      checkError(armExtend.setIdleMode(IdleMode.kBrake), "AE set idle mode to break {}");
      armExtendPID = armExtend.getPIDController();
      armExtendEncoder = armExtend.getEncoder();
      armExtendEncoder.setPosition(0);

      // PID coefficients Extend
      extendP = 0.2;
      extendI = 0;
      extendD = 1;
      extendIzone = 0;
      extendFF = 0;
      extendMaxOutput = 0.2;
      extendMinOutput = -0.2;
      // STU
      extendPIDDisable = false;

      // set PID coefficients
      armExtendPID.setP(extendP);
      armExtendPID.setI(extendI);
      armExtendPID.setD(extendD);
      armExtendPID.setIZone(extendIzone);
      armExtendPID.setFF(extendFF);
      armExtendPID.setOutputRange(extendMinOutput, extendMaxOutput);
      if (extendPIDDisable) {
         armExtendPID.setReference(0.0, ControlType.kVoltage);
      } else {
         armExtendPID.setReference(0.0, ControlType.kPosition);
      }

      // display PID coefficients on SmartDashboard
      SmartDashboard.putBoolean("Arm Ext PID Enabled", extendPIDDisable);
      SmartDashboard.putNumber("Arm Ext P Gain", extendP);
      SmartDashboard.putNumber("Arm Ext I Gain", extendI);
      SmartDashboard.putNumber("Arm Ext D Gain", extendD);
      SmartDashboard.putNumber("Arm Ext I Zone", extendIzone);
      SmartDashboard.putNumber("Arm Ext Feed Forward", extendFF);
      SmartDashboard.putNumber("Arm Ext Max Output", extendMaxOutput);
      SmartDashboard.putNumber("Arm Ext Min Output", extendMinOutput);
      SmartDashboard.putNumber("Arm Ext Set Target", 0);
      SmartDashboard.putNumber("Arm Ext Feedback", armExtendEncoder.getPosition());

      // leftIngest = new TalonFX(31);
      // rightIngest = new TalonFX(32);

      // Instantiate solenoid and set to false(?) as initial state
      gripperSolenoid = pneumH.makeSolenoid(gripperSolenoidChannel);
      gripperSolenoid.set(false);
      gripperOpen = false;
   }

   // last error (not the same as kOk)
   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private REVLibError lastError;

   private void checkError(REVLibError error, String message) {
      if (error != REVLibError.kOk) {
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

      drive.arcadeDrive(0, 0);

      armRotate.set(0);
      armExtend.set(0);

      // leftIngest.set(TalonFXControlMode.PercentOutput, 0);
      // rightIngest.set(TalonFXControlMode.PercentOutput, 0);
   }

   @Override
   public void disabledPeriodic() {

      // read PID coefficients from SmartDashboard
      rotatePIDDisable = SmartDashboard.getBoolean("Arm Rot PID Enabled", rotatePIDDisable);
      double ar_p = SmartDashboard.getNumber("Arm Rot P Gain", 0);
      double ar_i = SmartDashboard.getNumber("Arm Rot I Gain", 0);
      double ar_d = SmartDashboard.getNumber("Arm Rot D Gain", 0);
      double ar_iz = SmartDashboard.getNumber("Arm Rot I Zone", 0);
      double ar_ff = SmartDashboard.getNumber("Arm Rot Feed Forward", 0);
      double ar_max = SmartDashboard.getNumber("Arm Rot Max Output", 0);
      double ar_min = SmartDashboard.getNumber("Arm Rot Min Output", 0);
      rotateTarget = SmartDashboard.getNumber("Arm Rot Set Target", 0);

      // if PID coefficients on SmartDashboard have changed, write new values to
      // controller
      if ((ar_p != rotateP)) {
         armRotatePID.setP(ar_p);
         rotateP = ar_p;
      }
      if ((ar_i != rotateI)) {
         armRotatePID.setI(ar_i);
         rotateI = ar_i;
      }
      if ((ar_d != rotateD)) {
         armRotatePID.setD(ar_d);
         rotateD = ar_d;
      }
      if ((ar_iz != rotateIzone)) {
         armRotatePID.setIZone(ar_iz);
         rotateIzone = ar_iz;
      }
      if ((ar_ff != rotateFF)) {
         armRotatePID.setFF(ar_ff);
         rotateFF = ar_ff;
      }
      if ((ar_max != rotateMaxOutput) || (ar_min != rotateMinOutput)) {
         armRotatePID.setOutputRange(ar_min, ar_max);
         rotateMinOutput = ar_min;
         rotateMaxOutput = ar_max;
      }

      // read PID coefficients from SmartDashboard
      extendPIDDisable = SmartDashboard.getBoolean("Arm Ext PID Enabled", extendPIDDisable);
      ar_p = SmartDashboard.getNumber("Arm Ext P Gain", 0);
      ar_i = SmartDashboard.getNumber("Arm Ext I Gain", 0);
      ar_d = SmartDashboard.getNumber("Arm Ext D Gain", 0);
      ar_iz = SmartDashboard.getNumber("Arm Ext I Zone", 0);
      ar_ff = SmartDashboard.getNumber("Arm Ext Feed Forward", 0);
      ar_max = SmartDashboard.getNumber("Arm Ext Max Output", 0);
      ar_min = SmartDashboard.getNumber("Arm Ext Min Output", 0);
      extendTarget = SmartDashboard.getNumber("Arm Ext Set Target", 0);

      // if PID coefficients on SmartDashboard have changed, write new values to
      // controller
      if ((ar_p != extendP)) {
         armExtendPID.setP(ar_p);
         extendP = ar_p;
      }
      if ((ar_i != extendI)) {
         armExtendPID.setI(ar_i);
         extendI = ar_i;
      }
      if ((ar_d != extendD)) {
         armExtendPID.setD(ar_d);
         extendD = ar_d;
      }
      if ((ar_iz != extendIzone)) {
         armExtendPID.setIZone(ar_iz);
         extendIzone = ar_iz;
      }
      if ((ar_ff != extendFF)) {
         armExtendPID.setFF(ar_ff);
         extendFF = ar_ff;
      }
      if ((ar_max != extendMaxOutput) || (ar_min != extendMinOutput)) {
         armExtendPID.setOutputRange(ar_min, ar_max);
         extendMinOutput = ar_min;
         extendMaxOutput = ar_max;
      }
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
      m_autonomousCommand = null;

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

      gripperOpen = false;

      highArmRotateButtonPressed = false;
      midArmRotateButtonPressed = false;
      lowArmRotateButtonPressed = false;
   }

   /** This function is called periodically during operator control. */
   @Override
   public void teleopPeriodic() {
      // -****************************************************************
      // -*
      // -* DRIVE
      // -*
      // -****************************************************************

      // FIXME: Make this right calls
      // xSpeed,zRotation
      // drive.arcadeDrive(0, 0);

      // drive.arcadeDrive(-driverStick.getY(), -driverStick.getX());

      // drive.arcadeDrive(-driverStick.getRawAxis(1) * .60,
      // -driverStick.getRawAxis(4) * 0.60);

      // drive.curvatureDrive(-driverStick.getRawAxis(1) * .60,
      // -driverStick.getRawAxis(4) * 0.60, false);

      drive.curvatureDrive(-driverStick.getRawAxis(1) * .60,
            -driverStick.getRawAxis(4) * 0.60, driverStick.getRawButton(5));

      // -****************************************************************
      // -*
      // -* ARM ROTATION
      // -*
      // -****************************************************************

      //
      // FWD: Up, BCK: Down - so reverse sign
      //
      rotatePIDDisable = SmartDashboard.getBoolean("Arm Rot PID Enabled", rotatePIDDisable);
      if (rotatePIDDisable) {
         double armVal = (-operatorStick.getRawAxis(5) * 6); // 0-12v in voltage mode
         SmartDashboard.putNumber("Arm Rot arvVal", armVal);
         armRotatePID.setReference(armVal, ControlType.kVoltage);
         armRotatePID.setP(1.0);
      } else {
         rotateTarget = SmartDashboard.getNumber("Arm Rot Set Target", 0.0);
         // armRotatePID.setReference(rotateTarget, ControlType.kPosition);
         armRotatePID.setReference(rotateTarget, ControlType.kPosition);
      }
      SmartDashboard.putNumber("Arm Rot Feedback", armRotateEncoder.getPosition());

      if (operatorStick.getRawButton(4)) {
         if (!highArmRotateButtonPressed) {
            logger.debug("set arm rotate PID to high {}", highArmSetPoint);
            if (!rotatePIDDisable) {
               armRotatePID.setReference(highArmSetPoint, ControlType.kPosition);
            }
            highArmRotateButtonPressed = true;
         }
      } else if (operatorStick.getRawButton(2)) {
         if (!midArmRotateButtonPressed) {
            logger.debug("set arm rotate PID to mid {}", midArmSetPoint);
            if (!rotatePIDDisable) {
               armRotatePID.setReference(midArmSetPoint, ControlType.kPosition);
            }
            midArmRotateButtonPressed = true;
         }
      } else if (operatorStick.getRawButton(1)) {
         if (!lowArmRotateButtonPressed) {
            logger.debug("set arm rotate PID to low {}", lowArmSetPoint);
            if (!rotatePIDDisable) {
               armRotatePID.setReference(lowArmSetPoint, ControlType.kPosition);
            }
            lowArmRotateButtonPressed = true;
         }
      }
      highArmRotateButtonPressed = operatorStick.getRawButton(4);
      midArmRotateButtonPressed = operatorStick.getRawButton(2);
      lowArmRotateButtonPressed = operatorStick.getRawButton(1);

      // -****************************************************************
      // -*
      // -* ARM EXTENSION
      // -*
      // -****************************************************************

      // FWD: Out, BCK: In - no need to reverse sign
      // armExtend.set(operatorStick.getRawAxis(1) * 0.20);
      // SmartDashboard.putNumber("Arm Ext Feedback", armExtendEncoder.getPosition());
      extendPIDDisable = SmartDashboard.getBoolean("Arm Ext PID Enabled", extendPIDDisable);
      if (extendPIDDisable) {
         double armVal = (-operatorStick.getRawAxis(1) * 6); // 0-12v in voltage mode
         SmartDashboard.putNumber("Arm Ext arvVal", armVal);
         armExtendPID.setReference(armVal, ControlType.kVoltage);
         armExtendPID.setP(1.0);
      } else {
         extendTarget = SmartDashboard.getNumber("Arm Ext Set Target", 0.0);
         // armExtendPID.setReference(extendTarget, ControlType.kPosition);
         armExtendPID.setReference(extendTarget, ControlType.kPosition);
      }
      SmartDashboard.putNumber("Arm Ext Feedback", armExtendEncoder.getPosition());

      // -****************************************************************
      // -*
      // -* GRIPPER INGEST
      // -*
      // -****************************************************************

      // double inSpeed = operatorStick.getRawAxis(2);
      // double outSpeed = operatorStick.getRawAxis(3);
      // double speed = 0;
      // if (inSpeed > outSpeed) {
      // speed = inSpeed;
      // } else {
      // speed = -outSpeed;
      // }
      // speed *= 0.30;
      // leftIngest.set(TalonFXControlMode.PercentOutput, speed);
      // rightIngest.set(TalonFXControlMode.PercentOutput, -speed);

      // -****************************************************************
      // -*
      // -* GRIPPER CLAW
      // -*
      // -****************************************************************

      if (operatorStick.getRawButton(6)) {
         if (!gripperOpen) {
            logger.info("button 6 pressed - open gripper");
            gripperSolenoid.set(true);
            gripperOpen = true;
         }
      } else if (!operatorStick.getRawButton(6) && gripperOpen) {
         logger.info("button 6 released - close gripper");
         gripperSolenoid.set(false);
         gripperOpen = false;
      }
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

      gripperOpen = false;
   }

   /** This function is called periodically during test mode. */
   @Override
   public void testPeriodic() {
      /*
       * Gripper Ingest Testing
       * 
       * // double inSpeed = operatorStick.getRawAxis(2);
       * // double outSpeed = operatorStick.getRawAxis(3);
       * // double speed = 0;
       * // if (inSpeed > outSpeed) {
       * // speed = inSpeed;
       * // }
       * // else {
       * // speed = -outSpeed;
       * // }
       * // speed *= 0.30;
       * // leftIngest.set(TalonFXControlMode.PercentOutput, speed);
       * // rightIngest.set(TalonFXControlMode.PercentOutput, -speed);
       */

      /*
       * Gripper Pneumatics Testing
       * if (operatorStick.getRawButton(3)) {
       * if (!gripperOpen) {
       * logger.info("button 3 pressed - open gripper");
       * gripperSolenoid.set(true);
       * gripperOpen = true;
       * }
       * } else if (!operatorStick.getRawButton(3) && gripperOpen) {
       * logger.info("button 3 released - close gripper");
       * gripperSolenoid.set(false);
       * gripperOpen = false;
       * }
       */
   }

   @Override
   public void testExit() {
   }

}
