/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.turret;

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
public class RealTurretSubsystem extends BaseTurretSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RealTurretSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   private RelativeEncoder encoder;

   RealTurretSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(51, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");

      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      // Motor sense controls encoder as well with brushless
      motor.setInverted(true);
      checkError(motor.setClosedLoopRampRate(0), "set closed loop ramp rate to 0 {}");
      // checkError(motor.setSmartCurrentLimit(20), "set current limit to 20 {}");

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

      // Set the PID so when it wakes up it doesn't try to move
      moveToTarget(encoder.getPosition());
      // Coast mode when under PID control
      checkError(motor.setIdleMode(IdleMode.kCoast), "set idle mode to coast {}");
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      // Set the PID so when it wakes up it doesn't try to move
      moveToTarget(encoder.getPosition());
      // Coast mode when under PID control
      checkError(motor.setIdleMode(IdleMode.kCoast), "set idle mode to coast {}");
   };

   @Override
   public void updateTelemetry() {
      logger.trace("called");
      setTlmPIDCurrent(encoder.getPosition());

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
   public void moveToPosition(TurretPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      moveToTarget(target);
   }

   @Override
   public void moveToTarget(double target) {
      logger.trace("set PID target = {}", target);

      checkError(pid.setReference(target, ControlType.kPosition), "PID set reference to kPosition {}");
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
      // TODO Auto-generated method stub
      logger.trace("speed={}", speed);

      motor.set(speed);
   }

}
