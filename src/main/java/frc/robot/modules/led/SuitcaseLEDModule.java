/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import riolog.PKLogger;
import riolog.RioLogger;

public class SuitcaseLEDModule extends AddressibleLEDModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseLEDModule.class.getName());

   private static final int pwmPort = 0;
   private static final int ledLength = 16;

   public SuitcaseLEDModule() {
      super(pwmPort, ledLength);
      logger.info("constructing");

      logger.info("constructed");
   }

}
