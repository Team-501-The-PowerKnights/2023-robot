/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.robot;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

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

import riolog.PKLogger;

class ProtoRobot extends BaseRobot {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ProtoRobot.class.getName());

   public ProtoRobot() {
      logger.info("constructing");

      logger.info("constructed");
   }

   //
   private enum AutoSelection {
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

      @SuppressWarnings("unused")
      public String getName() {
         return name;
      }
   }

   @Override
   public void configureBindings() {
      logger.info("configure");
      logger.info("configured");
   }

   // Chooser for autonomous command from Dashboard
   private SendableChooser<AutoSelection> autoChooser;
   // Command that was selected
   private AutoSelection autoSelected;

   @Override
   public void createAutoChooser() {
      logger.info("creating");

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

      SmartDashboard.putData("Auto Chooser", autoChooser);

      logger.info("created");
   }

   @Override
   public boolean isRealAutoSelected() {
      return (autoChooser.getSelected() != AutoSelection.doNothing);
   }

   @Override
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
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(-5), new ArmRotateWaitAtSetPoint()),
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
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(-5), new ArmRotateWaitAtSetPoint()),
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
