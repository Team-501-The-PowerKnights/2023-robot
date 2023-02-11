/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

import frc.robot.subsystems.ISubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IArmSubsystem extends ISubsystem {

   /**
    * Stop the arm from any motion it may have been running under.
    */
   public void stop();

   /**
    * DOCS: Add your docs here.
    */
   public enum armRotationSetPoint {
      position_1, position_2, position_3
   }

   /**
    * Rotate the arm to the specified set point and hold it
    * there (using a PID).
    */
   public void rotateToSetPoint(armRotationSetPoint point);

   /**
    * Rotate the arm (away from the ground) or "backwards" (towards
    * the back of the robot).
    */
   public void rotateUp();

   /**
    * Rotate the arm down (towards the ground) or "forward" (towards
    * the front of the robot).
    */
   public void rotateDown();

   /**
    * Rotate the arm under 'manual' control.
    *
    * @param speed
    *           speed to rotate at ("+" is up, "-" is down)
    */
   public void rotate(double speed);

   /**
    * Extend the arm out.
    */
   public void extend();

   /**
    * Retract the arm in.
    */
   public void retract();

}