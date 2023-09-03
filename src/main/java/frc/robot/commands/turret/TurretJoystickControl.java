/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.turret;

import org.slf4j.Logger;

import java.util.function.DoubleSupplier;

import riolog.PKLogger;

public class TurretJoystickControl extends TurretCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(TurretJoystickControl.class.getName());

   private static final double scale = 0.15;

   private final DoubleSupplier supplier;

   public TurretJoystickControl(DoubleSupplier supplier) {
      logger.info("constructing {}", getName());

      this.supplier = supplier;

      logger.info("constructed");
   }

   @Override
   public void execute() {
      double input = supplier.getAsDouble();

      // If close enough to zero we stop motion (gamepad doesn't zero)
      if (isZero(input, 0.01)) {
         subsys.stop();
      }

      // Scale to make less responsive
      // Reverse sign to make gamepad match convention
      input = negate(input * scale);

      subsys.move(input);
   }

}
