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
   public static enum ArmRotationPosition {
      //@formatter:off
      highPosition("High", 0), 
      midPosition("Mid", 0), 
      lowPosition("Low", 0);
      //@formatter:on

      private final String name;
      private double position;

      private ArmRotationPosition(String name, double position) {
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
    * Rotate the arm to the specified position and hold it
    * there (using a PID).
    */
   public void rotateToPosition(ArmRotationPosition position);

   /**
    * Rotate the arm to the specified position and hold it
    * there (using a PID). This is meant for a "manual" setting
    * rather than one of the canned pre-sets.
    * 
    * @param target
    */
   public void rotateToTarget(double target);

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
    * Extend the arm to the specified position and hold it
    * there (using a PID). This is meant for a "manual" setting
    * rather than one of the canned pre-sets.
    * 
    * @param target
    */
   public void extendToTarget(double target);

   /**
    * Extend the arm out.
    */
   public void extend();

   /**
    * Retract the arm in.
    */
   public void retract();

   /**
    * Extend the arm under 'manual' control.
    *
    * @param speed
    *           speed to extend at ("+" is out, "-" is in)
    */
   public void extend(double speed);

}