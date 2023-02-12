/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.gripper;

import riolog.PKLogger;
import riolog.RioLogger;

public class GripperClose extends GripperCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(GripperClose.class.getName());

   public GripperClose() {
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   public void execute() {
      super.execute();
   }

   @Override
   protected void firstExecution() {
      logger.trace("gripper.close() called in firstExecution()");
      gripper.close();
   }

}
