/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

import org.slf4j.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;

/**
 * DOCS: Add your docs here.
 */
public class SuitcaseArmSubsystem extends BaseArmSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SuitcaseArmSubsystem.class.getName());

   /** */
   private final CANSparkMax rotateMotor;
   private SparkMaxPIDController rotatePID;
   private RelativeEncoder rotateEncoder;
   /** */
   private final CANSparkMax extendMotor;
   private SparkMaxPIDController extendPID;
   private RelativeEncoder extendEncoder;

   SuitcaseArmSubsystem() {
      logger.info("constructing");

      rotateMotor = new CANSparkMax(20, MotorType.kBrushless);
      checkError(rotateMotor.restoreFactoryDefaults(), "AR restore factory defaults {}");
      checkError(rotateMotor.setIdleMode(IdleMode.kBrake), "AR set idle mode to brake {}");
      rotatePID = rotateMotor.getPIDController();
      rotateEncoder = rotateMotor.getEncoder();
      checkError(rotateEncoder.setPosition(0), "AR set encoder position to 0 {}");

      extendMotor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(extendMotor.restoreFactoryDefaults(), "AR restore factory defaults {}");
      checkError(extendMotor.setIdleMode(IdleMode.kBrake), "AR set idle mode to brake {}");
      extendPID = rotateMotor.getPIDController();
      extendEncoder = rotateMotor.getEncoder();
      checkError(extendEncoder.setPosition(0), "AR set encoder position to 0 {}");

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

      rotatePID.setP(rotatePIDValues.P);
      rotatePID.setI(rotatePIDValues.I);
      rotatePID.setD(rotatePIDValues.D);
      rotatePID.setIZone(rotatePIDValues.IZone);
      rotatePID.setFF(rotatePIDValues.FF);
      rotatePID.setOutputRange(rotatePIDValues.MinOutput, rotatePIDValues.MaxOutput);

      extendPID.setP(extendPIDValues.P);
      extendPID.setI(extendPIDValues.I);
      extendPID.setD(extendPIDValues.D);
      extendPID.setIZone(extendPIDValues.IZone);
      extendPID.setFF(extendPIDValues.FF);
      extendPID.setOutputRange(extendPIDValues.MinOutput, extendPIDValues.MaxOutput);
   }

   @Override
   public void disable() {
      checkError(rotatePID.setReference(0, ControlType.kDutyCycle), "AR PID set reference to kDutyCycle,0 {}");
      setTlmRotatePIDEnabled(false);
   }

   @Override
   public void stop() {
      checkError(rotatePID.setReference(0, ControlType.kDutyCycle), "AR PID set reference to kDutyCycle,0 {}");
      setTlmRotatePIDEnabled(false);
   }

   @Override
   public void rotateToPosition(ArmRotationPosition position) {
      rotatePID.setReference(position.get(), ControlType.kPosition);
      setTlmRotatePIDEnabled(true);
      setTlmRotatePIDTarget(position.get());
   }

   @Override
   public void rotateToTarget(double target) {
      double newTarget = getTlmRotatePIDTarget() + target;
      rotatePID.setReference(newTarget, ControlType.kPosition);
      setTlmRotatePIDEnabled(true);
      setTlmRotatePIDTarget(newTarget);
   }

   @Override
   public void rotateUp() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateDown() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

   }

   @Override
   public void extendToTarget(double target) {
      // TODO Auto-generated method stub

   }

   @Override
   public void extend() {
      // TODO Auto-generated method stub

   }

   @Override
   public void retract() {
      // TODO Auto-generated method stub

   }

   @Override
   public void extend(double speed) {
      // TODO Auto-generated method stub

   }

}
