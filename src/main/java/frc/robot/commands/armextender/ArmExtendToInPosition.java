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

public class ArmExtendToInPosition extends ArmExtendToPosition {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmExtendToInPosition.class.getName());

   public ArmExtendToInPosition() {
      super(ArmExtensionPosition.inPosition);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

}
