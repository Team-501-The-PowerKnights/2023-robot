/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import org.slf4j.Logger;

import frc.robot.OI;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
abstract class DriveOICommandBase extends DriveCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(DriveOICommandBase.class.getName());

   // Handle to the OI
   protected OI oi;

   public DriveOICommandBase() {
      logger.info("constructing {}", getName());

      oi = OI.getInstance();

      logger.info("constructed");
   }

}
