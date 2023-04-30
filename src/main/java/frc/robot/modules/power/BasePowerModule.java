/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.power;

import org.slf4j.Logger;

import frc.robot.modules.BaseModule;
import frc.robot.modules.ModuleNames;

import riolog.PKLogger;

abstract class BasePowerModule extends BaseModule implements IPowerModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BasePowerModule.class.getName());

   BasePowerModule() {
      super(ModuleNames.powerName);
      logger.info("constructing");

      logger.info("constructed");
   }

}
