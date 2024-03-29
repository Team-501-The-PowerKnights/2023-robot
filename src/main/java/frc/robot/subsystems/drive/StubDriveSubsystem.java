/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import org.slf4j.Logger;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import riolog.PKLogger;

class StubDriveSubsystem extends BaseDriveSubsystem {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(StubDriveSubsystem.class.getName());

   StubDriveSubsystem() {
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
   public void setBrake(boolean brakeOn) {
      // Stub doesn't implement this
   }

   @Override
   public void toggleBrake() {
      // Stub doesn't implement this
   }

   @Override
   public void drive(double hmiSpeed, double hmiTurn) {
      // Stub doesn't implement this
   }

   @Override
   public RamseteCommand followTrajectory(Trajectory trajectory) {
      // Stub doesn't implement this
      return null;
   }

   @Override
   public void swap() {
      // Stub doesn't implement this
   }

   @Override
   public void logOdometer() {
      // Stub doesn't implement this
   }

   @Override
   public void resetOdometer() {
      // Stub doesn't implement this
   }

   @Override
   public double getOdometerClicks() {
      // Stub doesn't implement this
      return 0;
   }

   @Override
   public double getSpeedometerClicks() {
      // Stub doesn't implement this
      return 0;
   }

   @Override
   public void setSpeed(int canID, double speed) {
      // Stub doesn't implement this
   }

}
