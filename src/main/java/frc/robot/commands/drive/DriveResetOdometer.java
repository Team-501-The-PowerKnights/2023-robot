/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import org.slf4j.Logger;

import riolog.PKLogger;

public class DriveResetOdometer extends DriveCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(DriveResetOdometer.class.getName());

   public DriveResetOdometer() {
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   public void execute() {
      super.execute();
   }

   @Override
   protected void firstExecution() {
      logger.trace("drive.resetOdometer() called in firstExecution()");

      drive.resetOdometer();
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
