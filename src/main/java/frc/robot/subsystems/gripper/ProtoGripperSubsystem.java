/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

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
   public void disable() {
      leftMotor.set(0);
      setTlmSpeed(0);
   }

   @Override
   public void stop() {
      leftMotor.set(0);
      setTlmSpeed(0);
   }

   @Override
   public void pullIn() {
      // TODO Auto-generated method stub
   }

   @Override
   public void pushOut() {
      // TODO Auto-generated method stub
   }

   @Override
   public void grip(double speed) {
      if (speed < 0) {
         speed = Math.max(speed, -0.7); // -maxOutSpeed
      } else {
         speed = Math.min(speed, 0.5); // maxInSpeed
      }
      leftMotor.set(speed);
      setTlmSpeed(speed);
   }

}
