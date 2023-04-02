/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
public class ProtoGripperSubsystem extends BaseGripperSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ProtoGripperSubsystem.class.getName());

   /** */
   private final CANSparkMax leftMotor;
   private final CANSparkMax rightMotor;
   //
   private final TalonFX frameMotor;

   ProtoGripperSubsystem() {
      super();
      logger.info("constructing");

      leftMotor = new CANSparkMax(31, MotorType.kBrushed);
      checkError(leftMotor.restoreFactoryDefaults(), "LG restore factory defaults {}");
      checkError(leftMotor.setIdleMode(IdleMode.kCoast), "LG set idle mode to coast {}");

      rightMotor = new CANSparkMax(32, MotorType.kBrushed);
      checkError(rightMotor.restoreFactoryDefaults(), "RG restore factory defaults {}");
      checkError(rightMotor.setIdleMode(IdleMode.kCoast), "RG set idle mode to coast {}");

      checkError(rightMotor.follow(leftMotor, true), "RG set follow and invert to true {}");

      frameMotor = new TalonFX(33);
      checkError(frameMotor.configFactoryDefault(), "FG restore factory defaults {}");
      frameMotor.setNeutralMode(NeutralMode.Coast);

      // frameMotor.setInverted(true);
      // frameMotor.follow(leftMotor);

      logger.info("constructed");
   }

   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private REVLibError lastREVError;

   private void checkError(REVLibError error, String message) {
      if (error != REVLibError.kOk) {
         lastREVError = error;
         logger.error(message, error);
      }
   }

   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private ErrorCode lastCTREError;

   private void checkError(ErrorCode error, String message) {
      if (error != ErrorCode.OK) {
         lastCTREError = error;
         logger.error(message, error);
      }
   }

   @Override
   public void autonomousInit() {
      super.autonomousInit();

      checkError(leftMotor.setIdleMode(IdleMode.kBrake), "LG set idle mode to brake {}");
      checkError(rightMotor.setIdleMode(IdleMode.kBrake), "RG set idle mode to brake {}");
      frameMotor.setNeutralMode(NeutralMode.Brake);
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      checkError(leftMotor.setIdleMode(IdleMode.kBrake), "LG set idle mode to brake {}");
      checkError(rightMotor.setIdleMode(IdleMode.kBrake), "RG set idle mode to brake {}");
      frameMotor.setNeutralMode(NeutralMode.Brake);
   };

   @Override
   public void disable() {
      double speed = 0;
      leftMotor.set(speed);
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
   }

   @Override
   public void stop() {
      double speed = 0;
      leftMotor.set(speed);
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
      setTlmSpeed(speed);
   }

   @Override
   public void pullIn() {
      // TODO Auto-generated method stub
   }

   @Override
   public void pushOut() {
      double speed = -0.8;
      leftMotor.set(speed);
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
      setTlmSpeed(speed);
   }

   @Override
   public void idleIn() {
      grip(idleSpeed);
   }

   @Override
   public void grip(double speed) {
      if (speed < 0) {
         speed = Math.max(speed, -maxOutSpeed);
      } else {
         speed = Math.min(speed, maxInSpeed);
      }
      leftMotor.set(speed);
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
      setTlmSpeed(speed);
   }

}
