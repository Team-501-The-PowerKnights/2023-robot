/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import org.slf4j.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;
import riolog.ProblemTracker;

/**
 * DOCS: Add your docs here.
 */
public class SuitcaseArmExtenderSubsystem extends BaseArmExtenderSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SuitcaseArmExtenderSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   @SuppressWarnings("unused")
   private RelativeEncoder encoder;

   SuitcaseArmExtenderSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(22, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      checkError(motor.setSoftLimit(SoftLimitDirection.kReverse, 0), "set min soft limit to 0 {}");
      checkError(motor.setSoftLimit(SoftLimitDirection.kForward, 0), "set max soft limit to 0 {}");
      checkError(motor.enableSoftLimit(SoftLimitDirection.kReverse, true), "enable reverse soft limit {}");
      checkError(motor.enableSoftLimit(SoftLimitDirection.kForward, true), "enable forward soft limit {}");

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
   public void updatePreferences() {
      super.updatePreferences();

      checkError(pid.setP(pidValues.P), "set PID_P {}");
      checkError(pid.setI(pidValues.I), "set PID_I {}");
      checkError(pid.setD(pidValues.D), "set PID_D {}");
      checkError(pid.setIZone(pidValues.IZone), "set PID_IZone {}");
      checkError(pid.setFF(pidValues.FF), "set PID_FF {}");
      checkError(pid.setOutputRange(pidValues.MinOutput, pidValues.MaxOutput), "set PID_ min and max output {}");

      checkError(motor.setSoftLimit(SoftLimitDirection.kReverse, minSoftLimit), "set min soft limit to 0 {}");
      checkError(motor.setSoftLimit(SoftLimitDirection.kForward, maxSoftLimit), "set max soft limit to 0 {}");

      ArmExtensionPosition.highPosition.set(highSetPoint);
      ArmExtensionPosition.midPosition.set(midSetPoint);
      ArmExtensionPosition.lowPosition.set(lowSetPoint);
      ArmExtensionPosition.inPosition.set(inSetPoint);
   }

   @Override
   public void disable() {
      // FIXME: Change to non-deprecated method
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle,0 {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void stop() {
      // FIXME: Change to non-deprecated method
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle,0 {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void extendToPosition(ArmExtensionPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      extendToTarget(target);
   }

   @Override
   public void extendToTarget(double target) {
      logger.debug("set PID target = {}", target);

      // FIXME: Change to non-deprecated method
      checkError(pid.setReference(target, ControlType.kPosition), "PID set reference to kPosition,0 {}");
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void offsetTarget(double offset) {
      logger.trace("offset PID target = {}", offset);

      double target = getTlmPIDTarget();
      target += offset;
      extendToTarget(target);
   }

   @Override
   public void extend(double speed) {
      // TODO Auto-generated method stub

   }

}
