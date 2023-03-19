/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
class SuitcaseGripperSubsystem extends BaseGripperSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(SuitcaseGripperSubsystem.class.getName());

   SuitcaseGripperSubsystem() {
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
      grip(idleSpeed);
   }

   @Override
   public void grip(double speed) {
      if (speed < 0) {
         speed = Math.max(speed, -maxOutSpeed);
      } else {
         speed = Math.min(speed, maxInSpeed);
      }
      // Suitcase doesn't implement this
      setTlmSpeed(speed);
   }

}
