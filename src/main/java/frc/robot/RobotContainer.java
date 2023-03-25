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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.AutoDoNothing;
import frc.robot.commands.LogPIDs;
import frc.robot.commands.armextender.ArmExtendToAutoConePosition;
import frc.robot.commands.armextender.ArmExtendToLowPosition;
import frc.robot.commands.armrotator.ArmRotateToAutoConePosition;
import frc.robot.commands.armrotator.ArmRotateToLowPosition;
import frc.robot.commands.drive.DriveBackwardTimed;
import frc.robot.commands.drive.DriveBackwardToBalance;
import frc.robot.commands.drive.DriveBalance;
import frc.robot.commands.drive.DriveForwardTimed;
import frc.robot.commands.gripper.GripperEject;
import frc.robot.commands.gripper.GripperStop;
import frc.robot.commands.wrist.WristRotateToOverPosition;
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
      doBackupToBalance("doBackupToBalance"),
      doCommunityToBalance("doCommunityToBalance"),
      doCone("doCone"),
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
      autoChooser.addOption("Do Nothing", AutoSelection.doNothing);

      //
      autoChooser.addOption("Simple Backup", AutoSelection.doSimpleBackup);
      //
      autoChooser.setDefaultOption("Backup to Balance", AutoSelection.doBackupToBalance);
      //
      autoChooser.addOption("Community to Balance", AutoSelection.doCommunityToBalance);

      //
      autoChooser.addOption("(me) Not Full (mid cone)", AutoSelection.doCone);
      //
      autoChooser.addOption("(me) Not Full (mid cone & backup)", AutoSelection.doConeAndBackup);
      //
      autoChooser.addOption("Full Auto", AutoSelection.doFull);

      //

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
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new ArmRotateToLowPosition(),
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5),
                  new DriveBackwardTimed(3.0, -0.60)
              );
            // @formatter:on

         case doBackupToBalance:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToLowPosition(), 
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5), 
                  new LogPIDs(),
                  new DriveBackwardToBalance(2.12, -0.60), // 2.25
                  new DriveBalance()
              );
            // @formatter:on

         case doCommunityToBalance:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToLowPosition(), 
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5), 
                  new LogPIDs(),
                  new DriveBackwardToBalance(3.5, -0.60),
                  new DriveForwardTimed(3.5, 0.45),
                  new DriveBalance()
              );
            // @formatter:on

         case doCone:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToAutoConePosition(), new WaitCommand(1)),
                  new SequentialCommandGroup(new WristRotateToOverPosition(), new WaitCommand(0.5)),
                  new SequentialCommandGroup(new ArmExtendToAutoConePosition(), new WaitCommand(3.5)), // 4
                  new LogPIDs(),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.5)),
                  new ParallelCommandGroup(
                     new SequentialCommandGroup(new ArmExtendToLowPosition(), new WaitCommand(3)),
                     new SequentialCommandGroup(new GripperStop(), new WaitCommand(0.1))
                  )
               );
            // @formatter:on

         case doConeAndBackup:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToAutoConePosition(), new WaitCommand(1)),
                  new SequentialCommandGroup(new WristRotateToOverPosition(), new WaitCommand(0.5)),
                  new SequentialCommandGroup(new ArmExtendToAutoConePosition(), new WaitCommand(3.5)), // 4
                  new LogPIDs(),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.5)),
                  new ParallelCommandGroup(
                     new SequentialCommandGroup(new ArmExtendToLowPosition(), new WaitCommand(3)),
                     new SequentialCommandGroup(new GripperStop(), new WaitCommand(0.1))
                  ),
                  new DriveBackwardToBalance(2.24, -0.60)
               );
            // @formatter:on

         case doFull:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToAutoConePosition(), new WaitCommand(1)),
                  new SequentialCommandGroup(new WristRotateToOverPosition(), new WaitCommand(0.5)),
                  new SequentialCommandGroup(new ArmExtendToAutoConePosition(), new WaitCommand(3.5)), // 4
                  new LogPIDs(),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.5)),
                  new ParallelCommandGroup(
                     new SequentialCommandGroup(new ArmExtendToLowPosition(), new WaitCommand(3)),
                     new SequentialCommandGroup(new GripperStop(), new WaitCommand(0.1))
                  ),
                  new LogPIDs(),
                  new DriveBackwardToBalance(2.12, -0.60), // 2.25
                  new DriveBalance()
               );
            // @formatter:on

         default:
            return new AutoDoNothing();
      }
   }

}
