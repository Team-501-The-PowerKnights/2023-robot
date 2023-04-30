/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.wrist;

import org.slf4j.Logger;

import riolog.PKLogger;

public class WristDoNothing extends WristCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(WristDoNothing.class.getName());

   public WristDoNothing() {
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

}
