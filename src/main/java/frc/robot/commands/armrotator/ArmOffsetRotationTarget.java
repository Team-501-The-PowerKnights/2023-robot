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

public class ArmOffsetRotationTarget extends ArmRotatorCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmOffsetRotationTarget.class.getName());

   private final double offset;

   public ArmOffsetRotationTarget(double offset) {
      logger.info("constructing {}", getName());

      this.offset = offset;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.offsetTarget() to {} called in firstExecution()", offset);

      subsys.offsetTarget(offset);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
