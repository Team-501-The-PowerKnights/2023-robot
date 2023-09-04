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

public class TurretJoystickControl extends TurretManualCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(TurretJoystickControl.class.getName());

   public TurretJoystickControl(DoubleSupplier supplier) {
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
         subsys.stop();
      }

      // apply any adjustments (including sign to match subsystem convention)
      double speed = getCorrectedInput(input);
      logger.trace("input={}, corrected speed={}", speed);
      subsys.move(speed);
   }

   @Override
   public void end(boolean interrupted) {
      subsys.stop();

      super.end(interrupted);
   }

}
