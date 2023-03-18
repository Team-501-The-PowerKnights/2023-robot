/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
public class SuitcaseArmExtenderSubsystem extends BaseArmExtenderSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseArmExtenderSubsystem.class.getName());

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
      }
   }

   @Override
   public void updatePreferences() {
      super.updatePreferences();

      checkError(pid.setP(pidPrefs.P), "set PID_P {}");
      checkError(pid.setI(pidPrefs.I), "set PID_I {}");
      checkError(pid.setD(pidPrefs.D), "set PID_D {}");
      checkError(pid.setIZone(pidPrefs.IZone), "set PID_IZone {}");
      checkError(pid.setFF(pidPrefs.FF), "set PID_FF {}");
      checkError(pid.setOutputRange(pidPrefs.MinOutput, pidPrefs.MaxOutput), "set PID_ min and max output {}");

      checkError(motor.setSoftLimit(SoftLimitDirection.kReverse, minSoftLimit), "set min soft limit to 0 {}");
      checkError(motor.setSoftLimit(SoftLimitDirection.kForward, maxSoftLimit), "set max soft limit to 0 {}");

      ArmExtensionPosition.highPosition.set(highSetPoint);
      ArmExtensionPosition.midPosition.set(midSetPoint);
      ArmExtensionPosition.lowPosition.set(lowSetPoint);
      ArmExtensionPosition.inPosition.set(inSetPoint);
   }

   @Override
   public void disable() {
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle,0 {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void stop() {
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
      checkError(pid.setReference(target, ControlType.kPosition), "PID set reference to kPosition,0 {}");
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void extend(double speed) {
      // TODO Auto-generated method stub

   }

}
