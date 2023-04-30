/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armextender;

import org.slf4j.Logger;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.armextender.ArmExtenderFactory;
import frc.robot.subsystems.armextender.IArmExtenderSubsystem;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
abstract class ArmExtenderCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmExtenderCommandBase.class.getName());

   // Handle to our subsystem
   protected final IArmExtenderSubsystem subsys;

   public ArmExtenderCommandBase() {
      logger.info("constructing {}", getName());

      subsys = ArmExtenderFactory.getInstance();
      addRequirements(subsys);

      logger.info("constructed");
   }

}
