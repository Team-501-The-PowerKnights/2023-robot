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

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.properties.PropertiesManager;
import frc.robot.subsystems.arm.ArmFactory;
import frc.robot.subsystems.armextender.ArmExtenderFactory;
import frc.robot.subsystems.armrotator.ArmRotatorFactory;
import frc.robot.subsystems.drive.DriveFactory;
import frc.robot.subsystems.gripper.GripperFactory;
import frc.robot.subsystems.wrist.WristFactory;
import frc.robot.telemetry.TelemetryManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;

/**
 * 
 **/
public class SubsystemsFactory {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SubsystemsFactory.class.getName());

   public static List<ISubsystem> constructSubsystems() {
      logger.info("constructing");

      ArrayList<ISubsystem> subsystems = new ArrayList<ISubsystem>();

      TelemetryManager tlmMgr = TelemetryManager.getInstance();

      // ** Drive **
      // Always do drive first
      SmartDashboard.putNumber(TelemetryNames.Drive.status,
            PKStatus.unknown.tlmValue);
      {
         DriveFactory.constructInstance();
         ISubsystem ss = DriveFactory.getInstance();
         tlmMgr.addProvider(ss);
         subsystems.add(ss);
      }

      switch (PropertiesManager.getInstance().getRobotName()) {
         case "Suitcase-Bot":
         case "Swprog-Bot":
         case "Proto-Bot":

            // ** ArmRotator **
            // @formatter:off
            SmartDashboard.putNumber(TelemetryNames.ArmRotator.status,
                  PKStatus.unknown.tlmValue);
            {
               ArmRotatorFactory.constructInstance();
               ISubsystem ss = ArmRotatorFactory.getInstance();
               tlmMgr.addProvider(ss);
               subsystems.add(ss);
            }
            // @formatter:off

            // ** ArmExtender **
            // @formatter:off
            SmartDashboard.putNumber(TelemetryNames.ArmExtender.status,
                  PKStatus.unknown.tlmValue);
            {
               ArmExtenderFactory.constructInstance();
               ISubsystem ss = ArmExtenderFactory.getInstance();
               tlmMgr.addProvider(ss);
               subsystems.add(ss);
            }
            // @formatter:on

            SmartDashboard.putNumber(TelemetryNames.Arm.status,
                  PKStatus.unknown.tlmValue);

            break;

         case "Real-Bot":

            SmartDashboard.putNumber(TelemetryNames.ArmRotator.status,
                  PKStatus.unknown.tlmValue);
            SmartDashboard.putNumber(TelemetryNames.ArmExtender.status,
                  PKStatus.unknown.tlmValue);

            // ** Arm **
            // @formatter:off
            SmartDashboard.putNumber(TelemetryNames.Arm.status,
                  PKStatus.unknown.tlmValue);
            {
               ArmFactory.constructInstance();
               ISubsystem ss = ArmFactory.getInstance();
               tlmMgr.addProvider(ss);
               subsystems.add(ss);
            }
            // @formatter:on

            break;

         default:
            break;
      }

      // ** Gripper **
      SmartDashboard.putNumber(TelemetryNames.Gripper.status,
            PKStatus.unknown.tlmValue);
      {
         GripperFactory.constructInstance();
         ISubsystem ss = GripperFactory.getInstance();
         tlmMgr.addProvider(ss);
         subsystems.add(ss);
      }

      // ** Gripper **
      SmartDashboard.putNumber(TelemetryNames.Wrist.status,
            PKStatus.unknown.tlmValue);
      {
         WristFactory.constructInstance();
         ISubsystem ss = WristFactory.getInstance();
         tlmMgr.addProvider(ss);
         subsystems.add(ss);
      }

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
