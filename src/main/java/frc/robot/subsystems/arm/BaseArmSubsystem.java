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
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.PIDTelemetry;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PIDValues;

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
   protected PIDValues rotatePIDValues = new PIDValues(
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
   protected PIDValues extendPIDValues = new PIDValues(
      extend_pid_P, 
      extend_pid_I, 
      extend_pid_D,
      extend_pid_IZone,
      extend_pid_FF, 
      extend_pid_minOutput, 
      extend_pid_maxOutput);
   //@formatter:on

   private final ArmPreferences prefs;

   BaseArmSubsystem() {
      super(SubsystemNames.armName);
      logger.info("constructing");

      prefs = ArmPreferences.getInstance();

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

      v = Preferences.getDouble(prefs.rotatePID_P, rotatePIDValues.P);
      logger.info("{} = {}", prefs.rotatePID_P, v);
      rotatePIDValues.P = v;
      v = Preferences.getDouble(prefs.rotatePID_I, rotatePIDValues.I);
      logger.info("{} = {}", prefs.rotatePID_I, v);
      rotatePIDValues.I = v;
      v = Preferences.getDouble(prefs.rotatePID_D, rotatePIDValues.D);
      logger.info("{} = {}", prefs.rotatePID_D, v);
      rotatePIDValues.D = v;
      v = Preferences.getDouble(prefs.rotatePID_IZone, rotatePIDValues.IZone);
      logger.info("{} = {}", prefs.rotatePID_IZone, v);
      rotatePIDValues.IZone = v;
      v = Preferences.getDouble(prefs.rotatePID_FF, rotatePIDValues.FF);
      logger.info("{} = {}", prefs.rotatePID_FF, v);
      rotatePIDValues.FF = v;
      v = Preferences.getDouble(prefs.rotatePID_minOutput, rotatePIDValues.MinOutput);
      logger.info("{} = {}", prefs.rotatePID_minOutput, v);
      rotatePIDValues.MinOutput = v;
      v = Preferences.getDouble(prefs.rotatePID_maxOutput, rotatePIDValues.MaxOutput);
      logger.info("{} = {}", prefs.rotatePID_maxOutput, v);
      rotatePIDValues.MaxOutput = v;

      v = Preferences.getDouble(prefs.rotate_highSetPoint, rotate_highSetPoint);
      logger.info("{} = {}", prefs.rotate_highSetPoint, v);
      rotate_highSetPoint = v;
      v = Preferences.getDouble(prefs.rotate_midSetPoint, rotate_midSetPoint);
      logger.info("{} = {}", prefs.rotate_midSetPoint, v);
      rotate_midSetPoint = v;
      v = Preferences.getDouble(prefs.rotate_lowSetPoint, rotate_lowSetPoint);
      logger.info("{} = {}", prefs.rotate_lowSetPoint, v);
      rotate_lowSetPoint = v;

      ArmRotationPosition.highPosition.set(rotate_highSetPoint);
      ArmRotationPosition.midPosition.set(rotate_midSetPoint);
      ArmRotationPosition.lowPosition.set(rotate_lowSetPoint);

      v = Preferences.getDouble(prefs.extendPID_P, extendPIDValues.P);
      logger.info("{} = {}", prefs.extendPID_P, v);
      extendPIDValues.P = v;
      v = Preferences.getDouble(prefs.extendPID_I, extendPIDValues.I);
      logger.info("{} = {}", prefs.extendPID_I, v);
      extendPIDValues.I = v;
      v = Preferences.getDouble(prefs.extendPID_D, extendPIDValues.D);
      logger.info("{} = {}", prefs.extendPID_D, v);
      extendPIDValues.D = v;
      v = Preferences.getDouble(prefs.extendPID_IZone, extendPIDValues.IZone);
      logger.info("{} = {}", prefs.extendPID_IZone, v);
      extendPIDValues.IZone = v;
      v = Preferences.getDouble(prefs.extendPID_FF, extendPIDValues.FF);
      logger.info("{} = {}", prefs.extendPID_FF, v);
      extendPIDValues.FF = v;
      v = Preferences.getDouble(prefs.extendPID_minOutput, extendPIDValues.MinOutput);
      logger.info("{} = {}", prefs.extendPID_minOutput, v);
      extendPIDValues.MinOutput = v;
      v = Preferences.getDouble(prefs.extendPID_maxOutput, extendPIDValues.MaxOutput);
      logger.info("{} = {}", prefs.extendPID_maxOutput, v);
      extendPIDValues.MaxOutput = v;
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
      // FIXME: Put back the telemetry here
      // SmartDashboard.putBoolean(TelemetryNames.Arm.rotatePIDEnabled,
      // tlmRotatePID.PIDEnabled);
      // SmartDashboard.putNumber(TelemetryNames.Arm.rotatePIDTarget,
      // tlmRotatePID.PIDTarget);
      // SmartDashboard.putNumber(TelemetryNames.Arm.rotatePIDCurrent,
      // tlmRotatePID.PIDCurrent);

      // SmartDashboard.putBoolean(TelemetryNames.Arm.extendPIDEnabled,
      // tlmExtendPID.PIDEnabled);
      // SmartDashboard.putNumber(TelemetryNames.Arm.extendPIDTarget,
      // tlmExtendPID.PIDTarget);
      // SmartDashboard.putNumber(TelemetryNames.Arm.extendPIDCurrent,
      // tlmExtendPID.PIDCurrent);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
