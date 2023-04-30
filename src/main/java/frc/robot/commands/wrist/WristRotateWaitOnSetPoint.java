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

public class WristRotateWaitOnSetPoint extends WristCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(WristRotateWaitOnSetPoint.class.getName());

   public WristRotateWaitOnSetPoint() {
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.logPID() to {} called in firstExecution()");
      subsys.logPID();
   }

   @Override
   public boolean isFinished() {
      return subsys.onSetPoint();
   }

   @Override
   public void end(boolean interrupted) {
      logger.trace("subsys.logPID() to {} called in end()");
      subsys.logPID();

      super.end(interrupted);
   }

}
