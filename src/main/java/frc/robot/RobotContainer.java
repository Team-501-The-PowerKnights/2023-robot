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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.AutoDoNothing;
import frc.robot.commands.LogPIDs;
import frc.robot.commands.armextender.ArmExtendToInPosition;
import frc.robot.commands.armextender.ArmExtendToLowPosition;
import frc.robot.commands.armextender.ArmExtendToMidPosition;
import frc.robot.commands.armextender.ArmExtendToOverPosition;
import frc.robot.commands.armextender.ArmExtendWaitAtSetPoint;
import frc.robot.commands.armrotator.ArmOffsetRotationTarget;
import frc.robot.commands.armrotator.ArmRotateToHighPosition;
import frc.robot.commands.armrotator.ArmRotateToLowPosition;
import frc.robot.commands.armrotator.ArmRotateToMidPosition;
import frc.robot.commands.armrotator.ArmRotateToOverPosition;
import frc.robot.commands.armrotator.ArmRotateWaitAtSetPoint;
import frc.robot.commands.drive.DriveBackwardTimed;
import frc.robot.commands.drive.DriveBackwardToBalance;
import frc.robot.commands.drive.DriveBalance;
import frc.robot.commands.drive.DriveForwardTimed;
import frc.robot.commands.drive.DriveForwardToBalance;
import frc.robot.commands.gripper.GripperEject;
import frc.robot.commands.gripper.GripperStop;
import frc.robot.commands.wrist.WristRotateToOverPosition;
import frc.robot.commands.wrist.WristRotateToUpPosition;
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
      //
      doSimpleBackward("doSimpleBackward"),
      doSimpleForward("doSimpleForward"),
      //
      doBackwardToBalance("doBackwardToBalance"),
      doCommunityBackwardToBalance("doCommunityBackupToBalance"),
      doForwardToBalance("doForwardToBalance"),
      doCommunityForwardToBalance("doCommunityForwardToBalance"),
      //
      doMidConeAndBackward("doMidConeAndBackward"),
      doOverConeAndGoForward("doOverConeAndForward"),
      //
      doMidConeAndBackwardToBalance("doMidConeAndBackwardToBalance"),
      //
      doHighCubeAndBackward("doHighCubeAndBackward"),
      //
      doHighCubeAndBackwardToBalance("doHighCubeAndBackwardToBalance");
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

      /**
       * Drive
       */
      //
      autoChooser.addOption("Simple BACKWARD", AutoSelection.doSimpleBackward);
      //
      autoChooser.addOption("Simple FORWARD", AutoSelection.doSimpleForward);

      /**
       * Drive and Balance
       */
      //
      autoChooser.addOption("BACKWARD to Balance", AutoSelection.doBackwardToBalance);
      //
      autoChooser.addOption("Community BACKWARD to Balance", AutoSelection.doCommunityBackwardToBalance);
      //
      autoChooser.addOption("FORWARD to Balance", AutoSelection.doForwardToBalance);
      //
      autoChooser.addOption("Community FORWARD to Balance", AutoSelection.doCommunityForwardToBalance);

      /**
       * Cone and Drive
       */
      //
      autoChooser.addOption("Place Mid CONE & BACKWARD", AutoSelection.doMidConeAndBackward);
      //
      autoChooser.addOption("Place Over CONE & Go FORWARD", AutoSelection.doOverConeAndGoForward);

      /**
       * Cone and Balance
       */
      //
      autoChooser.addOption("Place Mid CONE & BACKWARD to Balance", AutoSelection.doMidConeAndBackwardToBalance);

      /**
       * Cube and Drive
       */
      //
      autoChooser.addOption("Place High CUBE & BACKWARD", AutoSelection.doHighCubeAndBackward);

      /**
       * Cube and Balance
       */
      //
      autoChooser.addOption("Place High CUBE & BACKWARD to Balance", AutoSelection.doHighCubeAndBackwardToBalance);

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

         case doSimpleBackward:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToLowPosition(),
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5),
                  new DriveBackwardTimed(3.4, -0.60)  // 3.0
              );
            // @formatter:on

         case doSimpleForward:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new ArmRotateToLowPosition(),
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5),
                  new DriveForwardTimed(3.4, -0.60)  // 3.0
              );
            // @formatter:on

         case doBackwardToBalance:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToHighPosition(), 
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5), 
                  new LogPIDs(),
                  new DriveBackwardToBalance(2.12, -0.60), // 2.25
                  new DriveBalance()
              );
            // @formatter:on

         case doCommunityBackwardToBalance:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToHighPosition(), 
                  new ArmExtendToLowPosition(),
                  new WaitCommand(0.5), 
                  new LogPIDs(),
                  new DriveBackwardToBalance(3.9, -0.60),  // 3.5
                  new DriveForwardTimed(3.9, 0.45),  // 3.5
                  new DriveBalance()
              );
            // @formatter:on

         case doForwardToBalance:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToMidPosition(), 
                  new ArmExtendToLowPosition(),
                  new WaitCommand(1.0), 
                  new LogPIDs(),
                  new DriveForwardToBalance(2.12, 0.60), // 2.25
                  new DriveBalance()
              );
            // @formatter:on

         case doCommunityForwardToBalance:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  new GripperEject(),
                  new ArmRotateToMidPosition(), 
                  new ArmExtendToLowPosition(),
                  new WaitCommand(1.0), 
                  new LogPIDs(),
                  new DriveForwardToBalance(3.9, 0.60),  // 3.6
                  new DriveBackwardTimed(3.8, -0.45),  // 3.5
                  new DriveBalance()
              );
            // @formatter:on

         case doMidConeAndBackward:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToMidPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(-4), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop(),
                  new DriveBackwardTimed(3.5, -0.60)
               );
            // @formatter:on

         case doOverConeAndGoForward:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmRotateToHighPosition(), new ArmRotateWaitAtSetPoint()),
                  new WristRotateToOverPosition(),
                  new SequentialCommandGroup(new ArmRotateToOverPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToOverPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(3), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop(),
                  new ArmRotateToHighPosition(),
                  new WristRotateToUpPosition(),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new DriveForwardTimed(3.5, 0.60)
               );
            // @formatter:on

         case doMidConeAndBackwardToBalance:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToMidPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(-4), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop(),
                  new DriveBackwardToBalance(2.12, -0.60),
                  new DriveBalance()
               );
            // @formatter:on

         case doHighCubeAndBackward:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToMidPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop(),
                  new DriveBackwardTimed(3.5, -0.60)
               );
            // @formatter:on

         case doHighCubeAndBackwardToBalance:
            // @formatter:off
            return
               new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToMidPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop(),
                  new DriveBackwardToBalance(2.12, -0.60),
                  new DriveBalance()
               );
            // @formatter:on

         default:
            return new AutoDoNothing();
      }
   }

}
