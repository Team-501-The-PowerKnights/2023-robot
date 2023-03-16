/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armrotator;

import riolog.PKLogger;
import riolog.RioLogger;

public class ArmRotatorDoNothing extends ArmRotatorCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmRotatorDoNothing.class.getName());

   public ArmRotatorDoNothing() {
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

}
