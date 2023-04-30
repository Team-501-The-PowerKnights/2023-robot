/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import org.slf4j.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.ProblemTracker;

/**
 * This class implements the <code>DriveSubsystem</code> for a chassis
 * with a drive using 4 {@link CANSparkMax} speed controllers and 2
 * NEO brushless motors. It uses the {@link DifferentialDrive} from
 * WPILib.
 */
abstract class CANSparkMaxDriveSubsystem extends BaseDriveSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(CANSparkMaxDriveSubsystem.class.getName());

   private final CANSparkMax leftFront;
   private final CANSparkMax leftRear;
   private final CANSparkMax rightFront;
   private final CANSparkMax rightRear;

   private final RelativeEncoder leftEncoder;
   private final RelativeEncoder rightEncoder;

   // Using WPILib DifferentialDrive for now
   private final DifferentialDrive drive;

   CANSparkMaxDriveSubsystem() {
      logger.info("constructing");

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

      // Start in coast mode
      setBrake(false);

      // FIXME: Needs to be based on preferences
      checkError(leftFront.setOpenLoopRampRate(0.5), "L setting ramp rate {}");
      checkError(rightFront.setOpenLoopRampRate(0.5), "R setting ramp rate {}");

      // Drive uses the two paired controllers (Front is master)
      drive = new DifferentialDrive(leftFront, rightFront);

      // Instantiation of encoders and zeroing
      leftEncoder = leftFront.getEncoder();
      rightEncoder = rightFront.getEncoder();

      checkError(leftEncoder.setPosition(0.0), "L zeroing the encoder {}");
      checkError(rightEncoder.setPosition(0.0), "R zeroing the encoder {}");

      // FIXME: Shouldn't need to do this
      drive.setSafetyEnabled(false);

      logger.info("constructed");
   }

   // last error (not the same as kOk)
   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private REVLibError lastError;

   private void checkError(REVLibError error, String message) {
      if (error != REVLibError.kOk) {
         lastError = error;
         logger.error(message, error);
         ProblemTracker.addError();
      }
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.Drive.leftEncoderClicks, leftEncoder.getPosition());
      SmartDashboard.putNumber(TelemetryNames.Drive.rightEncoderClicks, rightEncoder.getPosition());

      double leftCount = leftEncoder.getPosition();
      double rightCount = rightEncoder.getPosition();
      double clicks = (leftCount + rightCount) / 2;
      setTlmOdometerClicks(clicks);
      double leftVelocity = leftEncoder.getVelocity();
      double rightVelocity = rightEncoder.getVelocity();
      double velocity = (leftVelocity + rightVelocity) / 2;
      setTlmSpeedometerClicks(velocity);

      super.updateTelemetry();
   }

   @Override
   public void validateCalibration() {
      // TODO Auto-generated method stub
   }

   @Override
   public void disable() {
      // TODO Auto-generated method stub
   }

   @Override
   public void stop() {
      drive.arcadeDrive(0, 0);
   }

   @Override
   public void setBrake(boolean brakeOn) {
      if (brakeOn) {
         checkError(leftFront.setIdleMode(IdleMode.kBrake), "LF set idle mode to brake {}");
         checkError(rightFront.setIdleMode(IdleMode.kBrake), "RF set idle mode to brake {}");
      } else {
         checkError(leftFront.setIdleMode(IdleMode.kCoast), "LF set idle mode to coast {}");
         checkError(rightFront.setIdleMode(IdleMode.kCoast), "RF set idle mode to coast {}");
      }
      setTlmBrakeEnabled(brakeOn);
   }

   @Override
   public void toggleBrake() {
      IdleMode currentMode = leftFront.getIdleMode();
      if (currentMode == IdleMode.kCoast) {
         setBrake(true);
      } else {
         setBrake(false);
      }
   }

   @Override
   public void swap() {
      // TODO Auto-generated method stub
   }

   @Override
   public void drive(double hmiSpeed, double hmiTurn) {
      drive.arcadeDrive(hmiSpeed, hmiTurn);
   }

   @Override
   public RamseteCommand followTrajectory(Trajectory trajectory) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void logOdometer() {
      logger.info("{}: Odometer clicks = {} velocity = {}",
            myName, getOdometerClicks(), getSpeedometerClicks());
   }

   @Override
   public void resetOdometer() {
      checkError(leftEncoder.setPosition(0.0), "L zeroing the encoder {}");
      checkError(rightEncoder.setPosition(0.0), "R zeroing the encoder {}");

      setTlmOdometerClicks(0);
      setTlmSpeedometerClicks(0);
   }

   @Override
   public double getOdometerClicks() {
      return getTlmOdometerClicks();
   }

   @Override
   public double getSpeedometerClicks() {
      return getTlmSpeedometerClicks();
   }

   @Override
   public void setSpeed(int canID, double speed) {
      // TODO Auto-generated method stub
   }

}
