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

public class LiftNudgeTarget extends LiftManualCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(LiftNudgeTarget.class.getName());

   public LiftNudgeTarget(DoubleSupplier supplier) {
      super(supplier);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   public void execute() {
      super.execute();

      // value directly from the gamepad
      double input = supplier.getAsDouble();

      if (noInput(input)) {
         return;
      }

      // apply any adjustments (including sign to match subsystem convention)
      double offset = getCorrectedInput(input);
      logger.trace("input={}, corrected offset={}", input, offset);
      subsys.offsetTarget(offset);
   }

}
