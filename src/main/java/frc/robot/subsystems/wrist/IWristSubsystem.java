/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.wrist;

import frc.robot.subsystems.ISubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IWristSubsystem extends ISubsystem {

   /**
    * Stop the wrist from any motion it may have been running under.
    */
   public void stop();

   /**
    * DOCS: Add your docs here.
    */
   public static enum WristPosition {
      //@formatter:off
      upPosition("CW", 0),
      overPosition("CCW", 0);
      //@formatter:on

      private final String name;
      private double position;

      private WristPosition(String name, double position) {
         this.name = name;
         this.position = position;
      }

      public void set(double position) {
         this.position = position;
      }

      public double get() {
         return position;
      }

      @Override
      public String toString() {
         return name;
      }
   }

   /**
    * Rotate the wrist to the specified position and hold it
    * there (using a PID).
    */
   public void rotateToPosition(WristPosition position);

   /**
    * Rotate the wrist to the specified position and hold it
    * there (using a PID). This is meant for a "manual" setting
    * rather than one of the canned pre-sets.
    * 
    * @param target
    */
   public void rotateToTarget(double target);

   /**
    * Rotate the gripper clock-wise.
    */
   public void rotateCW();

   /**
    * Rotate the gripper counter-clockwise.
    */
   public void rotateCCW();

   /**
    * Rotate the gripper.
    *
    * @param speed
    *           speed to rotate at ("+" is CW, "-" is CCW)
    */
   public void rotate(double speed);

}
