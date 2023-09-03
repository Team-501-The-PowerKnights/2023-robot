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

public class LiftJoystickControl extends LiftManualCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(LiftJoystickControl.class.getName());

   public LiftJoystickControl(DoubleSupplier supplier) {
      super(supplier);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   @Override
   public void execute() {
      double input = supplier.getAsDouble();

      if (noInput(input)) {
         subsys.stop();
      }

      subsys.move(getCorrectedInput(input));
   }

}
