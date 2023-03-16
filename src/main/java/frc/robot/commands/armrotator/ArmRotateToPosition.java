/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armrotator;

import frc.robot.subsystems.armrotator.IArmRotatorSubsystem.ArmRotationPosition;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * This class implements an abstract base class suitable for building a
 * <code>Command<code> to rotate the arm to one of the pre-canned, fixed
 * locations. It is implemented as a <i>one-shot</code> which means it
 * makes the call to set the PID reference during the
 * <code>firstExecution()</code> method, and then does nothing for the
 * remainder of the subsequent calls to <code>execute()</code>.
 */
abstract class ArmRotateToPosition extends ArmRotatorCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmRotateToPosition.class.getName());

   /** Which of the fixed set point positions to rotate to */
   private final ArmRotationPosition position;

   public ArmRotateToPosition(ArmRotationPosition position) {
      logger.info("constructing {}", getName());

      this.position = position;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("arm.rotateToPosition() to {} called in firstExecution()", position);

      subsys.rotateToPosition(position);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
