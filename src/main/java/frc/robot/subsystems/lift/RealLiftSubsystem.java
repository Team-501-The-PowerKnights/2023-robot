/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.lift;

import org.slf4j.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;
import riolog.ProblemTracker;

/**
 * DOCS: Add your docs here.
 */
public class RealLiftSubsystem extends BaseLiftSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RealLiftSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   private final int smartMotionSlotID = 0;
   private RelativeEncoder encoder;

   RealLiftSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      checkError(motor.setSmartCurrentLimit(15), "set current limit to 15 {}");

      pid = motor.getPIDController();
      encoder = motor.getEncoder();

      // Motor sense controls encoder as well with brushless
      motor.setInverted(true);

      logger.info("constructed");
   }

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
   public void disabledInit() {
      super.disabledInit();

      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
   };

   @Override
   public void autonomousInit() {
      super.autonomousInit();

      initPIDControl(pidValues.Use);
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      initPIDControl(pidValues.Use);
   };

   private void initPIDControl(boolean usePID) {
      if (usePID) {
         logger.info("using PID control");
         // Set the PID so when it wakes up it doesn't try to move
         moveToTarget(encoder.getPosition());
         // Coast mode when under PID control
         checkError(motor.setIdleMode(IdleMode.kCoast), "set idle mode to coast {}");
      } else {
         logger.info("not using PID control");
         checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle {}");
         checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      }
   }

   @Override
   public void updatePreferences() {
      super.updatePreferences();

      checkError(pid.setP(pidValues.P), "set PID_P {}");
      checkError(pid.setI(pidValues.I), "set PID_I {}");
      checkError(pid.setD(pidValues.D), "set PID_D {}");
      checkError(pid.setIZone(pidValues.IZone), "set PID_IZone {}");
      checkError(pid.setFF(pidValues.FF), "set PID_FF {}");
      checkError(pid.setOutputRange(pidValues.MinOutput, pidValues.MaxOutput), "set PID_ min and max output {}");

      checkError(pid.setSmartMotionMinOutputVelocity(pidValues.MinVelocity, smartMotionSlotID),
            "set PID_SM_MinVelocity");
      checkError(pid.setSmartMotionMaxVelocity(pidValues.MaxVelocity, smartMotionSlotID),
            "set PID_SM_MaxVelocity");
      checkError(pid.setSmartMotionMaxAccel(pidValues.MaxAccel, smartMotionSlotID),
            "set PID_SM_MaxAccel");
      checkError(pid.setSmartMotionAllowedClosedLoopError(pidValues.AllowedError, smartMotionSlotID),
            "set PID_SM_AllowedError");

      checkError(motor.setClosedLoopRampRate(rampRate), "set closed loop ramp rate {}");

      LiftPosition.startPosition.set(startSetPoint);
      LiftPosition.highPosition.set(highSetPoint);
      LiftPosition.midPosition.set(midSetPoint);
      LiftPosition.lowPosition.set(lowSetPoint);
   }

   @Override
   public void updateTelemetry() {
      setTlmPIDCurrent(encoder.getPosition());
      setTlmPIDOutput(motor.get());

      super.updateTelemetry();
   }

   @Override
   public void disable() {
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void stop() {
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void moveToPosition(LiftPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      moveToTarget(target);
   }

   @Override
   public void moveToTarget(double target) {
      logger.trace("set PID target = {}", target);

      if (pidValues.smartMotionSet()) {
         logger.info("Setting PID to kSmartMotion w/ slotID={}", smartMotionSlotID);
         checkError(pid.setReference(target, ControlType.kSmartMotion, smartMotionSlotID),
               "PID set reference to kSmartMotion {}");
         setAbsoluteTolerance((pidValues.AllowedError * 2), pidValues.AllowedError);
      } else {
         logger.info("Setting PID to kPosition");
         checkError(pid.setReference(target, ControlType.kPosition), "PID set reference to kPosition {}");
         // FIXME: Make this something calculated
         setAbsoluteTolerance((target * 0.10 * 2), (target * 0.10));
      }

      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void offsetTarget(double offset) {
      logger.trace("offset PID target = {}", offset);

      double target = getTlmPIDTarget();
      target += offset;
      moveToTarget(target);
   }

   @Override
   public void move(double speed) {
      logger.trace("speed={}", speed);

      motor.set(speed);
   }

}
