/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.wrist;

import org.slf4j.Logger;

import riolog.PKLogger;

/**
 * DOCS: Add your docs here.
 */
class SuitcaseWristSubsystem extends BaseWristSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SuitcaseWristSubsystem.class.getName());

   SuitcaseWristSubsystem() {
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
   public void rotateToPosition(WristPosition position) {
      logger.debug("position = {}", position);

      double target = position.get();
      rotateToTarget(target);
   }

   @Override
   public void rotateToTarget(double target) {
      logger.trace("set PID target = {}", target);

      // Suitcase doesn't implement this
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void rotateCW() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateCCW() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

   }

}
