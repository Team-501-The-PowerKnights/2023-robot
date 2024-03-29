/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armextender;

import org.slf4j.Logger;

import java.util.function.DoubleSupplier;

import riolog.PKLogger;

public class ArmNudgeExtensionTarget extends ArmExtenderCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmNudgeExtensionTarget.class.getName());

   private final DoubleSupplier supplier;

   public ArmNudgeExtensionTarget(DoubleSupplier supplier) {
      logger.info("constructing {}", getName());

      this.supplier = supplier;

      logger.info("constructed");
   }

   @Override
   public void execute() {
      double input = supplier.getAsDouble();
      if (input == 0) {
         return;
      }

      // Scale by 15% and change sign
      input *= -0.15;
      subsys.offsetTarget(input);
   }

}
