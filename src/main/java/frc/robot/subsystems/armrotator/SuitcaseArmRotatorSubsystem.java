/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
public class SuitcaseArmRotatorSubsystem extends BaseArmRotatorSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseArmRotatorSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   private RelativeEncoder encoder;

   SuitcaseArmRotatorSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      checkError(encoder.setPosition(0), "set encoder position to 0 {}");
      checkError(motor.setOpenLoopRampRate(0), "set open loop ramp rate to 0 {}");

      // Assume starts to front as we have no encoder to tell
      onFrontSide = true;

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

   private boolean onFrontSide;

   @Override
   public void periodic() {
      if (isTlmPIDEnabled()) {
         double current = getTlmPIDCurrent();
         // Only works because '0' is straight up
         if (Math.abs(current) < 0.05) {
            if (current < 0) {
               // On the front side of robot; more power in negative side
               checkError(pid.setOutputRange(pidValues.MinOutput, pidValues.MaxOutput),
                     "set PID_ min and max output {}");
            } else {
               // On the back side of robot; more power in positive side
               checkError(pid.setOutputRange(pidValues.MinOutput, pidValues.MaxOutput),
                     "set PID_ min and max output {}");
            }
         }
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

      checkError(motor.setOpenLoopRampRate(rampRate), "set open loop ramp rate to 0 {}");

      ArmRotationPosition.overPosition.set(overSetPoint);
      ArmRotationPosition.highPosition.set(highSetPoint);
      ArmRotationPosition.midPosition.set(midSetPoint);
      ArmRotationPosition.lowPosition.set(lowSetPoint);
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
   public void rotateToPosition(ArmRotationPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      rotateToTarget(target);
   }

   @Override
   public void rotateToTarget(double target) {
      logger.trace("set PID target = {}", target);

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
      rotateToTarget(target);
   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

   }

}
