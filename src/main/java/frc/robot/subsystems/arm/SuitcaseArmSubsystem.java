/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
public class SuitcaseArmSubsystem extends BaseArmSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseArmSubsystem.class.getName());

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

      rotatePID.setP(rotatePIDPrefs.P);
      rotatePID.setI(rotatePIDPrefs.I);
      rotatePID.setD(rotatePIDPrefs.D);
      rotatePID.setIZone(rotatePIDPrefs.IZone);
      rotatePID.setFF(rotatePIDPrefs.FF);
      rotatePID.setOutputRange(rotatePIDPrefs.MinOutput, rotatePIDPrefs.MaxOutput);

      extendPID.setP(extendPIDPrefs.P);
      extendPID.setI(extendPIDPrefs.I);
      extendPID.setD(extendPIDPrefs.D);
      extendPID.setIZone(extendPIDPrefs.IZone);
      extendPID.setFF(extendPIDPrefs.FF);
      extendPID.setOutputRange(extendPIDPrefs.MinOutput, extendPIDPrefs.MaxOutput);
   }

   @Override
   public void disable() {
      // TODO Auto-generated method stub

   }

   @Override
   public void stop() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateToPosition(ArmRotationPosition position) {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateToTarget(double target) {
      // TODO Auto-generated method stub

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
