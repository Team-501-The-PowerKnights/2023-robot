/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.robot;

import org.slf4j.Logger;

import riolog.PKLogger;

abstract class BaseRobot implements IRobot {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BaseRobot.class.getName());

   BaseRobot() {
      logger.info("constructing");

      logger.info("constructed");
   }

}
