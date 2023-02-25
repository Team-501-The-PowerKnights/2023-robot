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
   protected PIDPreferences rotatePID = new PIDPreferences(
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
   protected PIDPreferences extendPID = new PIDPreferences(
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

      v = Preferences.getDouble(ArmPreferences.rotatePID_P, rotatePID.P);
      logger.info("{} = {}", ArmPreferences.rotatePID_P, v);
      rotatePID.P = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_I, rotatePID.I);
      logger.info("{} = {}", ArmPreferences.rotatePID_I, v);
      rotatePID.I = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_D, rotatePID.D);
      logger.info("{} = {}", ArmPreferences.rotatePID_D, v);
      rotatePID.D = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_IZone, rotatePID.IZone);
      logger.info("{} = {}", ArmPreferences.rotatePID_IZone, v);
      rotatePID.IZone = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_FF, rotatePID.FF);
      logger.info("{} = {}", ArmPreferences.rotatePID_FF, v);
      rotatePID.FF = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_minOutput, rotatePID.MinOutput);
      logger.info("{} = {}", ArmPreferences.rotatePID_minOutput, v);
      rotatePID.MinOutput = v;
      v = Preferences.getDouble(ArmPreferences.rotatePID_maxOutput, rotatePID.MaxOutput);
      logger.info("{} = {}", ArmPreferences.rotatePID_maxOutput, v);
      rotatePID.MaxOutput = v;

      v = Preferences.getDouble(ArmPreferences.rotate_highSetPoint, rotate_highSetPoint);
      logger.info("{} = {}", ArmPreferences.rotate_highSetPoint, v);
      rotate_highSetPoint = v;
      v = Preferences.getDouble(ArmPreferences.rotate_midSetPoint, rotate_midSetPoint);
      logger.info("{} = {}", ArmPreferences.rotate_midSetPoint, v);
      rotate_midSetPoint = v;
      v = Preferences.getDouble(ArmPreferences.rotate_lowSetPoint, rotate_lowSetPoint);
      logger.info("{} = {}", ArmPreferences.rotate_lowSetPoint, v);
      rotate_lowSetPoint = v;

      v = Preferences.getDouble(ArmPreferences.extendPID_P, extendPID.P);
      logger.info("{} = {}", ArmPreferences.extendPID_P, v);
      extendPID.P = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_I, extendPID.I);
      logger.info("{} = {}", ArmPreferences.extendPID_I, v);
      extendPID.I = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_D, extendPID.D);
      logger.info("{} = {}", ArmPreferences.extendPID_D, v);
      extendPID.D = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_IZone, extendPID.IZone);
      logger.info("{} = {}", ArmPreferences.extendPID_IZone, v);
      extendPID.IZone = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_FF, extendPID.FF);
      logger.info("{} = {}", ArmPreferences.extendPID_FF, v);
      extendPID.FF = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_minOutput, extendPID.MinOutput);
      logger.info("{} = {}", ArmPreferences.extendPID_minOutput, v);
      extendPID.MinOutput = v;
      v = Preferences.getDouble(ArmPreferences.extendPID_maxOutput, extendPID.MaxOutput);
      logger.info("{} = {}", ArmPreferences.extendPID_maxOutput, v);
      extendPID.MaxOutput = v;
   }

   @Override
   public void updateTelemetry() {
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
