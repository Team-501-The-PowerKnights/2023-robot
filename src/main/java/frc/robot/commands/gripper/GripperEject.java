/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.gripper;

import org.slf4j.Logger;

import riolog.PKLogger;

public class GripperEject extends GripperCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(GripperEject.class.getName());

   public GripperEject() {
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   public void execute() {
      super.execute();
   }

   @Override
   protected void firstExecution() {
      logger.trace("gripper.pushOut() called in firstExecution()");

      subsys.pushOut();
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}