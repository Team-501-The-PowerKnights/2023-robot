/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import org.slf4j.Logger;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import riolog.PKLogger;

/**
 * This class implements the <code>DriveSubsystem</code> for the
 * <i>Suitcase-Bot<i>. It uses the {@link DifferentialDrive} from
 * WPILib.
 */
public class SuitcaseDriveSubsystem extends BaseDriveSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SuitcaseDriveSubsystem.class.getName());

   // Need to WPILib wrapper classes to function w/ Drive class
   private final WPI_VictorSPX leftFront;
   private final WPI_VictorSPX leftRear;
   private final WPI_VictorSPX rightFront;
   private final WPI_VictorSPX rightRear;

   // Using WPILib DifferentialDrive for now
   private final DifferentialDrive drive;

   SuitcaseDriveSubsystem() {
      logger.info("constructing");

      // Instantiation and factory default-ing motors (can't persist due to timing)
      leftFront = new WPI_VictorSPX(11);
      checkError(leftFront.configFactoryDefault(), "LF restore factory defaults {}");
      leftRear = new WPI_VictorSPX(12);
      checkError(leftRear.configFactoryDefault(), "LR restore factory defaults {}");
      rightFront = new WPI_VictorSPX(13);
      checkError(rightFront.configFactoryDefault(), "RF restore factory defaults {}");
      rightRear = new WPI_VictorSPX(14);
      checkError(rightRear.configFactoryDefault(), "RR restore factory defaults {}");

      // Following mode (Rear follows Front)
      leftRear.follow(leftFront, FollowerType.PercentOutput);

      // Following mode (Rear follows Front)
      rightFront.setInverted(true);
      rightRear.follow(rightFront, FollowerType.PercentOutput);

      // Start in coast mode
      setBrake(false);

      // Drive uses the two paired controllers (Front is master)
      drive = new DifferentialDrive(leftFront, rightFront);

      // FIXME: shouldn't have this enabled
      drive.setSafetyEnabled(false);

      logger.info("constructed");
   }

   // last error (not the same as kOk)
   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private ErrorCode lastError;

   private void checkError(ErrorCode error, String message) {
      if (error != ErrorCode.OK) {
         lastError = error;
         logger.error(message, error);
      }
   }

   @Override
   public void disable() {
      // TODO Auto-generated method stub
   }

   @Override
   public void stop() {
      drive.arcadeDrive(0, 0);
   }

   private NeutralMode neutralMode;

   @Override
   public void setBrake(boolean brakeOn) {
      if (brakeOn) {
         neutralMode = NeutralMode.Brake;
      } else {
         neutralMode = NeutralMode.Coast;
      }
      leftFront.setNeutralMode(neutralMode);
      rightFront.setNeutralMode(neutralMode);
      setTlmBrakeEnabled(brakeOn);
   }

   @Override
   public void toggleBrake() {
      NeutralMode currentMode = neutralMode;
      if (currentMode == NeutralMode.Coast) {
         setBrake(true);
      } else {
         setBrake(false);
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
   public void logOdometer() {
      // TODO Auto-generated method stub
   }

   @Override
   public void resetOdometer() {
      // TODO Auto-generated method stub
   }

   @Override
   public double getOdometerClicks() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public double getSpeedometerClicks() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void setSpeed(int canID, double speed) {
      // TODO Auto-generated method stub
   }

}
