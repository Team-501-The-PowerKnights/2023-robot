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
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseArmSubsystem extends BaseSubsystem implements IArmSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseArmSubsystem.class.getName());

   /** Default preferences for subystem **/
   private final double default_pid_P = 0.0;
   private final double default_pid_I = 0.0;
   private final double default_pid_D = 0.0;
   private final double default_pid_F = 0.0;

   /** PID for subystem **/
   protected double pid_P = 0;
   protected double pid_I = 0;
   protected double pid_D = 0;
   protected double pid_F = 0;

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
      v = Preferences.getDouble(ArmPreferences.pid_P, default_pid_P);
      logger.info("{} = {}", ArmPreferences.pid_P, v);
      pid_P = v;
      v = Preferences.getDouble(ArmPreferences.pid_I, default_pid_I);
      logger.info("{} = {}", ArmPreferences.pid_I, v);
      pid_I = v;
      v = Preferences.getDouble(ArmPreferences.pid_D, default_pid_D);
      logger.info("{} = {}", ArmPreferences.pid_D, v);
      pid_D = v;
      v = Preferences.getDouble(ArmPreferences.pid_F, default_pid_F);
      logger.info("{} = {}", ArmPreferences.pid_F, v);
      pid_F = v;
   }

   @Override
   public void updateTelemetry() {
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
