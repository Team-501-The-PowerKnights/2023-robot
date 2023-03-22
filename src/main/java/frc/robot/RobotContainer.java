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

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.AutoDoNothing;
import frc.robot.commands.drive.DriveBackwardTimed;
import frc.robot.modules.IModule;
import frc.robot.modules.ModulesFactory;
import frc.robot.preferences.PreferencesManager;
import frc.robot.properties.PropertiesManager;
import frc.robot.sensors.ISensor;
import frc.robot.sensors.SensorsFactory;
import frc.robot.subsystems.ISubsystem;
import frc.robot.subsystems.SubsystemsFactory;
import frc.robot.telemetry.SchedulerProvider;
import frc.robot.telemetry.TelemetryManager;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(RobotContainer.class.getName());

   // The robot's subsystems and commands are defined here...
   // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

   // Replace with CommandPS4Controller or CommandJoystick if needed
   // private final CommandXboxController m_driverController = new
   // CommandXboxController(
   // OperatorConstants.kDriverControllerPort);

   //
   private final TelemetryManager tlmMgr;

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

      // Make sure Preferences are initialized
      intializePreferences();

      // Create telemetry manager
      TelemetryManager.constructInstance();
      tlmMgr = TelemetryManager.getInstance();

      // Create command manager
      SchedulerProvider.constructInstance();
      tlmMgr.addProvider(SchedulerProvider.getInstance());

      // Creat the OI "subsystem"
      OI.constructInstance();
      tlmMgr.addProvider(OI.getInstance());

      // Create all the modules
      modules = ModulesFactory.constructModules();
      ModeFollowers.getInstance().addAll(modules);

      // Create all the sensors
      sensors = SensorsFactory.constructSensors();
      ModeFollowers.getInstance().addAll(sensors);

      // Create all the subsystems
      subsystems = SubsystemsFactory.constructSubsystems();
      ModeFollowers.getInstance().addAll(subsystems);

      // Add the OI to mode followers
      // TODO: Determine if this needs to go after all the others?
      ModeFollowers.getInstance().add(OI.getInstance());

      // Create and place auto chooser on dashboard
      createAutoChooser();

      // Configure the trigger bindings
      configureBindings();

      logger.info("Created robot container");
   }

   private void intializePreferences() {
      // Reads and initializes all subsystems preferences
      PreferencesManager.constructInstance();

      logger.info("Preferences as initialized:");
      PreferencesManager.getInstance().logPreferences(logger);
   }

   private void initializeProperties() {
      // Reads and stores all the properties
      PropertiesManager.constructInstance();

      logger.info("Properties as initialized:");
      PropertiesManager.getInstance().logProperties(logger);
   }

   /**
    * Use this method to define your trigger->command mappings. Triggers can be
    * created via the {@link Trigger#Trigger(java.util.function.BooleanSupplier)}
    * constructor with an arbitrary predicate, or via the named factories in
    * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
    * for
    * {@link CommandXboxController
    * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
    * controllers or
    * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
    * joysticks}.
    */
   private void configureBindings() {
      // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
      // new Trigger(m_exampleSubsystem::exampleCondition)
      // .onTrue(new ExampleCommand(m_exampleSubsystem));

      // Schedule `exampleMethodCommand` when the Xbox controller's B button is
      // pressed, cancelling on release.
      // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
   }

   //
   public enum AutoSelection {
      // @formatter:off
      doNothing("doNothing"), 
      doSimpleBackup("doSimpleBackup"),
      doConeAndBackup("doConeAndBackup"),
      doFull("doFull");
      // @formatter:on

      private final String name;

      private AutoSelection(String name) {
         this.name = name;
      }

      public String getName() {
         return name;
      }
   }

   // Chooser for autonomous command from Dashboard
   private SendableChooser<AutoSelection> autoChooser;
   // Command that was selected
   private AutoSelection autoSelected;

   private void createAutoChooser() {
      autoChooser = new SendableChooser<>();

      // Default option is safety of "do nothing"
      autoChooser.setDefaultOption("Do Nothing", AutoSelection.doNothing);

      //
      autoChooser.addOption("Simple Backup", AutoSelection.doSimpleBackup);

      //
      autoChooser.addOption("Not Full Auto (place cone & backup)", AutoSelection.doConeAndBackup);
      //
      autoChooser.addOption("Full Auto (place cone & balance)", AutoSelection.doFull);

      SmartDashboard.putData("Auto Mode", autoChooser);
   }

   public boolean isRealAutoSelected() {
      return (autoChooser.getSelected() != AutoSelection.doNothing);
   }

   /**
    * Use this to pass the autonomous command to the main {@link Robot} class.
    *
    * @return the command to run in autonomous
    */
   public Command getAutonomousCommand() {
      autoSelected = autoChooser.getSelected();
      logger.info("auto command selected = {}", autoSelected);
      switch (autoSelected) {
         case doNothing:
            return new AutoDoNothing();

         case doSimpleBackup:
            return new DriveBackwardTimed(2.0);

         case doConeAndBackup:
            return new AutoDoNothing();

         case doFull:
            return new AutoDoNothing();

         default:
            return new AutoDoNothing();
      }
   }

}
