/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.armextender.ArmExtenderDoNothing;
import frc.robot.preferences.PIDPreferences;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.PIDTelemetry;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseArmExtenderSubsystem extends BaseSubsystem implements IArmExtenderSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseArmExtenderSubsystem.class.getName());

   /**
    * PID(s) for Subystem
    *
    * These are the defaults to use, and they get overwritten by the values from
    * the
    * <i>Preferences</i> if they exist.
    **/

   protected double pid_P = 0;
   protected double pid_I = 0;
   protected double pid_D = 0;
   protected double pid_IZone = 0;
   protected double pid_FF = 0;
   protected double pid_minOutput = 0;
   protected double pid_maxOutput = 0;
   //@formatter:off
   protected PIDPreferences pidPrefs = new PIDPreferences(
      pid_P, 
      pid_I, 
      pid_D,
      pid_IZone,
      pid_FF, 
      pid_minOutput, 
      pid_maxOutput);
   //@formatter:on

   BaseArmExtenderSubsystem() {
      super(SubsystemNames.armExtenderName);
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(ArmExtenderDoNothing.class);
      SmartDashboard.putString(TelemetryNames.ArmExtender.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.ArmExtender.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
      double v;

      logger.info("new preferences for {}:", myName);

      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_P, pidPrefs.P);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_P, v);
      pidPrefs.P = v;
      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_I, pidPrefs.I);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_I, v);
      pidPrefs.I = v;
      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_D, pidPrefs.D);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_D, v);
      pidPrefs.D = v;
      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_IZone, pidPrefs.IZone);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_IZone, v);
      pidPrefs.IZone = v;
      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_FF, pidPrefs.FF);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_FF, v);
      pidPrefs.FF = v;
      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_minOutput, pidPrefs.MinOutput);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_minOutput, v);
      pidPrefs.MinOutput = v;
      v = Preferences.getDouble(ArmExtenderPreferences.extendPID_maxOutput, pidPrefs.MaxOutput);
      logger.info("{} = {}", ArmExtenderPreferences.extendPID_maxOutput, v);
      pidPrefs.MaxOutput = v;
   }

   @Override
   public void validateCalibration() {
      // Default implementation is empty
   }

   /** Standard telemetry for PID */
   private PIDTelemetry tlmPID = new PIDTelemetry();

   protected void setTlmPIDEnabled(boolean enabled) {
      tlmPID.PIDEnabled = enabled;
   }

   protected void setTlmPIDTarget(double target) {
      tlmPID.PIDTarget = target;
   }

   protected double getTlmPIDTarget() {
      return tlmPID.PIDTarget;
   }

   protected void setTlmPIDCurrent(double current) {
      tlmPID.PIDCurrent = current;
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putBoolean(TelemetryNames.ArmExtender.PIDEnabled, tlmPID.PIDEnabled);
      SmartDashboard.putNumber(TelemetryNames.ArmExtender.PIDTarget, tlmPID.PIDTarget);
      SmartDashboard.putNumber(TelemetryNames.ArmExtender.PIDCurrent, tlmPID.PIDCurrent);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
