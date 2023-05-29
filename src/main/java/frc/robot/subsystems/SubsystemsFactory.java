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
import frc.robot.subsystems.lift.LiftFactory;
import frc.robot.subsystems.turret.TurretFactory;
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

      logger.info("constructing subsystems ...");

      // Always have Drive
      boolean constructDrive = false;

      // Common Subsystems
      boolean constructGripper = false;

      // Proto-Bot Unique
      boolean constructArmRotator = false;
      boolean constructArmExtener = false;
      boolean constructWrist = false;

      // Real-Bot Unique
      boolean constructTurret = false;
      boolean constructLift = false;
      boolean constructArm = false;

      switch (PropertiesManager.getInstance().getRobotName()) {

         case "Suitcase-Bot":

            constructDrive = true;

            constructGripper = true;

            constructArmRotator = true;
            constructArmExtener = true;
            constructWrist = true;

            constructTurret = true;
            constructLift = true;
            constructArm = true;

            break;

         case "Swprog-Bot":

            constructDrive = true;

            break;

         case "Proto-Bot":

            constructDrive = true;

            constructGripper = true;

            constructArmRotator = true;
            constructArmExtener = true;
            constructWrist = true;

            break;

         case "Real-Bot":

            constructDrive = true;

            constructGripper = true;

            constructTurret = true;
            constructLift = true;
            constructArm = true;

            break;

         default:
            break;
      }

      // ** Drive **
      // Always do drive first
      if (constructDrive) {
         logger.info("construct Drive");
         SmartDashboard.putNumber(TelemetryNames.Drive.status,
               PKStatus.unknown.tlmValue);
         {
            DriveFactory.constructInstance();
            ISubsystem ss = DriveFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** Gripper **
      if (constructGripper) {
         logger.info("construct Gripper");
         SmartDashboard.putNumber(TelemetryNames.Gripper.status,
               PKStatus.unknown.tlmValue);
         {
            GripperFactory.constructInstance();
            ISubsystem ss = GripperFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** ArmRotator **
      if (constructArmRotator) {
         logger.info("construct ArmRotator");
         SmartDashboard.putNumber(TelemetryNames.ArmRotator.status,
               PKStatus.unknown.tlmValue);
         {
            ArmRotatorFactory.constructInstance();
            ISubsystem ss = ArmRotatorFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** ArmExtender **
      if (constructArmExtener) {
         logger.info("construct ArmExtender");
         SmartDashboard.putNumber(TelemetryNames.ArmExtender.status,
               PKStatus.unknown.tlmValue);
         {
            ArmExtenderFactory.constructInstance();
            ISubsystem ss = ArmExtenderFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** Wrist **
      if (constructWrist) {
         logger.info("construct Wrist");
         SmartDashboard.putNumber(TelemetryNames.Wrist.status,
               PKStatus.unknown.tlmValue);
         {
            WristFactory.constructInstance();
            ISubsystem ss = WristFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** Turret **
      if (constructTurret) {
         logger.info("construct Turret");
         SmartDashboard.putNumber(TelemetryNames.Turret.status,
               PKStatus.unknown.tlmValue);
         {
            TurretFactory.constructInstance();
            ISubsystem ss = TurretFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** List **
      if (constructLift) {
         logger.info("construct Lift");
         SmartDashboard.putNumber(TelemetryNames.Lift.status,
               PKStatus.unknown.tlmValue);
         {
            LiftFactory.constructInstance();
            ISubsystem ss = LiftFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // ** Arm **
      if (constructArm) {
         logger.info("construct Arm");
         SmartDashboard.putNumber(TelemetryNames.Arm.status,
               PKStatus.unknown.tlmValue);
         {
            ArmFactory.constructInstance();
            ISubsystem ss = ArmFactory.getInstance();
            tlmMgr.addProvider(ss);
            subsystems.add(ss);
         }
      }

      // Load and update the preferences now that all the subsystems are created
      logger.info("updating subsystem preferences ...");
      for (ISubsystem ss : subsystems) {
         ss.updatePreferences();
      }

      // Load the default commands now that all subsystems are created
      logger.info("loading subsystem default commands ...");
      for (ISubsystem ss : subsystems) {
         ss.loadDefaultCommands();
      }

      logger.info("constructed");
      return subsystems;
   }

}
