/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.lift;

import frc.robot.subsystems.IPIDSubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface ILiftSubsystem extends IPIDSubsystem {

   /**
    * Stop the turret from any motion it may have been running under.
    */
   public void stop();

   /**
    * DOCS: Add your docs here.
    */
   public static enum LiftPosition {
      //@formatter:off
      startPosition("Start", 0),
      lowPosition("Low", 0),
      midPosition("Mid", 0),
      highPosition("High", 0);
      //@formatter:on

      private final String name;
      private double position;

      private LiftPosition(String name, double position) {
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
    * Lift the subsystem to the specified position and hold it
    * there (using a PID).
    */
   public void moveToPosition(LiftPosition position);

   /**
    * Lift the subsystem to the specified position and hold it
    * there (using a PID). This is meant for a "manual" setting
    * rather than one of the canned pre-sets.
    * 
    * @param target
    */
   public void moveToTarget(double target);

   /**
    * Adjust the current set point of the PID. This change will
    * be lost the next time one of the pre-sets is selected.
    *
    * @param offset
    *           amount of offset ("+" is up, "-" is down)
    */
   public void offsetTarget(double offset);

   /**
    * Raise/Lower the subsystem under 'manual' control.
    *
    * @param speed
    *           speed to move at ("+" is up, "-" is down)
    */
   public void move(double speed);

   /**
    * Lift the subystem up under 'manual' control.
    * 
    * @param speed
    *           speed to lift at
    */
   default public void liftUp(double speed) {
      move(Math.abs(speed));
   }

   /**
    * Drop the subsystem down under 'manual' control.
    *
    * @param speed
    *           speed to drop at
    */
   default public void dropDown(double speed) {
      move(-Math.abs(speed));
   }

}
