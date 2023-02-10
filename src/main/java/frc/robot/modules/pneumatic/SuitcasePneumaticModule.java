/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved. */
/* Open Source Software - may be modified and shared by other FRC teams */
/* under the terms of the Team501 license. The code must be accompanied */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project. */
/*-----------------------------------------------------------------------*/

package frc.robot.modules.pneumatic;

import riolog.PKLogger;
import riolog.RioLogger;

class SuitcasePneumaticModule extends PCHPneumaticModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcasePneumaticModule.class.getName());

   public SuitcasePneumaticModule() {
      logger.info("constructing");

      logger.info("constructed");
   }

}
