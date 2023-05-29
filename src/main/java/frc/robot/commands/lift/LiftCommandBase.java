/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import org.slf4j.Logger;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.lift.LiftFactory;
import frc.robot.subsystems.lift.ILiftSubsystem;

import riolog.PKLogger;

abstract public class LiftCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(LiftCommandBase.class.getName());

   // Handle to our subsystem
   protected final ILiftSubsystem subsys;

   public LiftCommandBase() {
      logger.info("constructing {}", getName());

      subsys = LiftFactory.getInstance();
      addRequirements(subsys);

      logger.info("constructed");
   }

}
