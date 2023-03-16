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
      // Stub doesn't implement this
   }

   @Override
   public void stop() {
      // Stub doesn't implement this
   }

   @Override
   public void extendToTarget(double target) {
      // TODO Auto-generated method stub

   }

   @Override
   public void extend() {
      // TODO Auto-generated method stub

   }

   @Override
   public void retract() {
      // TODO Auto-generated method stub

   }

   @Override
   public void extend(double speed) {
      // TODO Auto-generated method stub

   }

}
