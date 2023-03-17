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
public class ProtoArmRotatorSubsystem extends BaseArmRotatorSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseArmRotatorSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   private RelativeEncoder encoder;

   ProtoArmRotatorSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      checkError(encoder.setPosition(0), "set encoder position to 0 {}");
      checkError(motor.setOpenLoopRampRate(0), "set open loop ramp rate to 0 {}");

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
      checkError(pid.setOutputRange(pidPrefs.MinOutput, pidPrefs.MaxOutput), "set PID_OutputRange {}");

      // FIXME: Update RampRate

      // FIXME: Update SetPoints
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
      pid.setReference(position.get(), ControlType.kPosition);
      setTlmPIDEnabled(true);
      setTlmPIDTarget(position.get());
   }

   @Override
   public void rotateToTarget(double target) {
      // FIXME: rotateToTarget(double target)
      double newTarget = getTlmPIDTarget() + target;
      pid.setReference(newTarget, ControlType.kPosition);
      setTlmPIDEnabled(true);
      setTlmPIDTarget(newTarget);
   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

   }

}
