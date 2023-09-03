/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.arm;

import org.slf4j.Logger;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.arm.ArmFactory;
import frc.robot.subsystems.arm.IArmSubsystem;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
abstract class ArmCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmCommandBase.class.getName());

   // Handle to our subsystem
   protected final IArmSubsystem subsys;

   public ArmCommandBase() {
      logger.info("constructing {}", getName());

      subsys = ArmFactory.getInstance();
      addRequirements(subsys);

      logger.info("constructed");
   }

}
