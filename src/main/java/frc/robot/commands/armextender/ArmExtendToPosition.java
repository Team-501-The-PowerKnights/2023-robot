/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armextender;

import org.slf4j.Logger;

import frc.robot.subsystems.armextender.IArmExtenderSubsystem.ArmExtensionPosition;

import riolog.PKLogger;

/**
 * This class implements an abstract base class suitable for building a
 * <code>Command<code> to extend the arm to one of the pre-canned, fixed
 * locations. It is implemented as a <i>one-shot</code> which means it
 * makes the call to set the PID reference during the
 * <code>firstExecution()</code> method, and then does nothing for the
 * remainder of the subsequent calls to <code>execute()</code>.
 */
public class ArmExtendToPosition extends ArmExtenderCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmExtendToPosition.class.getName());

   /** Which of the fixed set point positions to rotate to */
   private final ArmExtensionPosition position;

   public ArmExtendToPosition(ArmExtensionPosition position) {
      logger.info("constructing {}", getName());

      this.position = position;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("subsys.extendToPosition() to {} called in firstExecution()", position);

      subsys.extendToPosition(position);
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
