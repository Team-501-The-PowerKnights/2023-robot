/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
class StubArmExtenderSubsystem extends BaseArmExtenderSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(StubArmExtenderSubsystem.class.getName());

   StubArmExtenderSubsystem() {
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void disable() {
      setTlmPIDEnabled(false);
   }

   @Override
   public void stop() {
      setTlmPIDEnabled(false);
   }

   @Override
   public void extendToPosition(ArmExtensionPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      extendToTarget(target);
   }

   @Override
   public void extendToTarget(double target) {
      logger.debug("set PID target = {}", target);

      // Stub doesn't implement this
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void offsetTarget(double offset) {
      logger.trace("offset PID target = {}", offset);

      double target = getTlmPIDTarget();
      target += offset;
      extendToTarget(target);
   }

   @Override
   public void extend(double speed) {
      // TODO Auto-generated method stub

   }

}
