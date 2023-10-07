/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.turret;

import org.slf4j.Logger;

import riolog.PKLogger;

public class TurretGoToTarget extends TurretCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(TurretGoToTarget.class.getName());

   /** New target to move to */
   private final double target;

   public TurretGoToTarget(double target) {
      logger.info("constructing {}", getName());

      this.target = target;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.moveToTarget() to {} called in firstExecution()", target);

      subsys.moveToTarget(target);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
