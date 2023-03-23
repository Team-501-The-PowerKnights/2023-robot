/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import frc.robot.utils.PKColor8Bit;

import riolog.PKLogger;
import riolog.RioLogger;

public class ProtoLEDModule extends AddressibleLEDModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ProtoLEDModule.class.getName());

   private static final int pwmPort = 0;
   private static final int ledLength = 72;

   public static final PKColor8Bit red = new PKColor8Bit("red", 255, 0, 0);
   public static final PKColor8Bit orange = new PKColor8Bit("orange", 255, 165, 0);
   public static final PKColor8Bit green = new PKColor8Bit("green", 0, 255, 0);

   public static final PKColor8Bit yellow = new PKColor8Bit("yellow", 255, 255, 0);
   public static final PKColor8Bit purple = new PKColor8Bit("purple", 160, 32, 240);

   public ProtoLEDModule() {
      super(pwmPort, ledLength);
      logger.info("constructing");

      logger.info("constructed");
   }

}
