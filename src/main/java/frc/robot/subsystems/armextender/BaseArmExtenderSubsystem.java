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
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.PIDTelemetry;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PIDValues;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseArmExtenderSubsystem extends BaseSubsystem implements IArmExtenderSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseArmExtenderSubsystem.class.getName());

   /**
    * PID(s) for Subystem
    *
    * These are the defaults to use, and they get overwritten by the values from
    * the <i>Preferences</i> if they exist.
    **/

   protected double pid_P;
   protected double pid_I;
   protected double pid_D;
   protected double pid_IZone;
   protected double pid_FF;
   protected double pid_minOutput;
   protected double pid_maxOutput;
   //@formatter:off
   protected PIDValues pidValues = new PIDValues(
      pid_P, 
      pid_I, 
      pid_D,
      pid_IZone,
      pid_FF, 
      pid_minOutput, 
      pid_maxOutput);
   //@formatter:on

   protected float minSoftLimit;
   protected float maxSoftLimit;

   protected double overSetPoint;
   protected double highSetPoint;
   protected double midSetPoint;
   protected double lowSetPoint;
   protected double inSetPoint;
   //
   protected double autoConeSetPoint;

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

      v = Preferences.getDouble(ArmExtenderPreferences.PID_P, pidValues.P);
      logger.info("{} = {}", ArmExtenderPreferences.PID_P, v);
      pidValues.P = v;
      v = Preferences.getDouble(ArmExtenderPreferences.PID_I, pidValues.I);
      logger.info("{} = {}", ArmExtenderPreferences.PID_I, v);
      pidValues.I = v;
      v = Preferences.getDouble(ArmExtenderPreferences.PID_D, pidValues.D);
      logger.info("{} = {}", ArmExtenderPreferences.PID_D, v);
      pidValues.D = v;
      v = Preferences.getDouble(ArmExtenderPreferences.PID_IZone, pidValues.IZone);
      logger.info("{} = {}", ArmExtenderPreferences.PID_IZone, v);
      pidValues.IZone = v;
      v = Preferences.getDouble(ArmExtenderPreferences.PID_FF, pidValues.FF);
      logger.info("{} = {}", ArmExtenderPreferences.PID_FF, v);
      pidValues.FF = v;
      v = Preferences.getDouble(ArmExtenderPreferences.PID_minOutput, pidValues.MinOutput);
      logger.info("{} = {}", ArmExtenderPreferences.PID_minOutput, v);
      pidValues.MinOutput = v;
      v = Preferences.getDouble(ArmExtenderPreferences.PID_maxOutput, pidValues.MaxOutput);
      logger.info("{} = {}", ArmExtenderPreferences.PID_maxOutput, v);
      pidValues.MaxOutput = v;

      v = Preferences.getDouble(ArmExtenderPreferences.minSoftLimit, minSoftLimit);
      logger.info("{} = {}", ArmExtenderPreferences.minSoftLimit, v);
      minSoftLimit = (float) v;
      v = Preferences.getDouble(ArmExtenderPreferences.maxSoftLimit, maxSoftLimit);
      logger.info("{} = {}", ArmExtenderPreferences.maxSoftLimit, v);
      maxSoftLimit = (float) v;

      v = Preferences.getDouble(ArmExtenderPreferences.overSetPoint, overSetPoint);
      logger.info("{} = {}", ArmExtenderPreferences.overSetPoint, v);
      overSetPoint = v;
      v = Preferences.getDouble(ArmExtenderPreferences.highSetPoint, highSetPoint);
      logger.info("{} = {}", ArmExtenderPreferences.highSetPoint, v);
      highSetPoint = v;
      v = Preferences.getDouble(ArmExtenderPreferences.midSetPoint, midSetPoint);
      logger.info("{} = {}", ArmExtenderPreferences.midSetPoint, v);
      midSetPoint = v;
      v = Preferences.getDouble(ArmExtenderPreferences.lowSetPoint, lowSetPoint);
      logger.info("{} = {}", ArmExtenderPreferences.lowSetPoint, v);
      lowSetPoint = v;
      v = Preferences.getDouble(ArmExtenderPreferences.inSetPoint, inSetPoint);
      logger.info("{} = {}", ArmExtenderPreferences.inSetPoint, v);
      inSetPoint = v;

      v = Preferences.getDouble(ArmExtenderPreferences.autoConeSetPoint, autoConeSetPoint);
      logger.info("{} = {}", ArmExtenderPreferences.autoConeSetPoint, v);
      autoConeSetPoint = v;

      ArmExtensionPosition.overPosition.set(overSetPoint);
      ArmExtensionPosition.highPosition.set(highSetPoint);
      ArmExtensionPosition.midPosition.set(midSetPoint);
      ArmExtensionPosition.lowPosition.set(lowSetPoint);
      //
      ArmExtensionPosition.autoConePosition.set(autoConeSetPoint);
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
   public void logTelemetry() {
      logger.debug("{}: PID target={} current={}", myName, tlmPID.PIDTarget, tlmPID.PIDCurrent);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
