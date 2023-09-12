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

public class ArmToHighPosition extends ArmGoToPosition {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmToHighPosition.class.getName());

   public ArmToHighPosition() {
      super(ArmPosition.highPosition);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

}
