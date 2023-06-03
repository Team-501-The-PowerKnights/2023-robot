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
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.AutoDoNothing;

import riolog.PKLogger;

class StubRobot extends BaseRobot {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(StubRobot.class.getName());

   public StubRobot() {
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void configureBindings() {
      logger.info("configure");
      logger.info("configured");
   }

   //
   private enum AutoSelection {
      // @formatter:off
      doNothing("doNothing");
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

         default:
            return new AutoDoNothing();
      }
   }

}
