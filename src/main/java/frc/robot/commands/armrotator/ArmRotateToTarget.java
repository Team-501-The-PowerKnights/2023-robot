/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.armrotator;

import java.util.function.DoubleSupplier;

import riolog.PKLogger;
import riolog.RioLogger;

public class ArmRotateToTarget extends ArmRotatorCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmRotateToTarget.class.getName());

   private final DoubleSupplier supplier;

   public ArmRotateToTarget(DoubleSupplier supplier) {
      logger.info("constructing {}", getName());

      this.supplier = supplier;

      logger.info("constructed");
   }

   @Override
   protected void firstExecution() {
      logger.trace("calling firstExecution()");

   }

   @Override
   public void execute() {
      double input = supplier.getAsDouble();
      if (input == 0) {
         return;
      } else {
         subsys.rotateToTarget(input);
      }
   }

}
