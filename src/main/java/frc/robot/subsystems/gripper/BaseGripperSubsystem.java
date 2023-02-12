/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.gripper.GripperDoNothing;
import frc.robot.modules.pneumatic.IPneumaticModule;
import frc.robot.modules.pneumatic.PneumaticModuleFactory;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseGripperSubsystem extends BaseSubsystem implements IGripperSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseGripperSubsystem.class.getName());

   private IPneumaticModule pneumatic;

   private static final int gripperChannel = 0;

   BaseGripperSubsystem() {
      super(SubsystemNames.gripperName);
      logger.info("constructing");

      pneumatic = PneumaticModuleFactory.getInstance();
      close();

      logger.info("constructed");
   }

   @Override
   public void loadDefaultCommands() {
      loadDefaultCommands(GripperDoNothing.class);
      SmartDashboard.putString(TelemetryNames.Gripper.autoCommand, defaultAutoCommand.getClass().getSimpleName());
      SmartDashboard.putString(TelemetryNames.Gripper.teleCommand, defaultTeleCommand.getClass().getSimpleName());
   }

   protected void loadPreferences() {
      @SuppressWarnings("unused")
      double v;

      logger.info("new preferences for {}:", myName);
   }

   /**
    * Telemetry support by subsystem.
    */
   protected boolean tlmIsOpen = false;

   @Override
   public void updateTelemetry() {
      SmartDashboard.putBoolean(TelemetryNames.Gripper.open, tlmIsOpen);
   }

   @Override
   public void updatePreferences() {
      loadPreferences();
   }

   @Override
   public void open() {
      setSolenoid(true);
   }

   @Override
   public void close() {
      setSolenoid(false);
   }

   private void setSolenoid(boolean isOpen) {
      tlmIsOpen = isOpen;
      pneumatic.setSolenoid(gripperChannel, isOpen);
   }

}
