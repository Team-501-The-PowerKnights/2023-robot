/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import org.slf4j.Logger;

import frc.robot.utils.PKColor8Bit;

import riolog.PKLogger;

public class StubLEDModule extends BaseLEDModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(StubLEDModule.class.getName());

   public StubLEDModule() {
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void enable() {
      // Stub doesn't implement this
   }

   @Override
   public void setRGB(int r, int g, int b) {
      // Stub doesn't implement this
   }

   @Override
   public void setColor(PKColor8Bit color) {
      // Stub doesn't implement this
   }

}
