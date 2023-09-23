/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.turret;

import org.slf4j.Logger;

import frc.robot.subsystems.turret.ITurretSubsystem.TurretPosition;

import riolog.PKLogger;

public class TurretRotateToRightPosition extends TurretRotateToPosition {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(TurretRotateToRightPosition.class.getName());

   public TurretRotateToRightPosition() {
      super(TurretPosition.rightPosition);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

}