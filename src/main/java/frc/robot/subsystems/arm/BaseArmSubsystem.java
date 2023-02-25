/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

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

abstract class BaseArmSubsystem extends BaseSubsystem implements IArmSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseArmSubsystem.class.getName());

   /**
    * PID(s) for Subystem
    *
    * These are the defaults to use, and they get overwritten by the values from
    * the
    * <i>Preferences</i> if they exist.
    **/

   /** Rotation **/
   protected double rotate_pid_P = 0;
   protected double rotate_pid_I = 0;
   protected double rotate_pid_D = 0;
   protected double rotate_pid_IZone = 0;
   protected double rotate_pid_FF = 0;
   protected double rotate_pid_minOutput = 0;
   protected double rotate_pid_maxOutput = 0;
   //@formatter:off
   protected PIDPreferences rotatePIDPrefs = new PIDPreferences(
      rotate_pid_P, 
      rotate_pid_I, 
      rotate_pid_D,
      rotate_pid_IZone,
      rotate_pid_FF, 
      rotate_pid_minOutput, 
      rotate_pid_maxOutput);
   //@formatter:on
   protected double rotate_highSetPoint;
   protected double rotate_midSetPoint;
   protected double rotate_lowSetPoint;

   /** Extend **/
   protected double extend_pid_P = 0;
   protected double extend_pid_I = 0;
   protected double extend_pid_D = 0;
   protected double extend_pid_IZone = 0;
   protected double extend_pid_FF = 0;
   protected double extend_pid_minOutput = 0;
   protected double extend_pid_maxOutput = 0;
   //@formatter:off
   protected PIDPreferences extendPIDPrefs = new PIDPreferences(
      extend_pid_P, 
      extend_pid_I, 
      extend_pid_D,
      extend_pid_IZone,
      extend_pid_FF, 
      extend_pid_minOutput, 
      extend_pid_maxOutput);
   //@formatter:on

   BaseArmSubsystem() {
      super(SubsystemNames.armName);
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(ArmDoNothing.class);
      SmartDashboard.putString(TelemetryNames.Arm.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.Arm.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
      double v;

      logger.info("new preferences for {}:", myName);

      v = Preferences.getDouble(ArmPreferences.rotatePID_P, rotatePIDPrefs.P);
      logger.info("{} = {}", ArmPreferences.rotatePID_P, v);
      rotatePIDPrefs.P = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_I, rotatePIDPrefs.I);
      logger.info("{} = {}", ArmPreferences.rotatePID_I, v);
      rotatePIDPrefs.I = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_D, rotatePIDPrefs.D);
      logger.info("{} = {}", ArmPreferences.rotatePID_D, v);
      rotatePIDPrefs.D = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_IZone, rotatePIDPrefs.IZone);
      logger.info("{} = {}", ArmPreferences.rotatePID_IZone, v);
      rotatePIDPrefs.IZone = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_FF, rotatePIDPrefs.FF);
      logger.info("{} = {}", ArmPreferences.rotatePID_FF, v);
      rotatePIDPrefs.FF = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_minOutput, rotatePIDPrefs.MinOutput);
      logger.info("{} = {}", ArmPreferences.rotatePID_minOutput, v);
      rotatePIDPrefs.MinOutput = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_maxOutput, rotatePIDPrefs.MaxOutput);
      logger.info("{} = {}", ArmPreferences.rotatePID_maxOutput, v);
      rotatePIDPrefs.MaxOutput = v;

      v = Preferences.getDouble(ArmPreferences.rotate_highSetPoint, rotate_highSetPoint);
      logger.info("{} = {}", ArmPreferences.rotate_highSetPoint, v);
      rotate_highSetPoint = v;
      v = Preferences.getDouble(ArmPreferences.rotate_midSetPoint, rotate_midSetPoint);
      logger.info("{} = {}", ArmPreferences.rotate_midSetPoint, v);
      rotate_midSetPoint = v;
      v = Preferences.getDouble(ArmPreferences.rotate_lowSetPoint, rotate_lowSetPoint);
      logger.info("{} = {}", ArmPreferences.rotate_lowSetPoint, v);
      rotate_lowSetPoint = v;

      ArmRotationPosition.highPosition.set(rotate_highSetPoint);
      ArmRotationPosition.midPosition.set(rotate_midSetPoint);
      ArmRotationPosition.lowPosition.set(rotate_lowSetPoint);

      v = Preferences.getDouble(ArmPreferences.extendPID_P, extendPIDPrefs.P);
      logger.info("{} = {}", ArmPreferences.extendPID_P, v);
      extendPIDPrefs.P = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_I, extendPIDPrefs.I);
      logger.info("{} = {}", ArmPreferences.extendPID_I, v);
      extendPIDPrefs.I = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_D, extendPIDPrefs.D);
      logger.info("{} = {}", ArmPreferences.extendPID_D, v);
      extendPIDPrefs.D = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_IZone, extendPIDPrefs.IZone);
      logger.info("{} = {}", ArmPreferences.extendPID_IZone, v);
      extendPIDPrefs.IZone = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_FF, extendPIDPrefs.FF);
      logger.info("{} = {}", ArmPreferences.extendPID_FF, v);
      extendPIDPrefs.FF = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_minOutput, extendPIDPrefs.MinOutput);
      logger.info("{} = {}", ArmPreferences.extendPID_minOutput, v);
      extendPIDPrefs.MinOutput = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_maxOutput, extendPIDPrefs.MaxOutput);
      logger.info("{} = {}", ArmPreferences.extendPID_maxOutput, v);
      extendPIDPrefs.MaxOutput = v;
   }

   @Override
   public void validateCalibration() {
      // Default implementation is empty
   }

   /** Standard telemetry for Rotation PID */
   private PIDTelemetry tlmRotatePID = new PIDTelemetry();
   /** Standard telemetry for Extension PID */
   private PIDTelemetry tlmExtendPID = new PIDTelemetry();

   protected void setTlmRotatePIDEnabled(boolean enabled) {
      tlmRotatePID.PIDEnabled = enabled;
   }

   protected void setTlmRotatePIDTarget(double target) {
      tlmRotatePID.PIDTarget = target;
   }

   protected double getTlmRotatePIDTarget() {
      return tlmRotatePID.PIDTarget;
   }

   protected void setTlmRotatePIDCurrent(double current) {
      tlmRotatePID.PIDCurrent = current;
   }

   protected void setTlmExtendPIDEnabled(boolean enabled) {
      tlmExtendPID.PIDEnabled = enabled;
   }

   protected void setTlmExtendPIDTarget(double target) {
      tlmExtendPID.PIDTarget = target;
   }

   protected void setTlmExtendPIDCurrent(double current) {
      tlmExtendPID.PIDCurrent = current;
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putBoolean(TelemetryNames.Arm.rotatePIDEnabled, tlmRotatePID.PIDEnabled);
      SmartDashboard.putNumber(TelemetryNames.Arm.rotatePIDTarget, tlmRotatePID.PIDTarget);
      SmartDashboard.putNumber(TelemetryNames.Arm.rotatePIDCurrent, tlmRotatePID.PIDCurrent);

      SmartDashboard.putBoolean(TelemetryNames.Arm.extendPIDEnabled, tlmExtendPID.PIDEnabled);
      SmartDashboard.putNumber(TelemetryNames.Arm.extendPIDTarget, tlmExtendPID.PIDTarget);
      SmartDashboard.putNumber(TelemetryNames.Arm.extendPIDCurrent, tlmExtendPID.PIDCurrent);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
