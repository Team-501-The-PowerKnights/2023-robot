/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armrotator;

import org.slf4j.Logger;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.armrotator.ArmRotatorFactory;
import frc.robot.subsystems.armrotator.IArmRotatorSubsystem;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
abstract class ArmRotatorCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmRotatorCommandBase.class.getName());

   // Handle to our subsystem
   protected final IArmRotatorSubsystem subsys;

   public ArmRotatorCommandBase() {
      logger.info("constructing {}", getName());

      subsys = ArmRotatorFactory.getInstance();
      addRequirements(subsys);

      logger.info("constructed");
   }

}
