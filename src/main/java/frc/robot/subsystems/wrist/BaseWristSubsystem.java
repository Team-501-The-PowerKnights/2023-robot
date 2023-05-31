/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.wrist;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.wrist.WristDoNothing;
import frc.robot.subsystems.PIDSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PIDValues;

import riolog.PKLogger;

abstract class BaseWristSubsystem extends PIDSubsystem implements IWristSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BaseWristSubsystem.class.getName());

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
   protected double pid_minVel;
   protected double pid_maxVel;
   protected double pid_maxAcc;
   protected double pid_maxErr;

   //@formatter:off
   protected PIDValues pidValues = new PIDValues(
      pid_P, 
      pid_I, 
      pid_D,
      pid_IZone,
      pid_FF, 
      pid_minOutput, 
      pid_maxOutput,
      pid_minVel,
      pid_maxVel,
      pid_maxAcc,
      pid_maxErr
      );
   //@formatter:on

   protected double upSetPoint;
   protected double overSetPoint;

   private final WristPreferences prefs;

   BaseWristSubsystem() {
      super(SubsystemNames.wristName);
      logger.info("constructing");

      prefs = WristPreferences.getInstance();

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

      v = Preferences.getDouble(prefs.PID_P, pidValues.P);
      logger.info("{} = {}", prefs.PID_P, v);
      pidValues.P = v;
      v = Preferences.getDouble(prefs.PID_I, pidValues.I);
      logger.info("{} = {}", prefs.PID_I, v);
      pidValues.I = v;
      v = Preferences.getDouble(prefs.PID_D, pidValues.D);
      logger.info("{} = {}", prefs.PID_D, v);
      pidValues.D = v;
      v = Preferences.getDouble(prefs.PID_IZone, pidValues.IZone);
      logger.info("{} = {}", prefs.PID_IZone, v);
      pidValues.IZone = v;
      v = Preferences.getDouble(prefs.PID_FF, pidValues.FF);
      logger.info("{} = {}", prefs.PID_FF, v);
      pidValues.FF = v;
      v = Preferences.getDouble(prefs.PID_minOutput, pidValues.MinOutput);
      logger.info("{} = {}", prefs.PID_minOutput, v);
      pidValues.MinOutput = v;
      v = Preferences.getDouble(prefs.PID_maxOutput, pidValues.MaxOutput);
      logger.info("{} = {}", prefs.PID_maxOutput, v);
      pidValues.MaxOutput = v;

      v = Preferences.getDouble(prefs.upSetPoint, upSetPoint);
      logger.info("{} = {}", prefs.upSetPoint, v);
      upSetPoint = v;
      v = Preferences.getDouble(prefs.overSetPoint, overSetPoint);
      logger.info("{} = {}", prefs.overSetPoint, v);
      overSetPoint = v;

      WristPosition.upPosition.set(upSetPoint);
      WristPosition.overPosition.set(overSetPoint);
   }

   @Override
   public void validateCalibration() {
      // Default implementation is empty
   }

   @Override
   public void updateTelemetry() {
      super.updateTelemetry(TelemetryNames.Wrist.PIDEnabled, TelemetryNames.Wrist.PIDTarget,
            TelemetryNames.Wrist.PIDCurrent, TelemetryNames.Wrist.PIDAtTarget, TelemetryNames.Wrist.PIDOnTarget);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
