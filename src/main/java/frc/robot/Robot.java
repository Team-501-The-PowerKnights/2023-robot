// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.ErrorCode;
// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
// import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.kauailabs.navx.frc.AHRS;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

   @SuppressWarnings("unused")
   private RobotContainer m_robotContainer;

   // Flag for having completed autonomous part of match
   private static boolean autonomousComplete;
   // Flag for having completed teleop part of match
   private static boolean teleopComplete;

   @SuppressWarnings("unused")
   private PowerDistribution powerD;

   private AHRS ahrs;
   boolean ahrsValid;

   //
   private enum AutoSelection {
      // @formatter:off
      doNothing("doNothing"), 
      doConeAndBackup("doConeAndBackup"),
      doFull("doFull");
      // @formatter:on

      private final String name;

      private AutoSelection(String name) {
         this.name = name;
      }

      public String getName() {
         return name;
      }
   }

   // Chooser for autonomous command from Dashboard
   private SendableChooser<AutoSelection> autoChooser;
   // Command that was selected
   private AutoSelection autoSelected;

   /*- *************************************
    *- Driver Stuff
    *- *************************************/
   private Joystick driverStick;

   private boolean driveBrakeButtonPressed;

   private boolean driveBrakeEnabled;

   private CANSparkMax leftFront;
   private CANSparkMax leftRear;
   private CANSparkMax rightFront;
   private CANSparkMax rightRear;

   private final double k_driveRampRate = 0.75;
   private double driveRampRate;

   private DifferentialDrive drive;

   /*- *************************************
    *- Operator Stuff
    *- *************************************/
   private Joystick operatorStick;

   // Arm Rotation
   private boolean armOverRotateButtonPressed;
   private boolean armHighRotateButtonPressed;
   private boolean armMidRotateButtonPressed;
   private boolean armLowRotateButtonPressed;

   private final double k_armRotateOverSetPoint = 60;
   private final double k_armRotateHighSetPoint = 26;
   private final double k_armRotateMidSetPoint = 17;
   private final double k_armRotateLowSetPoint = 7;
   private double armRotateOverSetPoint;
   private double armRotateHighSetPoint;
   private double armRotateMidSetPoint;
   private double armRotateLowSetPoint;

   private final double k_rotateP = 0.2;
   private final double k_rotateI = 1e-5;
   private final double k_rotateD = 1;
   private final double k_rotateIzone = 0;
   private final double k_rotateFF = 0;
   private final double k_rotateMinOutput = -0.2;
   private final double k_rotateMaxOutput = 0.5;
   private final double k_rotateRampRate = 0.5;

   private boolean rotatePIDDisable;
   private CANSparkMax armRotate;
   private SparkMaxPIDController armRotatePID;
   private RelativeEncoder armRotateEncoder;
   private double rotateP, rotateI, rotateD, rotateIzone, rotateFF, rotateMaxOutput, rotateMinOutput;
   private double rotateTarget;

   // Arm Extension
   private final double k_extendP = 0.2;
   private final double k_extendI = 0;
   private final double k_extendD = 1;
   private final double k_extendIzone = 0;
   private final double k_extendFF = 0;
   private final double k_extendMinOutput = -1.0; // -0.5;
   private final double k_extendMaxOutput = 1.0; // 0.7;

   private boolean extendPIDDisable;
   private CANSparkMax armExtend;
   private SparkMaxPIDController armExtendPID;
   private RelativeEncoder armExtendEncoder;
   private double extendP, extendI, extendD, extendIzone, extendFF, extendMaxOutput, extendMinOutput;
   private double extendTarget;

   private final float k_extendMinSoftLimit = 5f;
   private final float k_extendMaxSoftLimit = 160f;

   private final double k_armExtendHighSetPoint = 160;
   private final double k_armExtendMidSetPoint = 85;
   private final double k_armExtendLowSetPoint = 50;
   private final double k_armExtendInSetPoint = 5;
   private double armExtendHighSetPoint;
   private double armExtendMidSetPoint;
   private double armExtendLowSetPoint;

   // Gripper Ingest
   // private TalonFX leftIngest;
   // private TalonFX rightIngest;
   private CANSparkMax leftIngest;
   private CANSparkMax rightIngest;
   private double k_gripperMaxInSpeed = 1.0; // 0.25;
   private double k_gripperMaxOutSpeed = 1.0; // 0.17;
   private double k_gripperIdleSpeed = 0.00; // disable

   /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
   @Override
   public void robotInit() {
      // Instantiate our RobotContainer. This will perform all our button bindings,
      // and put our autonomous chooser on the dashboard.
      m_robotContainer = new RobotContainer();

      autonomousComplete = false;
      teleopComplete = false;

      // Create the chooser for autonomous command
      createAutoChooser();

      powerD = new PowerDistribution(1, ModuleType.kRev);

      // Instantiate
      try {
         ahrs = new AHRS(SPI.Port.kMXP);
         ahrsValid = waitForAhrsConnection();
      } catch (final RuntimeException ex) {
         logger.error("Instantiating naxX MXP" + ex.getMessage());
         // SmartDashboard.reportError("Error instantiating navX MXP: " +
         // ex.getMessage(), true);

         ahrs = null;
         ahrsValid = false;
      }
      SmartDashboard.putBoolean("ahrsValid", ahrsValid);
      if (ahrsValid) {
         configureAHRS();
      }

      gyroTlmCount = 0;

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

      /*******************************************************************
       * Arm Rotate Setup
       *******************************************************************/

      armRotate = new CANSparkMax(21, MotorType.kBrushless);
      checkError(armRotate.restoreFactoryDefaults(), "AR restore factory defaults {}");
      checkError(armRotate.setIdleMode(IdleMode.kBrake), "AR set idle mode to brake {}");
      armRotatePID = armRotate.getPIDController();
      armRotateEncoder = armRotate.getEncoder();
      armRotateEncoder.setPosition(0);
      armRotate.setOpenLoopRampRate(k_rotateRampRate);

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
         rotateTarget = 0;
         SmartDashboard.putNumber("Arm Rot Set Target", rotateTarget);
         armRotatePID.setReference(rotateTarget, ControlType.kPosition);
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
      if (!Preferences.containsKey("armRotate.over")) {
         Preferences.setDouble("armRotate.over", k_armRotateOverSetPoint);
      }
      armRotateOverSetPoint = Preferences.getDouble("armRotate.over", k_armRotateOverSetPoint);
      if (!Preferences.containsKey("armRotate.high")) {
         Preferences.setDouble("armRotate.high", k_armRotateHighSetPoint);
      }
      armRotateHighSetPoint = Preferences.getDouble("armRotate.high", k_armRotateHighSetPoint);
      if (!Preferences.containsKey("armRotate.mid")) {
         Preferences.setDouble("armRotate.mid", k_armRotateMidSetPoint);
      }
      armRotateMidSetPoint = Preferences.getDouble("armRotate.mid", k_armRotateMidSetPoint);
      if (!Preferences.containsKey("armRotate.low")) {
         Preferences.setDouble("armRotate.low", k_armRotateLowSetPoint);
      }
      armRotateLowSetPoint = Preferences.getDouble("armRotate.low", k_armRotateLowSetPoint);

      /*******************************************************************
       * Arm Extend Setup
       *******************************************************************/

      armExtend = new CANSparkMax(22, MotorType.kBrushless);
      checkError(armExtend.restoreFactoryDefaults(), "AE restore factory defaults {}");
      checkError(armExtend.setIdleMode(IdleMode.kBrake), "AE set idle mode to brake {}");
      armExtendPID = armExtend.getPIDController();
      armExtendEncoder = armExtend.getEncoder();
      checkError(armExtend.setSoftLimit(SoftLimitDirection.kReverse, k_extendMinSoftLimit),
            "AE set min software limit {}");
      checkError(armExtend.setSoftLimit(SoftLimitDirection.kForward, k_extendMaxSoftLimit),
            "AE set max soft limit {}");
      armExtend.enableSoftLimit(SoftLimitDirection.kReverse, true);
      armExtend.enableSoftLimit(SoftLimitDirection.kForward, true);

      extendPIDDisable = false;
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

      // initialize set points
      if (!Preferences.containsKey("armExtend.high")) {
         Preferences.setDouble("armExtend.high", k_armExtendHighSetPoint);
      }
      armExtendHighSetPoint = Preferences.getDouble("armExtend.high", k_armExtendHighSetPoint);
      if (!Preferences.containsKey("armExtend.mid")) {
         Preferences.setDouble("armExtend.mid", k_armExtendMidSetPoint);
      }
      armExtendMidSetPoint = Preferences.getDouble("armExtend.mid", k_armExtendMidSetPoint);
      if (!Preferences.containsKey("armExtend.low")) {
         Preferences.setDouble("armExtend.low", k_armExtendLowSetPoint);
      }
      armExtendLowSetPoint = Preferences.getDouble("armExtend.low", k_armExtendLowSetPoint);

      /*******************************************************************
       * Gripper Ingest Setup
       *******************************************************************/

      // leftIngest = new TalonFX(31);
      // checkError(leftIngest.configFactoryDefault(), "LI restore factory defaults
      // {}");
      // leftIngest.setNeutralMode(NeutralMode.Brake);
      // rightIngest = new TalonFX(32);
      // checkError(leftIngest.configFactoryDefault(), "RI restore factory defaults
      // {}");
      // rightIngest.setNeutralMode(NeutralMode.Brake);
      //
      // rightIngest.setInverted(true);
      // rightIngest.follow(leftIngest, true);

      leftIngest = new CANSparkMax(31, MotorType.kBrushed);
      checkError(leftIngest.restoreFactoryDefaults(), "LI restore factory defaults {}");
      checkError(leftIngest.setIdleMode(IdleMode.kCoast), "LI set idle mode to coast {}");
      rightIngest = new CANSparkMax(32, MotorType.kBrushed);
      checkError(rightIngest.restoreFactoryDefaults(), "RI restore factory defaults {}");
      checkError(rightIngest.setIdleMode(IdleMode.kCoast), "RI set idle mode to coast {}");

      checkError(rightIngest.follow(leftIngest, true), "RI set follow and invert to true {}");

      if (!Preferences.containsKey("Gripper.maxInSpeed")) {
         Preferences.setDouble("Gripper.maxInSpeed", k_gripperMaxInSpeed);
      }
      if (!Preferences.containsKey("Gripper.maxOutSpeed")) {
         Preferences.setDouble("Gripper.maxOutSpeed", k_gripperMaxOutSpeed);
      }
   }

   protected boolean waitForAhrsConnection() {
      long count = 0;
      while (!ahrs.isConnected()) {
         logger.debug("waiting ... ahrs not connected");
         try {
            Thread.sleep(50);
         } catch (final InterruptedException ex) {
            // Don't care
         }
         if (++count > 10) {
            logger.warn("connect - failed to finish count={}", count);
            break;
         }
      }
      final boolean value = ahrs.isConnected();
      if (value) {
         logger.debug("ahrs connected");
      }
      return value;
   }

   protected void configureAHRS() {
      zeroYaw();
   }

   private void zeroYaw() {
      logger.debug("yaw={}", ahrs.getYaw());
      long count = 0;
      while (Math.abs(ahrs.getYaw()) > 1.0) {
         ahrs.zeroYaw();
         try {
            logger.debug("zeroYaw::waiting ... yaw={}", ahrs.getYaw());
            Thread.sleep(50);
         } catch (final InterruptedException ex) {
            // Don't care
         }
         if (++count > 10) {
            logger.warn("zeroYaw - failed to finish count={}", count);
            break;
         }
      }
      logger.debug("zeroYaw::done ... yaw={}", ahrs.getYaw());
      logger.trace("zeroYaw::done ... angle={}", ahrs.getAngle());
   }

   // last error (not the same as kOk)
   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private REVLibError lastREVError;

   private void checkError(REVLibError error, String message) {
      if (error != REVLibError.kOk) {
         lastREVError = error;
         logger.error(message, error);
      }
   }

   // last error (not the same as kOk)
   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private ErrorCode lastCTREError;

   @SuppressWarnings("unused")
   private void checkError(ErrorCode error, String message) {
      if (error != ErrorCode.OK) {
         lastCTREError = error;
         logger.error(message, error);
      }
   }

   private void createAutoChooser() {
      autoChooser = new SendableChooser<>();

      // Default option is safety of "do nothing"
      autoChooser.setDefaultOption("Do Nothing", AutoSelection.doNothing);

      //
      autoChooser.addOption("Not Full Auto (place cone & backup)", AutoSelection.doConeAndBackup);
      //
      autoChooser.addOption("Full Auto (place cone & balance)", AutoSelection.doFull);

      SmartDashboard.putData("Auto Mode", autoChooser);
   }

   private long gyroTlmCount;

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

      SmartDashboard.putNumber("Arm Rot Feedback", armRotateEncoder.getPosition());
      SmartDashboard.putNumber("Arm Ext Feedback", armExtendEncoder.getPosition());

      gyroTlmCount++;
      if (gyroTlmCount > 25) {
         SmartDashboard.putNumber("Gyro roll", ahrs.getRoll());
         SmartDashboard.putNumber("Gyro pitch", ahrs.getPitch());
         // SmartDashboard.putNumber("Gyro yaw", ahrs.getYaw());
         gyroTlmCount = 0;
      }
   }

   /** This function is called once each time the robot enters DisSabled mode. */
   @Override
   public void disabledInit() {

      drive.arcadeDrive(0, 0);

      armRotate.set(0);
      armExtend.set(0);

      // leftIngest.set(TalonFXControlMode.PercentOutput, 0);
      leftIngest.set(0);
   }

   @Override
   public void disabledPeriodic() {

      // Has a "real" auto been selected yet?
      SmartDashboard.putBoolean("Real Auto?", (autoChooser.getSelected() != AutoSelection.doNothing));

      /**
       * Drive
       **/

      // Read amp rate from SmartDashboard
      driveRampRate = Preferences.getDouble("Drive.rampRate", k_driveRampRate);
      checkError(leftFront.setOpenLoopRampRate(driveRampRate), "L setting ramp rate {}");
      checkError(rightFront.setOpenLoopRampRate(driveRampRate), "R setting ramp rate {}");

      /**
       * Arm Rotate
       **/

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

      // if PID coefficients on SmartDashboard have changed, update controller
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
      armRotateOverSetPoint = Preferences.getDouble("armRotate.over", k_armRotateOverSetPoint);
      armRotateHighSetPoint = Preferences.getDouble("armRotate.high", k_armRotateHighSetPoint);
      armRotateMidSetPoint = Preferences.getDouble("armRotate.mid", k_armRotateMidSetPoint);
      armRotateLowSetPoint = Preferences.getDouble("armRotate.low", k_armRotateLowSetPoint);

      /**
       * Arm Extend
       **/

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

      // if PID coefficients on SmartDashboard have changed, update controller
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

      // read set points from SmartDashboard
      armExtendHighSetPoint = Preferences.getDouble("armExtend.high", k_armExtendHighSetPoint);
      armExtendMidSetPoint = Preferences.getDouble("armExtend.mid", k_armExtendMidSetPoint);
      armExtendLowSetPoint = Preferences.getDouble("armExtend.low", k_armExtendLowSetPoint);

      /**
       * Gripper
       **/

      double v;
      v = Preferences.getDouble("Gripper.maxInSpeed", k_gripperMaxInSpeed);
      if (v != k_gripperMaxInSpeed) {
         k_gripperMaxInSpeed = v;
      }
      v = Preferences.getDouble("Gripper.maxOutSpeed", k_gripperMaxOutSpeed);
      if (v != k_gripperMaxOutSpeed) {
         k_gripperMaxOutSpeed = v;
      }
   }

   @Override
   public void disabledExit() {
      if (autonomousComplete && teleopComplete) {
         logger.info("EventName:     {}", DriverStation.getEventName());
         logger.info("MatchType:     {}", DriverStation.getMatchType());
         logger.info("MatchNumber:   {}", DriverStation.getMatchNumber());
         logger.info("ReplayNumber:  {}", DriverStation.getReplayNumber());
         logger.info("Alliance:      {}", DriverStation.getAlliance());
         logger.info("Location:      {}", DriverStation.getLocation());
      }
   }

   private enum AutoState {
      // @formatter:off
      start, 
      rotateArmHigh, 
      extendArmLong, 
      rotateArmMid, 
      ejectGripper, 
      retractArm, 
      stopGripper, 
      driveBackward, 
      driveBackwardOnToRamp, 
      driveBackwardOnToRampTimed,
      pause,
      balance, 
      end, 
      done;
      // @formatter:on
   }

   private AutoState autoState;
   private boolean autoStateStarted;

   private long autoCommandTimerCount;
   private long autoCommandTimerCountTarget;

   /**
    * This autonomous runs the autonomous command selected by your
    * {@link RobotContainer} class.
    */
   @Override
   public void autonomousInit() {
      driveBrakeEnabled = true;
      SmartDashboard.putBoolean("driveBrakeEnabled", driveBrakeEnabled);
      checkError(leftFront.setIdleMode(IdleMode.kBrake), "LF set idle mode to brake {}");
      checkError(leftRear.setIdleMode(IdleMode.kBrake), "LR set idle mode to brake {}");
      checkError(rightFront.setIdleMode(IdleMode.kBrake), "RF set idle mode to brake {}");
      checkError(rightRear.setIdleMode(IdleMode.kBrake), "RR set idle mode to brake {}");

      checkError(armRotate.setIdleMode(IdleMode.kBrake), "AR set idle mode to brake {}");
      checkError(armExtend.setIdleMode(IdleMode.kBrake), "AE set idle mode to brake {}");
      checkError(leftIngest.setIdleMode(IdleMode.kBrake), "LI set idle mode to brake {}");
      checkError(rightIngest.setIdleMode(IdleMode.kBrake), "RI set idle mode to brake {}");

      autoSelected = autoChooser.getSelected();
      if (autoSelected == null) {
         autoSelected = AutoSelection.doNothing;
      }
      logger.info("auto function is {}", autoSelected.getName());

      autoState = AutoState.start;
      autoStateStarted = false;
      SmartDashboard.putString("Auto state", autoState.name());

      autoCommandTimerCount = 0;
      autoCommandTimerCountTarget = 0;

      // FIXME: shouldn't have this disabled
      drive.setSafetyEnabled(false);
   }

   /** This function is called periodically during autonomous. */
   @Override
   public void autonomousPeriodic() {

      SmartDashboard.putString("Auto state", autoState.name());

      switch (autoSelected) {
         case doNothing:
            autoDoNothing();
            break;
         case doConeAndBackup:
            autoDoConeAndBackup();
            break;
         case doFull:
            autoDoFull();
            break;
         default:
            autoDoNothing();
            break;
      }
   }

   private void autoDoNothing() {
   }

   private void autoDoConeAndBackup() {
      switch (autoState) {
         case start:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = 0;
               autoCommandTimerCount = 0;
            }
            autoState = AutoState.rotateArmHigh;
            autoStateStarted = false;
            armRotateHigh();
            break;
         case rotateArmHigh:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (1.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.extendArmLong;
               autoStateStarted = false;
               armExtendLong();
            }
            break;
         case extendArmLong:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (5.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.rotateArmMid;
               autoStateStarted = false;
               armRotateMid();
            }
            break;
         case rotateArmMid:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (1.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.ejectGripper;
               autoStateStarted = false;
               gripperEject();
            }
            break;
         case ejectGripper:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (0.5 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.retractArm;
               autoStateStarted = false;
               armRetractShort();
            }
            break;
         case retractArm:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (3.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.stopGripper;
               autoStateStarted = false;
               gripperStop();
            }
            break;
         case stopGripper:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (0.10 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.driveBackward;
               autoStateStarted = false;
               driveBackward();
            }
            break;
         case driveBackward:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (3.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               drive.arcadeDrive(0, 0);
               autoState = AutoState.end;
               autoStateStarted = false;
            }
            break;
         case end:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               // Just in case ...
               drive.arcadeDrive(0, 0);
            }
            autoState = AutoState.done;
            autoStateStarted = false;
            autoCommandTimerCountTarget = 0;
            autoCommandTimerCount = 0;
            break;
         case done:
         default:
            break;
      }
   }

   private void autoDoFull() {
      switch (autoState) {
         case start:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = 0;
               autoCommandTimerCount = 0;
            }
            autoState = AutoState.rotateArmHigh;
            autoStateStarted = false;
            armRotateHigh();
            break;
         case rotateArmHigh:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (1.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.extendArmLong;
               autoStateStarted = false;
               armExtendLong();
            }
            break;
         case extendArmLong:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (5.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.rotateArmMid;
               autoStateStarted = false;
               armRotateMid();
            }
            break;
         case rotateArmMid:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (1.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.ejectGripper;
               autoStateStarted = false;
               gripperEject();
            }
            break;
         case ejectGripper:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (0.5 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.stopGripper;
               autoStateStarted = false;
               gripperStop();
            }
            break;
         case stopGripper:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (0.10 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.driveBackwardOnToRampTimed;
               autoStateStarted = false;
               driveBackwardOnToRamp();
            }
            break;
         case driveBackwardOnToRampTimed:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (2.3 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.pause;
               autoStateStarted = false;
               drive.arcadeDrive(0, 0);
            }
            break;
         case driveBackwardOnToRamp:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (2.3 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if ((++autoCommandTimerCount >= autoCommandTimerCountTarget) ||
                  (ahrs.getPitch() > 16.0)) {
               autoState = AutoState.pause;
               autoStateStarted = false;
               drive.arcadeDrive(0, 0);
            }
            break;
         case pause:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (0.10 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               autoState = AutoState.balance;
               autoStateStarted = false;
               // Done within mode (nothing to start)
            }
            break;
         case balance:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               autoCommandTimerCountTarget = (long) (3.0 / 0.020); // sec / 20 msec
               autoCommandTimerCount = 0;
            }
            // double pitch = ahrs.getPitch();
            // double speed = Math.max(Math.abs(pitch * 0.03), 0.3);
            // if (pitch > 0) {
            // drive.arcadeDrive(speed, 0);
            // } else if (pitch < 0) {
            // drive.arcadeDrive(-speed, 0);
            // } else {
            // drive.arcadeDrive(0, 0);
            // }
            if (++autoCommandTimerCount >= autoCommandTimerCountTarget) {
               drive.arcadeDrive(0, 0);
               autoState = AutoState.end;
               autoStateStarted = false;
            }
            break;
         case end:
            if (!autoStateStarted) {
               logger.info("state = {}", autoState);
               autoStateStarted = true;
               // Just in case ...
               drive.arcadeDrive(0, 0);
            }
            autoState = AutoState.done;
            autoStateStarted = false;
            autoCommandTimerCountTarget = 0;
            autoCommandTimerCount = 0;
            break;
         case done:
         default:
            break;
      }
   }

   private void armRotateHigh() {
      logger.info("starting command armRotateHigh");
      rotateTarget = armRotateHighSetPoint;
      logger.debug("set arm rotate PID to high {}", rotateTarget);
      armRotatePID.setReference(rotateTarget, ControlType.kPosition);
   }

   private void armExtendLong() {
      logger.info("starting command armExtendLong");
      extendTarget = +190;
      logger.debug("set arm extend PID to ad-hoc {}", extendTarget);
      armExtendPID.setReference(extendTarget, ControlType.kPosition);
   }

   private void armRotateMid() {
      logger.info("starting command armRotateMid");
      rotateTarget = armRotateMidSetPoint;
      logger.debug("set arm rotate PID to mid {}", rotateTarget);
      armRotatePID.setReference(rotateTarget, ControlType.kPosition);
   }

   private void gripperEject() {
      logger.info("starting command gripperEject");
      // leftIngest.set(TalonFXControlMode.PercentOutput, 0.30);
      leftIngest.set(-0.80);
   }

   private void armRetractShort() {
      logger.info("starting command armRetractShort");
      extendTarget = +10;
      logger.debug("set arm retract PID to ad-hoc {}", extendTarget);
      armExtendPID.setReference(extendTarget, ControlType.kPosition);
   }

   private void gripperStop() {
      logger.info("starting command gripperStop");
      // leftIngest.set(TalonFXControlMode.PercentOutput, 0);
      leftIngest.set(0);
   }

   private void driveBackward() {
      logger.info("starting command driveBackward");
      drive.arcadeDrive(-0.60, 0);
   }

   private void driveBackwardOnToRamp() {
      logger.info("starting command driveBackwardOnToRamp");
      drive.arcadeDrive(-0.60, 0);
   }

   @Override
   public void autonomousExit() {
      autonomousComplete = true;

      drive.arcadeDrive(0, 0);

      // FIXME: shouldn't have this enabled
      drive.setSafetyEnabled(true);
   }

   @Override
   public void teleopInit() {
      checkError(armRotate.setIdleMode(IdleMode.kBrake), "AR set idle mode to brake {}");
      checkError(armExtend.setIdleMode(IdleMode.kBrake), "AE set idle mode to brake {}");
      checkError(leftIngest.setIdleMode(IdleMode.kBrake), "LI set idle mode to brake {}");
      checkError(rightIngest.setIdleMode(IdleMode.kBrake), "RI set idle mode to brake {}");

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
      double speed = -driverStick.getRawAxis(1);
      double rotation = -driverStick.getRawAxis(4);
      // Apply crawl?
      if (driverStick.getRawButton(5)) {
         speed *= 0.60;
         rotation *= 0.75;
      }
      drive.arcadeDrive(speed, rotation);

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
      } else {

         // Override Posistion
         if (Math.abs(operatorStick.getRawAxis(5)) > .10) {
            rotateTarget -= operatorStick.getRawAxis(5) * 0.15; // Offset
            armRotatePID.setReference(rotateTarget, ControlType.kPosition); // update pid
         }

         if (operatorStick.getRawButton(6)) {
            if (!armOverRotateButtonPressed) {
               rotateTarget = armRotateOverSetPoint;
               logger.debug("set arm rotate PID to over {}", rotateTarget);
               armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               armOverRotateButtonPressed = true;
            }
         } else if (operatorStick.getRawButton(4)) {
            if (!armHighRotateButtonPressed) {
               rotateTarget = armRotateHighSetPoint;
               logger.debug("set arm rotate PID to high {}", rotateTarget);
               armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               armHighRotateButtonPressed = true;
            }
         } else if (operatorStick.getRawButton(2)) {
            if (!armMidRotateButtonPressed) {
               rotateTarget = armRotateMidSetPoint;
               logger.debug("set arm rotate PID to mid {}", rotateTarget);
               armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               armMidRotateButtonPressed = true;
            }
         } else if (operatorStick.getRawButton(1)) {
            if (!armLowRotateButtonPressed) {
               rotateTarget = armRotateLowSetPoint;
               logger.debug("set arm rotate PID to low {}", rotateTarget);
               armRotatePID.setReference(rotateTarget, ControlType.kPosition);
               armLowRotateButtonPressed = true;
            }
         }
         SmartDashboard.putNumber("Arm Rot Set Target", rotateTarget);

         armOverRotateButtonPressed = operatorStick.getRawButton(6);
         armHighRotateButtonPressed = operatorStick.getRawButton(4);
         armMidRotateButtonPressed = operatorStick.getRawButton(2);
         armLowRotateButtonPressed = operatorStick.getRawButton(1);
      }
      SmartDashboard.getNumber("Arm Rot Set Target", rotateTarget);
      // Position tracked in robot periodic

      double armRotatePosition = armRotateEncoder.getPosition();
      if (highArmRotateAsTarget) {
         highArmRotateReached = pidSetPointChecker.isReached(armRotatePosition);
         midArmRotateReached = false;
         lowArmRotateReached = false;
      } else if (midArmRotateAsTarget) {
         highArmRotateReached = false;
         midArmRotateReached = pidSetPointChecker.isReached(armRotatePosition);
         lowArmRotateReached = false;
      } else if (lowArmRotateAsTarget) {
         highArmRotateReached = false;
         midArmRotateReached = false;
         lowArmRotateReached = pidSetPointChecker.isReached(armRotatePosition);
      }

      armRotationReached = (highArmRotateReached || lowArmRotateReached || midArmRotateReached);
      SmartDashboard.putBoolean("armRotationReached", armRotationReached);

      // -****************************************************************
      // -*
      // -* ARM EXTENSION
      // -*
      // -****************************************************************

      // FWD: Out, BCK: In - no need to reverse sign
      extendPIDDisable = SmartDashboard.getBoolean("Arm Ext PID Enabled", extendPIDDisable);
      if (extendPIDDisable) {
         double armVal = (-operatorStick.getRawAxis(1) * 6); // 0-12v in voltage mode
         SmartDashboard.putNumber("Arm Ext arvVal", armVal);
         armExtendPID.setReference(armVal, ControlType.kVoltage);
      } else {

         // Override Posistion
         if (Math.abs(operatorStick.getRawAxis(1)) > .10) {
            extendTarget -= operatorStick.getRawAxis(1) * 0.3; // Offset
            armExtendPID.setReference(extendTarget, ControlType.kPosition); // update pid
         }

         if (armOverRotateButtonPressed) {
            // Over the top retracts all the way in
            extendTarget = k_armExtendInSetPoint;
            logger.debug("set arm extend PID to full in {}", extendTarget);
            armExtendPID.setReference(extendTarget, ControlType.kPosition);
         } else if (armHighRotateButtonPressed) {
            extendTarget = armExtendHighSetPoint;
            logger.debug("set arm extend PID to high {}", extendTarget);
            armExtendPID.setReference(extendTarget, ControlType.kPosition);
         } else if (armMidRotateButtonPressed) {
            extendTarget = armExtendMidSetPoint;
            logger.debug("set arm extend PID to mid {}", extendTarget);
            armExtendPID.setReference(extendTarget, ControlType.kPosition);
         } else if (armLowRotateButtonPressed) {
            extendTarget = armExtendLowSetPoint;
            logger.debug("set arm extend PID to low {}", extendTarget);
            armExtendPID.setReference(extendTarget, ControlType.kPosition);
         } else if (operatorStick.getRawButton(3)) {
            // Retract all the way in
            extendTarget = k_armExtendInSetPoint;
            logger.debug("set arm extend PID to full in {}", extendTarget);
            armExtendPID.setReference(extendTarget, ControlType.kPosition);
         }
         SmartDashboard.putNumber("Arm Ext Set Target", extendTarget);
      }
      SmartDashboard.putNumber("Arm Ext Set Target", extendTarget);
      // Position tracked in robot periodic

      // -****************************************************************
      // -*
      // -* GRIPPER INGEST
      // -*
      // -****************************************************************

      double inSpeed = operatorStick.getRawAxis(2);
      double outSpeed = operatorStick.getRawAxis(3);
      SmartDashboard.putNumber("Gripper inSpeed", inSpeed);
      SmartDashboard.putNumber("Gripper outSpeed", outSpeed);

      double gripperSpeed = 0;
      if (inSpeed > outSpeed) {
         // speed = inSpeed;
         gripperSpeed = Math.max(-inSpeed, -Math.abs(k_gripperMaxOutSpeed));
      } else {
         // speed = -outSpeed;
         gripperSpeed = Math.min(outSpeed, Math.abs(k_gripperMaxInSpeed));
      }

      if (gripperSpeed == 0) {
         gripperSpeed = Math.abs(k_gripperIdleSpeed); // Make the intake run in slow always
      }

      SmartDashboard.putNumber("Gripper speed", gripperSpeed);
      // leftIngest.set(TalonFXControlMode.PercentOutput, -speed);
      leftIngest.set(-gripperSpeed);
   }

   private PidSetPointReached pidSetPointChecker;

   // Could use velocity if determine scale
   private class PidSetPointReached {
      private final int sameCountCount = 25; // at 0.020 sec loop, this is 0.5 seconds
      private int sameCount;

      private double target;
      private double lastPosition;

      private final double window = 1.0;

      public PidSetPointReached(double target) {
         this.target = target;

         sameCount = 0;

         lastPosition = Double.POSITIVE_INFINITY;
      }

      boolean isReached(double position) {
         if (Math.abs(target - position) < window) {
            sameCount++;
         } else {
            sameCount = 0;
            lastPosition = Double.POSITIVE_INFINITY;
         }
         return (sameCount >= sameCountCount);
      }
   }

   @Override
   public void teleopExit() {
      teleopComplete = true;
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
