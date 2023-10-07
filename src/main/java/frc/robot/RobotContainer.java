/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import org.slf4j.Logger;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.modules.IModule;
import frc.robot.modules.ModulesFactory;
import frc.robot.preferences.PreferencesManager;
import frc.robot.properties.PropertiesManager;
import frc.robot.robot.IRobot;
import frc.robot.robot.RobotFactory;
import frc.robot.sensors.ISensor;
import frc.robot.sensors.SensorsFactory;
import frc.robot.subsystems.ISubsystem;
import frc.robot.subsystems.SubsystemsFactory;
import frc.robot.telemetry.SchedulerProvider;
import frc.robot.telemetry.TelemetryManager;

import riolog.PKLogger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RobotContainer.class.getName());

   //
   private final TelemetryManager tlmMgr;

   //
   private final IRobot robot;

   //
   private final List<IModule> modules;
   //
   private final List<ISensor> sensors;
   //
   private final List<ISubsystem> subsystems;

   /**
    * The container for the robot. Contains subsystems, OI devices, and commands.
    */
   public RobotContainer() {
      logger.info("Creating robot container");

      // Make sure Properties file exists and can be parsed
      initializeProperties();

      // Create the robot with all the configuration, etc.
      RobotFactory.constructInstance();
      robot = RobotFactory.getInstance();

      // Make sure Preferences are initialized
      intializePreferences();

      // Create telemetry manager
      TelemetryManager.constructInstance();
      tlmMgr = TelemetryManager.getInstance();

      // Create command manager
      SchedulerProvider.constructInstance();
      tlmMgr.addProvider(SchedulerProvider.getInstance());

      // Create the OI "subsystem"
      OI.constructInstance();
      tlmMgr.addProvider(OI.getInstance());

      // Create all the modules
      modules = ModulesFactory.constructModules();
      ModeFollowers.getInstance().addAll(modules);

      // Create all the sensors
      sensors = SensorsFactory.constructSensors();
      ModeFollowers.getInstance().addAll(sensors);

      //
      NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-realbot");
      NetworkTableEntry camMode = table.getEntry("camMode");
      camMode.setNumber(1); // 0 = vision process, 1 = driver view

      // Create all the subsystems
      subsystems = SubsystemsFactory.constructSubsystems();
      ModeFollowers.getInstance().addAll(subsystems);

      // Add the OI to mode followers
      // TODO: Determine if this needs to go after all the others?
      ModeFollowers.getInstance().add(OI.getInstance());

      // Create and place auto chooser on dashboard
      robot.createAutoChooser();

      // Configure the trigger bindings
      robot.configureBindings();

      logger.info("Created robot container");
   }

   private void intializePreferences() {
      // Reads and initializes all subsystems preferences
      PreferencesManager.constructInstance();
   }

   private void initializeProperties() {
      // Reads and stores all the properties
      PropertiesManager.constructInstance();

      logger.info("Properties as initialized:");
      PropertiesManager.getInstance().logProperties(logger);
   }

   public boolean isRealAutoSelected() {
      return robot.isRealAutoSelected();
   }

   public Command getAutonomousCommand() {
      return robot.getAutonomousCommand();
   }

}
