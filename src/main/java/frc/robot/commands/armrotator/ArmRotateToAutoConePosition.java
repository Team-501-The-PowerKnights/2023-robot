/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armrotator;

import org.slf4j.Logger;

import frc.robot.subsystems.armrotator.IArmRotatorSubsystem.ArmRotationPosition;

import riolog.PKLogger;

public class ArmRotateToAutoConePosition extends ArmRotateToPosition {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmRotateToAutoConePosition.class.getName());

   public ArmRotateToAutoConePosition() {
      super(ArmRotationPosition.autoConePosition);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

}