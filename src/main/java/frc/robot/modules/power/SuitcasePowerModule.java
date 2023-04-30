/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.power;

import org.slf4j.Logger;

import riolog.PKLogger;

/**
 * This class provides the implementation of the {@link IPowerModule} for the
 * Suitcase-Bot.
 */
class SuitcasePowerModule extends PDHPowerModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SuitcasePowerModule.class.getName());

   public SuitcasePowerModule() {
      logger.info("constructing");

      logger.info("constructed");
   }

}
