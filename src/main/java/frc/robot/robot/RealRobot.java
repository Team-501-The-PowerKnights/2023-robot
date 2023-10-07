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
import frc.robot.commands.drive.DriveBackwardTimed;
import frc.robot.commands.drive.DriveForwardTimed;
import frc.robot.commands.gripper.GripperEject;
import riolog.PKLogger;

class RealRobot extends BaseRobot {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RealRobot.class.getName());

   public RealRobot() {
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public boolean hasDrive() {
      return true;
   };

   @Override
   public boolean hasGripper() {
      return true;
   };

   @Override
   public boolean hasTurret() {
      return true;
   };

   @Override
   public boolean hasLift() {
      return true;
   };

   @Override
   public boolean hasArm() {
      return true;
   };

   @Override
   public void configureBindings() {
      logger.info("configure");
      logger.info("configured");
   }

   //
   private enum AutoSelection {
      // @formatter:off
      doNothing("doNothing"),
      //
      doSimpleBackward("doSimpleBackward"),
      doSimpleForward("doSimpleForward");
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
                  // Any Arm or Lift actions? Proto had rotate & extend
                  new WaitCommand(0.5),
                  new DriveBackwardTimed(3.4, -0.60)
              );
            // @formatter:on

         case doSimpleForward:
            // @formatter:off
            return
              new SequentialCommandGroup(
                  // Any Arm or Lift actions? Proto had rotate & extend
                  new WaitCommand(0.5),
                  new DriveForwardTimed(3.4, -0.60)
              );
            // @formatter:on

         default:
            return new AutoDoNothing();
      }
   }

}
