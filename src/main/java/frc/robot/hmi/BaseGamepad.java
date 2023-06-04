/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;

import riolog.PKLogger;

abstract class BaseGamepad {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BaseGamepad.class.getName());

   /** Our gamepad's name **/
   protected final String myName;

   /** Our joysick (as old, non-command way) */
   protected final GenericHID stick;
   /** Our joystick (command-based variant) */
   protected final CommandGenericHID cmdStick;

   protected BaseGamepad(String name, int port) {
      logger.info("constructing {} for {}", name, port);

      myName = name;

      // Create new command based way ...
      cmdStick = new CommandGenericHID(port);
      // ... but get old way for backwards compatibility
      stick = cmdStick.getHID();

      logger.info("constructed");
   }

   /**
    * 
    * Lifted from:
    * https://www.chiefdelphi.com/t/how-do-i-program-a-joystick-deadband/122625
    * 
    * @param value
    * @param cutOff
    * @return
    */
   protected final double deadBand(final double value, final double cutOff) {
      double retValue;
      if (value < cutOff && value > (cutOff * (-1))) {
         retValue = 0;
      } else {
         retValue = (value - (Math.abs(value) / value * cutOff)) / (1 - cutOff);
      }
      return retValue;
   }

}