/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands;

import org.slf4j.Logger;

import riolog.PKLogger;

public class DebugPrint extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(DebugPrint.class.getName());

   //
   private final String message;

   public DebugPrint(String message) {
      logger.info("constructing {}", getName());

      this.message = message;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("{} called in firstExecution()", message);
   }

}
