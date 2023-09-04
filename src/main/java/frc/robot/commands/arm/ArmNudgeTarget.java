/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.arm;

import org.slf4j.Logger;

import java.util.function.DoubleSupplier;

import riolog.PKLogger;

public class ArmNudgeTarget extends ArmManualCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmNudgeTarget.class.getName());

   public ArmNudgeTarget(DoubleSupplier supplier) {
      super(supplier);
      logger.info("constructing {}", getName());

      logger.info("constructed");
   }

   /**
    * Moves the Arm in response to the values being provided by
    * the Operator input device. The <code>offset</code> to the PID
    * set point is provided as a double, where '+' moves it CW and
    * '-' moves it CCW.
    * 
    * @see edu.wpi.first.wpilibj2.command.Command#execute()
    */
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
