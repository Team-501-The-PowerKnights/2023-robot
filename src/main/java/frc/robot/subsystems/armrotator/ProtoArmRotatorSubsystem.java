/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.telemetry.TelemetryNames;
import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
public class ProtoArmRotatorSubsystem extends BaseArmRotatorSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseArmRotatorSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   private RelativeEncoder encoder;

   private AbsoluteEncoder absEncoder;

   ProtoArmRotatorSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      checkError(encoder.setPosition(0), "set encoder position to 0 {}");
      checkError(motor.setOpenLoopRampRate(0), "set open loop ramp rate to 0 {}");

      absEncoder = motor.getAbsoluteEncoder(Type.kDutyCycle);

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
   public void autonomousInit() {
      super.autonomousInit();

      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
   };

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.ArmRotator.absCurrent, absEncoder.getPosition());

      setTlmPIDCurrent(encoder.getPosition());

      super.updateTelemetry();
   }

   @Override
   public void updatePreferences() {
      super.updatePreferences();

      checkError(pid.setP(pidValues.P), "set PID_P {}");
      checkError(pid.setI(pidValues.I), "set PID_I {}");
      checkError(pid.setD(pidValues.D), "set PID_D {}");
      checkError(pid.setIZone(pidValues.IZone), "set PID_IZone {}");
      checkError(pid.setFF(pidValues.FF), "set PID_FF {}");
      checkError(pid.setOutputRange(pidValues.MinOutput, pidValues.MaxOutput), "set PID_OutputRange {}");

      checkError(motor.setOpenLoopRampRate(rampRate), "set open loop ramp rate to 0 {}");

      ArmRotationPosition.overPosition.set(overSetPoint);
      ArmRotationPosition.highPosition.set(highSetPoint);
      ArmRotationPosition.midPosition.set(midSetPoint);
      ArmRotationPosition.lowPosition.set(lowSetPoint);
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
   public void rotateToPosition(ArmRotationPosition position) {
      logger.trace("position = {}", position);

      double target = position.get();
      rotateToTarget(target);
   }

   @Override
   public void rotateToTarget(double target) {
      logger.trace("set PID target = {}", target);

      checkError(pid.setReference(target, ControlType.kPosition), "PID set reference to kPosition,0 {}");
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void offsetTarget(double offset) {
      logger.trace("offset PID target = {}", offset);

      double target = getTlmPIDTarget();
      target -= offset;
      rotateToTarget(target);
   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

   }

}
