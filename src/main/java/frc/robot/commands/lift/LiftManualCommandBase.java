/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import org.slf4j.Logger;

import java.util.function.DoubleSupplier;

import riolog.PKLogger;

abstract class LiftManualCommandBase extends LiftCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(LiftManualCommandBase.class.getName());

   //
   private static final double zeroEpsilon = 0.01;
   //
   private static final double scale = 0.15;

   protected final DoubleSupplier supplier;

   public LiftManualCommandBase(DoubleSupplier supplier) {
      logger.info("constructing {}", getName());

      this.supplier = supplier;

      logger.info("constructed");
   }

   protected boolean noInput(double input) {
      return isZero(input, zeroEpsilon);
   }

   protected double getCorrectedInput(double input) {
      // Scale to make less responsive
      // Gamepad already reversed
      return (input * scale);
   }

}
