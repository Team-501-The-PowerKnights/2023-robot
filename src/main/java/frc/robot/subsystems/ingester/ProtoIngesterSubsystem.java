/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.ingester;

import org.slf4j.Logger;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import riolog.PKLogger;
import riolog.ProblemTracker;

/**
 * DOCS: Add your docs here.
 */
class ProtoIngesterSubsystem extends BaseIngesterSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ProtoIngesterSubsystem.class.getName());

   /** */
   private final TalonFX frameMotor;

   ProtoIngesterSubsystem() {
      super();
      logger.info("constructing");

      frameMotor = new TalonFX(33);
      checkError(frameMotor.configFactoryDefault(), "FG restore factory defaults {}");
      frameMotor.setNeutralMode(NeutralMode.Coast);

      logger.info("constructed");
   }

   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private ErrorCode lastCTREError;

   private void checkError(ErrorCode error, String message) {
      if (error != ErrorCode.OK) {
         lastCTREError = error;
         logger.error(message, error);
         ProblemTracker.addError();
      }
   }

   @Override
   public void autonomousInit() {
      super.autonomousInit();

      frameMotor.setNeutralMode(NeutralMode.Brake);
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      frameMotor.setNeutralMode(NeutralMode.Brake);
   };

   @Override
   public void disable() {
      double speed = 0;
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
   }

   @Override
   public void stop() {
      double speed = 0;
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
      setTlmSpeed(speed);
   }

   @Override
   public void pullIn() {
      // TODO Auto-generated method stub
   }

   @Override
   public void pushOut() {
      double speed = -0.3;
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
      setTlmSpeed(speed);
   }

   @Override
   public void idleIn() {
      // Don't run the frame one
      double speed = 0;
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
   }

   @Override
   public void ingest(double speed) {
      if (speed < 0) {
         speed = Math.max(speed, -maxOutSpeed);
      } else {
         speed = Math.min(speed, maxInSpeed);
      }

      // Reduce the rate for frame one
      if (Math.abs(speed) < 0.75) {
         speed = 0.35;
         speed *= (speed < 0) ? -1 : 1;
      }
      frameMotor.set(TalonFXControlMode.PercentOutput, speed);
   }

}
