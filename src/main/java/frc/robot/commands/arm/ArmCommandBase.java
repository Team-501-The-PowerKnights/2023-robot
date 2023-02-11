/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.arm;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.arm.ArmFactory;
import frc.robot.subsystems.arm.IArmSubsystem;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * Add your docs here.
 */
abstract class ArmCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmCommandBase.class.getName());

   // Handle to our subsystem
   protected final IArmSubsystem arm;

   public ArmCommandBase() {
      logger.info("constructing {}", getName());

      arm = ArmFactory.getInstance();
      addRequirements(arm);

      logger.info("constructed");
   }

}
