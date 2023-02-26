/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.Preferences;
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

   /** Default preferences for subystem **/
   private final double default_pid_P = 0.0;
   private final double default_pid_I = 0.0;
   private final double default_pid_D = 0.0;
   private final double default_pid_F = 0.0;
   private final double default_ramp = 0.0;

   /** PID for subystem **/
   protected double pid_P = 0;
   protected double pid_I = 0;
   protected double pid_D = 0;
   protected double pid_F = 0;
   /** Speed controller ramping between 0 and max (sec) */
   protected double ramp = 0;

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
      double v;

      logger.info("new preferences for {}:", myName);
      v = Preferences.getDouble(DrivePreferences.pid_P, default_pid_P);
      logger.info("{} = {}", DrivePreferences.pid_P, v);
      pid_P = v;
      v = Preferences.getDouble(DrivePreferences.pid_I, default_pid_I);
      logger.info("{} = {}", DrivePreferences.pid_I, v);
      pid_I = v;
      v = Preferences.getDouble(DrivePreferences.pid_D, default_pid_D);
      logger.info("{} = {}", DrivePreferences.pid_D, v);
      pid_D = v;
      v = Preferences.getDouble(DrivePreferences.pid_F, default_pid_F);
      logger.info("{} = {}", DrivePreferences.pid_F, v);
      pid_F = v;
      v = Preferences.getDouble(DrivePreferences.ramp, default_ramp);
      logger.info("{} = {}", DrivePreferences.ramp, v);
      ramp = v;
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
