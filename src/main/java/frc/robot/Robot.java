// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

   @SuppressWarnings("unused")
   private RobotContainer m_robotContainer;

   @SuppressWarnings("unused")
   private PowerDistribution powerD;

   private PneumaticHub pneumH;

   /*
    * Driver Stuff
    */
   private Joystick driverStick;

   private boolean driveBrakeButtonPressed;

   private boolean driveBrakeEnabled;

   private CANSparkMax leftFront;
   private CANSparkMax leftRear;
   private CANSparkMax rightFront;
   private CANSparkMax rightRear;

   private final double k_driveRampRate = 1.5;
   private double driveRampRate;

   private DifferentialDrive drive;

   /*
    * Operator Stuff
    */
   private Joystick operatorStick;

   private boolean armHighRotateButtonPressed;
   private boolean armMidRotateButtonPressed;
   private boolean armLowRotateButtonPressed;

   private final double k_armHighSetPoint = 26;
   private final double k_armMidSetPoint = 17;
   private final double k_armLowSetPoint = 7;
   private double armHighSetPoint;
   private double armMidSetPoint;
   private double armLowSetPoint;

   private final double k_rotateP = 0.2;
   private final double k_rotateI = 1e-5;
   private final double k_rotateD = 1;
   private final double k_rotateIzone = 0;
   private final double k_rotateFF = 0;
   private final double k_rotateMinOutput = -0.1;
   private final double k_rotateMaxOutput = 0.3;
   private double rotCurrentRef = 0.0;

   private boolean rotatePIDDisable;
   private CANSparkMax armRotate;
   private SparkMaxPIDController armRotatePID;
   private RelativeEncoder armRotateEncoder;
   private double rotateP, rotateI, rotateD, rotateIzone, rotateFF, rotateMaxOutput, rotateMinOutput;
   private double rotateTarget;

   private final double k_extendP = 0.2;
   private final double k_extendI = 0;
   private final double k_extendD = 1;
   private final double k_extendIzone = 0;
   private final double k_extendFF = 0;
   private final double k_extendMinOutput = -0.2;
   private final double k_extendMaxOutput = 0.2;

   private boolean extendPIDDisable;
   private CANSparkMax armExtend;
   private SparkMaxPIDController armExtendPID;
   // private AbsoluteEncoder armExtendEncoder;
   private RelativeEncoder armExtendEncoder;
   private double extendP, extendI, extendD, extendIzone, extendFF, extendMaxOutput, extendMinOutput;
   private double extendTarget;

   @SuppressWarnings("unused")
   private TalonFX leftIngest;
   @SuppressWarnings("unused")
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
      checkError(leftRear.restoreFactoryDefaults(), "LR restore factory defaults {}");
      rightFront = new CANSparkMax(13, MotorType.kBrushless);
      checkError(rightFront.restoreFactoryDefaults(), "RF restore factory defaults {}");
      rightRear = new CANSparkMax(14, MotorType.kBrushless);
      checkError(rightRear.restoreFactoryDefaults(), "RR restore factory defaults {}");

      // Following mode (Rear follows Front)
      checkError(leftRear.follow(leftFront), "L setting following mode {}");

      // Inverted (Right from Left) and Following mode (Rear follows Front)
      rightFront.setInverted(true);
      checkError(rightRear.follow(rightFront), "R setting following mode {}");

      // Brake mode for now (should only be in balance)
      driveBrakeEnabled = false;
      SmartDashboard.putBoolean("driveBrakeEnabled", driveBrakeEnabled);
      checkError(leftFront.setIdleMode(IdleMode.kCoast), "LF set idle mode to coast {}");
      checkError(leftRear.setIdleMode(IdleMode.kCoast), "LR set idle mode to coast {}");
      checkError(rightFront.setIdleMode(IdleMode.kCoast), "RF set idle mode to coast {}");
      checkError(rightRear.setIdleMode(IdleMode.kCoast), "RR set idle mode to coast {}");

      // Ramp rate
      // initialize ramp rate
      if (!Preferences.containsKey("Drive.rampRate")) {
         Preferences.setDouble("Drive.rampRate", k_driveRampRate);
      }
      driveRampRate = Preferences.getDouble("Drive.rampRate", k_driveRampRate);
      checkError(leftFront.setOpenLoopRampRate(driveRampRate), "L setting ramp rate {}");
      checkError(rightFront.setOpenLoopRampRate(driveRampRate), "R setting ramp rate {}");

      drive = new DifferentialDrive(leftFront, rightFront);

      operatorStick = new Joystick(1);

      /****************************************************************************
       * Arm Rotate Setup
       *******************************************************************/

      armRotate = new CANSparkMax(21, MotorType.kBrushless);
      checkError(armRotate.restoreFactoryDefaults(), "AR restore factory defaults {}");
      checkError(armRotate.setIdleMode(IdleMode.kBrake), "AR set idle mode to brake {}");
      armRotatePID = armRotate.getPIDController();
      armRotateEncoder = armRotate.getEncoder();
      armRotateEncoder.setPosition(0);

      rotatePIDDisable = false;
      // PID coefficients
      rotateP = k_rotateP;
      rotateI = k_rotateI;
      rotateD = k_rotateD;
      rotateIzone = k_rotateIzone;
      rotateFF = k_rotateFF;
      rotateMinOutput = k_rotateMinOutput;
      rotateMaxOutput = k_rotateMaxOutput;

      if (rotatePIDDisable) {
         armRotatePID.setReference(0.0, ControlType.kVoltage);
      } else {
         armRotatePID.setReference(0.0, ControlType.kPosition);
      }
      // set PID coefficients
      armRotatePID.setP(rotateP);
      armRotatePID.setI(rotateI);
      armRotatePID.setD(rotateD);
      armRotatePID.setIZone(rotateIzone);
      armRotatePID.setFF(rotateFF);
      armRotatePID.setOutputRange(rotateMinOutput, rotateMaxOutput);

      // display PID coefficients on SmartDashboard
      SmartDashboard.putBoolean("Arm Rot PID Enabled", rotatePIDDisable);
      SmartDashboard.putNumber("Arm Rot P Gain", rotateP);
      SmartDashboard.putNumber("Arm Rot I Gain", rotateI);
      SmartDashboard.putNumber("Arm Rot D Gain", rotateD);
      SmartDashboard.putNumber("Arm Rot I Zone", rotateIzone);
      SmartDashboard.putNumber("Arm Rot Feed Forward", rotateFF);
      SmartDashboard.putNumber("Arm Rot Min Output", rotateMinOutput);
      SmartDashboard.putNumber("Arm Rot Max Output", rotateMaxOutput);
      SmartDashboard.putNumber("Arm Rot Set Target", 0);
      SmartDashboard.putNumber("Arm Rot Feedback", armRotateEncoder.getPosition());

      // initialize set points
      if (!Preferences.containsKey("armRotate.high")) {
         Preferences.setDouble("armRotate.high", k_armHighSetPoint);
      }
      armHighSetPoint = Preferences.getDouble("armRotate.high", k_armHighSetPoint);
      if (!Preferences.containsKey("armRotate.mid")) {
         Preferences.setDouble("armRotate.mid", k_armMidSetPoint);
      }
      armMidSetPoint = Preferences.getDouble("armRotate.mid", k_armMidSetPoint);
      if (!Preferences.containsKey("armRotate.low")) {
         Preferences.setDouble("armRotate.low", k_armLowSetPoint);
      }
      armLowSetPoint = Preferences.getDouble("armRotate.low", k_armLowSetPoint);

      // /****************************************************************************
      // * Arm Extend Setup
      // *******************************************************************/

      armExtend = new CANSparkMax(22, MotorType.kBrushless);
      checkError(armExtend.restoreFactoryDefaults(), "AE restore factory defaults {}");
      checkError(armExtend.setIdleMode(IdleMode.kBrake), "AE set idle mode to break {}");
      armExtendPID = armExtend.getPIDController();
      armExtendEncoder = armExtend.getEncoder();
      armExtendEncoder.setPosition(0);

      extendPIDDisable = true;
      // PID coefficients Extend
      extendP = k_extendP;
      extendI = k_extendI;
      extendD = k_extendD;
      extendIzone = k_extendIzone;
      extendFF = k_extendFF;
      extendMinOutput = k_extendMinOutput;
      extendMaxOutput = k_extendMaxOutput;

      if (extendPIDDisable) {
         armExtendPID.setReference(0.0, ControlType.kVoltage);
      } else {
         armExtendPID.setReference(0.0, ControlType.kPosition);
      }
      // set PID coefficients
      armExtendPID.setP(extendP);
      armExtendPID.setI(extendI);
      armExtendPID.setD(extendD);
      armExtendPID.setIZone(extendIzone);
      armExtendPID.setFF(extendFF);
      armExtendPID.setOutputRange(extendMinOutput, extendMaxOutput);

      // display PID coefficients on SmartDashboard
      SmartDashboard.putBoolean("Arm Ext PID Enabled", extendPIDDisable);
      SmartDashboard.putNumber("Arm Ext P Gain", extendP);
      SmartDashboard.putNumber("Arm Ext I Gain", extendI);
      SmartDashboard.putNumber("Arm Ext D Gain", extendD);
      SmartDashboard.putNumber("Arm Ext I Zone", extendIzone);
      SmartDashboard.putNumber("Arm Ext Feed Forward", extendFF);
      SmartDashboard.putNumber("Arm Ext Min Output", extendMinOutput);
      SmartDashboard.putNumber("Arm Ext Max Output", extendMaxOutput);
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

      // Read amp rate from SmartDashboard
      driveRampRate = Preferences.getDouble("Drive.rampRate", k_driveRampRate);
      checkError(leftFront.setOpenLoopRampRate(driveRampRate), "L setting ramp rate {}");
      checkError(rightFront.setOpenLoopRampRate(driveRampRate), "R setting ramp rate {}");

      // read PID coefficients from SmartDashboard
      rotatePIDDisable = SmartDashboard.getBoolean("Arm Rot PID Enabled", rotatePIDDisable);
      double ar_p = SmartDashboard.getNumber("Arm Rot P Gain", 0);
      double ar_i = SmartDashboard.getNumber("Arm Rot I Gain", 0);
      double ar_d = SmartDashboard.getNumber("Arm Rot D Gain", 0);
      double ar_iz = SmartDashboard.getNumber("Arm Rot I Zone", 0);
      double ar_ff = SmartDashboard.getNumber("Arm Rot Feed Forward", 0);
      double ar_min = SmartDashboard.getNumber("Arm Rot Min Output", 0);
      double ar_max = SmartDashboard.getNumber("Arm Rot Max Output", 0);
      rotateTarget = SmartDashboard.getNumber("Arm Rot Set Target", rotateTarget);

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
      if ((ar_min != rotateMinOutput) || (ar_max != rotateMaxOutput)) {
         armRotatePID.setOutputRange(ar_min, ar_max);
         rotateMinOutput = ar_min;
         rotateMaxOutput = ar_max;
      }

      // read set points from SmartDashboard
      armHighSetPoint = Preferences.getDouble("armRotate.high", k_armHighSetPoint);
      armMidSetPoint = Preferences.getDouble("armRotate.mid", k_armMidSetPoint);
      armLowSetPoint = Preferences.getDouble("armRotate.low", k_armLowSetPoint);

      // read PID coefficients from SmartDashboard
      extendPIDDisable = SmartDashboard.getBoolean("Arm Ext PID Enabled", extendPIDDisable);
      ar_p = SmartDashboard.getNumber("Arm Ext P Gain", 0);
      ar_i = SmartDashboard.getNumber("Arm Ext I Gain", 0);
      ar_d = SmartDashboard.getNumber("Arm Ext D Gain", 0);
      ar_iz = SmartDashboard.getNumber("Arm Ext I Zone", 0);
      ar_ff = SmartDashboard.getNumber("Arm Ext Feed Forward", 0);
      ar_min = SmartDashboard.getNumber("Arm Ext Min Output", 0);
      ar_max = SmartDashboard.getNumber("Arm Ext Max Output", 0);
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
      if ((ar_min != extendMinOutput) || (ar_max != extendMaxOutput)) {
         armExtendPID.setOutputRange(ar_min, ar_max);
         extendMinOutput = ar_min;
         extendMaxOutput = ar_max;
      }
   }

   @Override
   public void disabledExit() {
   }

   // private long k_autoDelay = Math.fl(1.0 / 0.20); // sec / 20 msec

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
      SmartDashboard.putNumber("NavX Yaw", 0.0);
      SmartDashboard.putNumber("NavX Pitch", 0.0);
      SmartDashboard.putNumber("NavX Rotate", 0.0);
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

      driveBrakeButtonPressed = false;
      driveBrakeEnabled = false;
      SmartDashboard.putBoolean("driveBrakeEnabled", driveBrakeEnabled);
      checkError(leftFront.setIdleMode(IdleMode.kCoast), "LF set idle mode to coast {}");
      checkError(leftRear.setIdleMode(IdleMode.kCoast), "LR set idle mode to coast {}");
      checkError(rightFront.setIdleMode(IdleMode.kCoast), "RF set idle mode to coast {}");
      checkError(rightRear.setIdleMode(IdleMode.kCoast), "RR set idle mode to coast {}");

      armHighRotateButtonPressed = false;
      armMidRotateButtonPressed = false;
      armLowRotateButtonPressed = false;

      gripperOpen = false;
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

      drive.arcadeDrive(-driverStick.getRawAxis(1),
            -driverStick.getRawAxis(4));

      // drive.curvatureDrive(-driverStick.getRawAxis(1) * .60,
      // -driverStick.getRawAxis(4) * 0.60, false);

      // drive.curvatureDrive(-driverStick.getRawAxis(1) * .60,
      // -driverStick.getRawAxis(4) * 0.60, driverStick.getRawButton(6));

      if (driverStick.getRawButton(8)) {
         if (!driveBrakeButtonPressed) {
            logger.debug("toggle drive brake mode to {}", !driveBrakeEnabled);
            driveBrakeEnabled = !driveBrakeEnabled;
            SmartDashboard.putBoolean("driveBrakeEnabled", driveBrakeEnabled);
            if (driveBrakeEnabled) {
               checkError(leftFront.setIdleMode(IdleMode.kBrake), "LF set idle mode to brake {}");
               checkError(leftRear.setIdleMode(IdleMode.kBrake), "LR set idle mode to brake {}");
               checkError(rightFront.setIdleMode(IdleMode.kBrake), "RF set idle mode to brake {}");
               checkError(rightRear.setIdleMode(IdleMode.kBrake), "RR set idle mode to brake {}");
            } else {
               checkError(leftFront.setIdleMode(IdleMode.kCoast), "LF set idle mode to coast {}");
               checkError(leftRear.setIdleMode(IdleMode.kCoast), "LR set idle mode to coast {}");
               checkError(rightFront.setIdleMode(IdleMode.kCoast), "RF set idle mode to coast {}");
               checkError(rightRear.setIdleMode(IdleMode.kCoast), "RR set idle mode to coast {}");
            }
            driveBrakeButtonPressed = true;
         }
      }
      driveBrakeButtonPressed = driverStick.getRawButton(8);

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
         // rotateTarget = SmartDashboard.getNumber("Arm Rot Set Target", 0.0);
         // // armRotatePID.setReference(rotateTarget, ControlType.kPosition);
         // armRotatePID.setReference(rotateTarget, ControlType.kPosition);

         //Override Posistion
         if (  Math.abs(operatorStick.getRawAxis(5 )) > .01 ) 
            {  
               rotateTarget += operatorStick.getRawAxis(5) * .001; //Offset
               //armRotatePID.setReference(rotateTarget, ControlType.kPosition); //update pid

            }  

         if (operatorStick.getRawButton(4)) {
            if (!armHighRotateButtonPressed) {
               rotateTarget = armHighSetPoint;
               logger.debug("set arm rotate PID to high {}", rotateTarget);
               if (!rotatePIDDisable) {

                  armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               }
               armHighRotateButtonPressed = true;
            }
         } else if (operatorStick.getRawButton(2)) {
            if (!armMidRotateButtonPressed) {
               rotateTarget = armMidSetPoint;
               logger.debug("set arm rotate PID to mid {}", rotateTarget);
               if (!rotatePIDDisable) {
                  armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               }
               armMidRotateButtonPressed = true;
            }
         } else if (operatorStick.getRawButton(1)) {
            if (!armLowRotateButtonPressed) {
               rotateTarget = armLowSetPoint;
               logger.debug("set arm rotate PID to low {}", rotateTarget);
               if (!rotatePIDDisable) {
                  armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               }
               armLowRotateButtonPressed = true;
            }
         }
         SmartDashboard.putNumber("Arm Rot Set Target", rotateTarget);

         armHighRotateButtonPressed = operatorStick.getRawButton(4);
         armMidRotateButtonPressed = operatorStick.getRawButton(2);
         armLowRotateButtonPressed = operatorStick.getRawButton(1);
      }
      SmartDashboard.putNumber("Arm Rot Feedback", armRotateEncoder.getPosition());

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
      SmartDashboard.getNumber("Arm Rot Set Target", rotateTarget);

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
            // logger.info("button 6 pressed - open gripper");
            gripperSolenoid.set(true);
            gripperOpen = true;
         }
      } else if (!operatorStick.getRawButton(6) && gripperOpen) {
         // logger.info("button 6 released - close gripper");
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
