/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.arm.ArmDoNothing;
import frc.robot.preferences.PIDPreferences;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.PIDTelemetry;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseArmRotatorSubsystem extends BaseSubsystem implements IArmRotatorSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseArmRotatorSubsystem.class.getName());

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
   protected double highSetPoint;
   protected double midSetPoint;
   protected double lowSetPoint;

   BaseArmRotatorSubsystem() {
      super(SubsystemNames.armRotatorName);
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(ArmDoNothing.class);
      SmartDashboard.putString(TelemetryNames.ArmRotator.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.ArmRotator.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
      double v;

      logger.info("new preferences for {}:", myName);

      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_P, pidPrefs.P);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_P, v);
      pidPrefs.P = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_I, pidPrefs.I);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_I, v);
      pidPrefs.I = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_D, pidPrefs.D);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_D, v);
      pidPrefs.D = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_IZone, pidPrefs.IZone);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_IZone, v);
      pidPrefs.IZone = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_FF, pidPrefs.FF);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_FF, v);
      pidPrefs.FF = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_minOutput, pidPrefs.MinOutput);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_minOutput, v);
      pidPrefs.MinOutput = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotatePID_maxOutput, pidPrefs.MaxOutput);
      logger.info("{} = {}", ArmRotatorPreferences.rotatePID_maxOutput, v);
      pidPrefs.MaxOutput = v;

      v = Preferences.getDouble(ArmRotatorPreferences.rotate_highSetPoint, highSetPoint);
      logger.info("{} = {}", ArmRotatorPreferences.rotate_highSetPoint, v);
      highSetPoint = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotate_midSetPoint, midSetPoint);
      logger.info("{} = {}", ArmRotatorPreferences.rotate_midSetPoint, v);
      midSetPoint = v;
      v = Preferences.getDouble(ArmRotatorPreferences.rotate_lowSetPoint, lowSetPoint);
      logger.info("{} = {}", ArmRotatorPreferences.rotate_lowSetPoint, v);
      lowSetPoint = v;

      ArmRotationPosition.highPosition.set(highSetPoint);
      ArmRotationPosition.midPosition.set(midSetPoint);
      ArmRotationPosition.lowPosition.set(lowSetPoint);
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
      SmartDashboard.putBoolean(TelemetryNames.ArmRotator.PIDEnabled, tlmPID.PIDEnabled);
      SmartDashboard.putNumber(TelemetryNames.ArmRotator.PIDTarget, tlmPID.PIDTarget);
      SmartDashboard.putNumber(TelemetryNames.ArmRotator.PIDCurrent, tlmPID.PIDCurrent);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
