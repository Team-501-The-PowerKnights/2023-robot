/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

// import frc.robot.subsystems.arm.ArmFactory;
// import frc.robot.subsystems.drive.DriveFactory;
// import frc.robot.subsystems.gripper.GripperFactory;
import frc.robot.telemetry.TelemetryManager;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * 
 **/
public class SubsystemsFactory {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SubsystemsFactory.class.getName());

   public static List<ISubsystem> constructSubsystems() {
      logger.info("constructing");

      ArrayList<ISubsystem> subsystems = new ArrayList<ISubsystem>();

      @SuppressWarnings("unused")
      TelemetryManager tlmMgr = TelemetryManager.getInstance();

      // ** Drive **
      // Always do drive first
      // SmartDashboard.putNumber(TelemetryNames.Drive.status,
      // PKStatus.unknown.tlmValue);
      // {
      // DriveFactory.constructInstance();
      // ISubsystem ss = DriveFactory.getInstance();
      // tlmMgr.addProvider(ss);
      // subsystems.add(ss);
      // }

      // // ** Arm **
      // SmartDashboard.putNumber(TelemetryNames.Arm.status,
      // PKStatus.unknown.tlmValue);
      // {
      // ArmFactory.constructInstance();
      // ISubsystem ss = ArmFactory.getInstance();
      // tlmMgr.addProvider(ss);
      // subsystems.add(ss);
      // }

      // // ** Gripper **
      // SmartDashboard.putNumber(TelemetryNames.Gripper.status,
      // PKStatus.unknown.tlmValue);
      // {
      // GripperFactory.constructInstance();
      // ISubsystem ss = GripperFactory.getInstance();
      // tlmMgr.addProvider(ss);
      // subsystems.add(ss);
      // }

      // Load and update the preferences now that all the subsystems are created
      for (ISubsystem ss : subsystems) {
         ss.updatePreferences();
      }

      // Load the default commands now that all subsystems are created
      for (ISubsystem ss : subsystems) {
         ss.loadDefaultCommands();
      }

      logger.info("constructed");
      return subsystems;
   }

}
