/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * This class implements the <code>DriveSubsystem</code> for the
 * <i>Suitcase-Bot<i>. It uses the {@link DifferentialDrive} from
 * WPILib.
 */
public class ProtoDriveSubsystem extends BaseDriveSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseDriveSubsystem.class.getName());

   private final CANSparkMax leftFront;
   private final CANSparkMax leftRear;
   private final CANSparkMax rightFront;
   private final CANSparkMax rightRear;

   // Using WPILib DifferentialDrive for now
   private final DifferentialDrive drive;

   ProtoDriveSubsystem() {
      logger.info("constructing");

      // Instantiation and factory default-ing motors (can't persist due to timing)
      leftFront = new CANSparkMax(11, MotorType.kBrushless);
      checkError(leftFront.restoreFactoryDefaults(), "LF restore factory defaults {}");
      leftRear = new CANSparkMax(12, MotorType.kBrushless);
      checkError(leftRear.restoreFactoryDefaults(), "LF restore factory defaults {}");
      rightFront = new CANSparkMax(13, MotorType.kBrushless);
      checkError(rightFront.restoreFactoryDefaults(), "LF restore factory defaults {}");
      rightRear = new CANSparkMax(14, MotorType.kBrushless);
      checkError(rightRear.restoreFactoryDefaults(), "LF restore factory defaults {}");

      // Following mode (Rear follows Front)
      checkError(leftRear.follow(leftFront), "L setting following mode {}");

      // Inverted (Right from Left) and Following mode (Rear follows Front)
      rightFront.setInverted(true);
      checkError(rightRear.follow(rightFront), "R setting following mode {}");

      // Start in coast mode
      setBrake(false);

      // Drive uses the two paired controllers (Front is master)
      drive = new DifferentialDrive(leftFront, rightFront);

      logger.info("constructed");
   }

   // last error (not the same as kOk)
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
   public void validateCalibration() {
      // TODO Auto-generated method stub
   }

   @Override
   public void disable() {
      // TODO Auto-generated method stub
   }

   @Override
   public void stop() {
      drive.arcadeDrive(0, 0);
   }

   @Override
   public void setBrake(boolean brakeOn) {
      if (brakeOn) {
         checkError(leftFront.setIdleMode(IdleMode.kBrake), "LF set idle mode to brake {}");
         checkError(rightFront.setIdleMode(IdleMode.kBrake), "RF set idle mode to brake {}");
      } else {
         checkError(leftFront.setIdleMode(IdleMode.kCoast), "LF set idle mode to coast {}");
         checkError(rightFront.setIdleMode(IdleMode.kCoast), "RF set idle mode to coast {}");
      }
   }

   @Override
   public void swap() {
      // TODO Auto-generated method stub
   }

   @Override
   public void drive(double hmiSpeed, double hmiTurn) {
      drive.arcadeDrive(hmiSpeed, hmiTurn);
   }

   @Override
   public RamseteCommand followTrajectory(Trajectory trajectory) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public double getEncoderClicks() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public double getEncoderVelocity() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void setSpeed(int canID, double speed) {
      // TODO Auto-generated method stub
   }

}
