/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.utils;

import edu.wpi.first.wpilibj.util.Color8Bit;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS
 */
public class PKColor8Bit extends Color8Bit {

   /* Our classes logger */
   @SuppressWarnings("unused")
   private static final PKLogger logger = RioLogger.getLogger(PKStatus.class.getName());

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
