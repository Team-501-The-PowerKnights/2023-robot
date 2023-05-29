/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.lift;

import org.slf4j.Logger;

import riolog.PKLogger;

/**
 * DOCS: Add your docs here.
 */
public class StubLiftSubsystem extends BaseLiftSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(StubLiftSubsystem.class.getName());

   StubLiftSubsystem() {
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
   public void liftToPosition(LiftPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      liftToTarget(target);
   }

   @Override
   public void liftToTarget(double target) {
      logger.trace("set PID target = {}", target);

      // Stub doesn't implement this
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void offsetTarget(double offset) {
      logger.trace("offset PID target = {}", offset);

      double target = getTlmPIDTarget();
      target += offset;
      liftToTarget(target);
   }

   @Override
   public void lift(double speed) {
      // TODO Auto-generated method stub

   }

}
