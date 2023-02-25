/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
class StubArmSubsystem extends BaseArmSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(StubArmSubsystem.class.getName());

   StubArmSubsystem() {
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
   public void rotateToPosition(ArmRotationPosition position) {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateToTarget(double target) {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateUp() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotateDown() {
      // TODO Auto-generated method stub

   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

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
