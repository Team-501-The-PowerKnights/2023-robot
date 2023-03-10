/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.gripper;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.gripper.GripperFactory;
import frc.robot.subsystems.gripper.IGripperSubsystem;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * Add your docs here.
 */
abstract class GripperCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(GripperCommandBase.class.getName());

   // Handle to our subsystem
   protected final IGripperSubsystem gripper;

   public GripperCommandBase() {
      logger.info("constructing {}", getName());

      gripper = GripperFactory.getInstance();
      addRequirements(gripper);

      logger.info("constructed");
   }

}
