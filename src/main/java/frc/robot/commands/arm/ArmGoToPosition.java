/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.arm;

import org.slf4j.Logger;

import frc.robot.subsystems.arm.IArmSubsystem.ArmPosition;

import riolog.PKLogger;

public class ArmGoToPosition extends ArmCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmGoToPosition.class.getName());

   /** Which of the fixed set point positions to move to */
   private final ArmPosition position;

   public ArmGoToPosition(ArmPosition position) {
      logger.info("constructing {}", getName());

      this.position = position;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.moveToPosition() to {} called in firstExecution()", position);

      subsys.moveToPosition(position);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
