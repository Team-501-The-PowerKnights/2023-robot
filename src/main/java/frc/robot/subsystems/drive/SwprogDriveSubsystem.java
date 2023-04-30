/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import org.slf4j.Logger;

import riolog.PKLogger;

/**
 * This class implements the <code>DriveSubsystem</code> for the
 * <i>Swprog-Bot<i>.
 */
class SwprogDriveSubsystem extends CANSparkMaxDriveSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SwprogDriveSubsystem.class.getName());

   SwprogDriveSubsystem() {
      logger.info("constructing");

      logger.info("constructed");
   }

}
