/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.ingester;

import org.slf4j.Logger;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.ingester.IngesterFactory;
import frc.robot.subsystems.ingester.IIngesterSubsystem;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
public class IngesterCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(IngesterCommandBase.class.getName());

   // Handle to our subsystem
   protected final IIngesterSubsystem subsys;

   public IngesterCommandBase() {
      logger.info("constructing {}", getName());

      subsys = IngesterFactory.getInstance();
      addRequirements(subsys);

      logger.info("constructed");
   }

}
