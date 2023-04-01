/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armextender;

import riolog.PKLogger;
import riolog.RioLogger;

public class ArmExtendToTarget extends ArmExtenderCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmExtendToTarget.class.getName());

   /** Value of set point to exxtend to */
   private final double target;

   public ArmExtendToTarget(double target) {
      logger.info("constructing {}", getName());

      this.target = target;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.extendToTarget() to {} called in firstExecution()", target);

      subsys.extendToTarget(target);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
