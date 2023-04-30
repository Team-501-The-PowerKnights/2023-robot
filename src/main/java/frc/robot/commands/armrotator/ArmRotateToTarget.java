/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armrotator;

import org.slf4j.Logger;

import riolog.PKLogger;

public class ArmRotateToTarget extends ArmRotatorCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmRotateToTarget.class.getName());

   /** Value of set point to rotate to */
   private final double target;

   public ArmRotateToTarget(double target) {
      logger.info("constructing {}", getName());

      this.target = target;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.rotateToTarget() to {} called in firstExecution()", target);

      subsys.rotateToTarget(target);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
