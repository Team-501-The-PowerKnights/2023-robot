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

public class TurretRotateToRearPositionCW extends TurretRotateToPosition {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(TurretRotateToRearPositionCW.class.getName());

   public TurretRotateToRearPositionCW() {
      super(TurretPosition.frontPosition); // FIXME
      logger.info("constructing {}", getName());
      logger.info("*** Not implemented yet");

      logger.info("constructed");
   }

}
