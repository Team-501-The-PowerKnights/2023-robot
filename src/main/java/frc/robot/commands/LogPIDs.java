/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands;

import org.slf4j.Logger;

import frc.robot.subsystems.arm.ArmFactory;
import frc.robot.subsystems.lift.LiftFactory;
import frc.robot.subsystems.turret.TurretFactory;

import riolog.PKLogger;

public class LogPIDs extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(LogPIDs.class.getName());

   public LogPIDs() {
      logger.info("constructing {}", getName());
   }

   @Override
   public void execute() {
      super.execute();

      // FIXME Make robot based for which subsystems.
      // ArmRotatorFactory.getInstance().logPID();

      // ArmExtenderFactory.getInstance().logPID();

      // WristFactory.getInstance().logPID();

      TurretFactory.getInstance().logPID();

      LiftFactory.getInstance().logPID();

      ArmFactory.getInstance().logPID();
   }

   @Override
   public boolean isFinished() {
      return true;
   }

}
