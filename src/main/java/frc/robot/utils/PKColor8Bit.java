/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.utils;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.util.Color8Bit;

import riolog.PKLogger;

/**
 * DOCS
 */
public class PKColor8Bit extends Color8Bit {

   /* Our classes logger */
   @SuppressWarnings("unused")
   private static final Logger logger = PKLogger.getLogger(PKStatus.class.getName());

   public static final PKColor8Bit blackRGB = new PKColor8Bit("black", 0, 0, 0);

   public static final PKColor8Bit redRGB = new PKColor8Bit("red", 255, 0, 0);
   public static final PKColor8Bit yellowRGB = new PKColor8Bit("yellow", 255, 255, 0);
   public static final PKColor8Bit orangeRGB = new PKColor8Bit("orange", 255, 165, 0);
   public static final PKColor8Bit greenRGB = new PKColor8Bit("green", 0, 255, 0);

   public static final PKColor8Bit blueRGB = new PKColor8Bit("blue", 0, 0, 255);

   @SuppressWarnings("unused")
   private final String name;

   private String stringRep;

   public PKColor8Bit(String name, int red, int green, int blue) {
      super(red, green, blue);

      this.name = name;

      stringRep = name + " (" + toHexString() + ")";
   }

   @Override
   public String toString() {
      return stringRep;
   }

}
