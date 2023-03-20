/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.wrist;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.wrist.WristDoNothing;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.PIDTelemetry;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PIDValues;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseWristSubsystem extends BaseSubsystem implements IWristSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseWristSubsystem.class.getName());

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

   protected double cwSetPoint;
   protected double ccwSetPoint;

   BaseWristSubsystem() {
      super(SubsystemNames.wristName);
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(WristDoNothing.class);
      SmartDashboard.putString(TelemetryNames.Wrist.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.Wrist.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
      double v;

      logger.info("new preferences for {}:", myName);

      v = Preferences.getDouble(WristPreferences.PID_P, pidValues.P);
      logger.info("{} = {}", WristPreferences.PID_P, v);
      pidValues.P = v;
      v = Preferences.getDouble(WristPreferences.PID_I, pidValues.I);
      logger.info("{} = {}", WristPreferences.PID_I, v);
      pidValues.I = v;
      v = Preferences.getDouble(WristPreferences.PID_D, pidValues.D);
      logger.info("{} = {}", WristPreferences.PID_D, v);
      pidValues.D = v;
      v = Preferences.getDouble(WristPreferences.PID_IZone, pidValues.IZone);
      logger.info("{} = {}", WristPreferences.PID_IZone, v);
      pidValues.IZone = v;
      v = Preferences.getDouble(WristPreferences.PID_FF, pidValues.FF);
      logger.info("{} = {}", WristPreferences.PID_FF, v);
      pidValues.FF = v;
      v = Preferences.getDouble(WristPreferences.PID_minOutput, pidValues.MinOutput);
      logger.info("{} = {}", WristPreferences.PID_minOutput, v);
      pidValues.MinOutput = v;
      v = Preferences.getDouble(WristPreferences.PID_maxOutput, pidValues.MaxOutput);
      logger.info("{} = {}", WristPreferences.PID_maxOutput, v);
      pidValues.MaxOutput = v;

      v = Preferences.getDouble(WristPreferences.cwSetPoint, cwSetPoint);
      logger.info("{} = {}", WristPreferences.cwSetPoint, v);
      cwSetPoint = v;
      v = Preferences.getDouble(WristPreferences.ccwSetPoint, ccwSetPoint);
      logger.info("{} = {}", WristPreferences.ccwSetPoint, v);
      ccwSetPoint = v;

      WristPosition.cwPosition.set(cwSetPoint);
      WristPosition.ccwPosition.set(ccwSetPoint);
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
      SmartDashboard.putBoolean(TelemetryNames.Wrist.PIDEnabled, tlmPID.PIDEnabled);
      SmartDashboard.putNumber(TelemetryNames.Wrist.PIDTarget, tlmPID.PIDTarget);
      SmartDashboard.putNumber(TelemetryNames.Wrist.PIDCurrent, tlmPID.PIDCurrent);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
