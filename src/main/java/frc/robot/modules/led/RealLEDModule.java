/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import org.slf4j.Logger;

import riolog.PKLogger;

public class RealLEDModule extends AddressibleLEDModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RealLEDModule.class.getName());

   private static final int pwmPort = 0;
   private static final int ledLength = 60;

   public RealLEDModule() {
      super(pwmPort, ledLength);
      logger.info("constructing");

      logger.info("constructed");
   }

}
