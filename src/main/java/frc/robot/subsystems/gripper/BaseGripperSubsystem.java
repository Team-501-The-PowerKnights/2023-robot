/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.gripper.GripperDoNothing;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseGripperSubsystem extends BaseSubsystem implements IGripperSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseGripperSubsystem.class.getName());

   protected double maxInSpeed;
   protected double maxOutSpeed;

   protected double idleSpeed;

   private final GripperPreferences prefs;

   BaseGripperSubsystem() {
      super(SubsystemNames.gripperName);
      logger.info("constructing");

      prefs = GripperPreferences.getInstance();

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(GripperDoNothing.class);
      SmartDashboard.putString(TelemetryNames.Gripper.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.Gripper.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
      double v;

      logger.info("new preferences for {}:", myName);

      v = Preferences.getDouble(prefs.maxInSpeed, maxInSpeed);
      logger.info("{} = {}", prefs.maxInSpeed, v);
      maxInSpeed = v;
      v = Preferences.getDouble(prefs.maxOutSpeed, maxOutSpeed);
      logger.info("{} = {}", prefs.maxOutSpeed, v);
      maxOutSpeed = v;

      v = Preferences.getDouble(prefs.idleSpeed, idleSpeed);
      logger.info("{} = {}", prefs.idleSpeed, v);
      idleSpeed = v;
   }

   @Override
   public void validateCalibration() {
      // Default implementation is empty
   }

   /**
    * Telemetry support by subsystem.
    */
   protected double tlmSpeed = 0;

   protected void setTlmSpeed(double speed) {
      tlmSpeed = speed;
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.Gripper.speed, tlmSpeed);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

}
