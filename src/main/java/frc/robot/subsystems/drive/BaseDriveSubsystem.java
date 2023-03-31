/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.drive.DriveDoNothing;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * Add your docs here.
 */
abstract class BaseDriveSubsystem extends BaseSubsystem implements IDriveSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseDriveSubsystem.class.getName());

   BaseDriveSubsystem() {
      super(SubsystemNames.driveName);
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(DriveDoNothing.class);
      SmartDashboard.putString(TelemetryNames.Drive.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.Drive.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
   }

   @Override
   public void validateCalibration() {
      // Default implementation is empty
   }

   /**
    * Telemetry supported by subsystem.
    */
   // private double tlmSpeed = 0.0;
   // private double tlmTurn = 0.0;
   private double tlmLeftSpeed = 0.0;
   private double tlmRightSpeed = 0.0;
   private boolean tlmBrakeEnabled = false;

   protected void setTlmSpeed(double leftSpeed, double rightSpeed) {
      tlmLeftSpeed = leftSpeed;
      tlmRightSpeed = rightSpeed;
   }

   protected void setTlmBrakeEnabled(boolean brakeEnabled) {
      tlmBrakeEnabled = brakeEnabled;
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.Drive.leftSpeed, tlmLeftSpeed);
      SmartDashboard.putNumber(TelemetryNames.Drive.rightSpeed, tlmRightSpeed);
      SmartDashboard.putBoolean(TelemetryNames.Drive.brakeEnabled, tlmBrakeEnabled);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
