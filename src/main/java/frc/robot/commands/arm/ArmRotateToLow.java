/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.arm;

import frc.robot.subsystems.arm.IArmSubsystem.ArmRotationPosition;

import riolog.PKLogger;
import riolog.RioLogger;

public class ArmRotateToLow extends ArmRotateToPosition {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmRotateToLow.class.getName());

   public ArmRotateToLow() {
      super(ArmRotationPosition.lowPosition);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("arm.rotateToPosition() to called in firstExecution()");
      this.firstExecution();
   }

}
