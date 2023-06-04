/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import org.slf4j.Logger;

import riolog.PKLogger;

/**
 * This class implements the Operator's gamepad.
 * <p>
 * See <code>control_mode.md</code> for documentation of how configured and
 * used.
 */
abstract class BaseOperatorGamepad extends F310Gamepad implements IOperatorGamepad {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BaseOperatorGamepad.class.getName());

   public BaseOperatorGamepad(String name, int port) {
      super(name, port);
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void configureButtonBindings() {
      logger.error("no one should be calling this at the moment");
   }

}
