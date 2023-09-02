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
   private RelativeEncoder encoder;

   RealLiftSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      // Motor sense controls encoder as well with brushless
      motor.setInverted(true);
      checkError(motor.setClosedLoopRampRate(0), "set closed loop ramp rate to 0 {}");
      // checkError(motor.setSmartCurrentLimit(20), "set current limit to 20 {}");

      // Set the PID so when it wakes up it doesn't try to move
      liftToTarget(encoder.getPosition());

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
   public void autonomousInit() {
      super.autonomousInit();

      checkError(motor.setIdleMode(IdleMode.kCoast), "set idle mode to brake {}");
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      // Set the PID so when it wakes up it doesn't try to move
      liftToTarget(encoder.getPosition());

      checkError(motor.setIdleMode(IdleMode.kCoast), "set idle mode to brake {}");
   };

   @Override
   public void updateTelemetry() {
      setTlmPIDCurrent(encoder.getPosition());

      super.updateTelemetry();
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
   public void liftToPosition(LiftPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      liftToTarget(target);
   }

   @Override
   public void liftToTarget(double target) {
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
      liftToTarget(target);
   }

   @Override
   public void lift(double speed) {
      // TODO Auto-generated method stub

   }

}
