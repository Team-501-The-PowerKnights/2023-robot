/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.ingester;

import org.slf4j.Logger;

import riolog.PKLogger;

/**
 * DOCS: Add your docs here.
 */
class SuitcaseIngesterSubsystem extends BaseIngesterSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SuitcaseIngesterSubsystem.class.getName());

   SuitcaseIngesterSubsystem() {
      super();
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void disable() {
      // Suitcase doesn't implement this
      setTlmSpeed(0);
   }

   @Override
   public void stop() {
      // Suitcase doesn't implement this
      setTlmSpeed(0);
   }

   @Override
   public void pullIn() {
      // TODO Auto-generated method stub
   }

   @Override
   public void pushOut() {
      // TODO Auto-generated method stub
   }

   @Override
   public void idleIn() {
      ingest(idleSpeed);
   }

   @Override
   public void ingest(double speed) {
      if (speed < 0) {
         speed = Math.max(speed, -maxOutSpeed);
      } else {
         speed = Math.min(speed, maxInSpeed);
      }

      // Reduce the rate for frame one
      if (Math.abs(speed) < 0.75) {
         speed = 0.35;
         speed *= (speed < 0) ? -1 : 1;
      }

      // Suitcase doesn't implement this
      setTlmSpeed(speed);
   }

}
