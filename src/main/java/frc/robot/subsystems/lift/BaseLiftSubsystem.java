/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.lift;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.lift.LiftDoNothing;
import frc.robot.subsystems.PIDSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PIDValues;

import riolog.PKLogger;

abstract class BaseLiftSubsystem extends PIDSubsystem implements ILiftSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BaseLiftSubsystem.class.getName());

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

   protected double rampRate;

   protected double startSetPoint;
   protected double highSetPoint;
   protected double midSetPoint;
   protected double lowSetPoint;

   private final LiftPreferences prefs;

   BaseLiftSubsystem() {
      super(SubsystemNames.liftName);
      logger.info("constructing");

      prefs = LiftPreferences.getInstance();

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(LiftDoNothing.class);
      SmartDashboard.putString(TelemetryNames.Lift.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.Lift.teleCommand, defaultTeleCommand.getClass().getSimpleName());
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

      v = Preferences.getDouble(prefs.rampRate, rampRate);
      logger.info("{} = {}", prefs.rampRate, v);
      rampRate = v;

      v = Preferences.getDouble(prefs.startSetPoint, startSetPoint);
      logger.info("{} = {}", prefs.startSetPoint, v);
      startSetPoint = v;
      v = Preferences.getDouble(prefs.highSetPoint, highSetPoint);
      logger.info("{} = {}", prefs.highSetPoint, v);
      highSetPoint = v;
      v = Preferences.getDouble(prefs.midSetPoint, midSetPoint);
      logger.info("{} = {}", prefs.midSetPoint, v);
      midSetPoint = v;
      v = Preferences.getDouble(prefs.lowSetPoint, lowSetPoint);
      logger.info("{} = {}", prefs.lowSetPoint, v);
      lowSetPoint = v;

      LiftPosition.startPosition.set(startSetPoint);
      LiftPosition.lowPosition.set(lowSetPoint);
      LiftPosition.midPosition.set(midSetPoint);
      LiftPosition.highPosition.set(highSetPoint);
   }

   @Override
   public void validateCalibration() {
      // Default implementation is empty
   }

   @Override
   public void updateTelemetry() {
      super.updateTelemetry(TelemetryNames.Lift.PIDEnabled, TelemetryNames.Lift.PIDTarget,
            TelemetryNames.Lift.PIDCurrent, TelemetryNames.Lift.PIDAtTarget,
            TelemetryNames.Lift.PIDOnTarget, TelemetryNames.Lift.PIDOutput);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
