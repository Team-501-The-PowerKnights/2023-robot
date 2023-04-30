/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.gripper;

import java.util.function.DoubleSupplier;

import org.slf4j.Logger;

import riolog.PKLogger;

public class GripperGrip extends GripperCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(GripperGrip.class.getName());

   private final DoubleSupplier supplier;

   public GripperGrip(DoubleSupplier supplier) {
      logger.info("constructing {}", getName());

      this.supplier = supplier;

      logger.info("constructed");
   }

   @Override
   public void execute() {
      double input = supplier.getAsDouble();

      subsys.grip(input);
   }

}
