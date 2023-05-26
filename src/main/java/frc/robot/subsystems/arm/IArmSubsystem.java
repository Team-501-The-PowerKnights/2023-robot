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
   public static enum ArmPosition {
      //@formatter:off
      highPosition("High", 0), 
      midPosition("Mid", 0), 
      lowPosition("Low", 0);
      //@formatter:on

      private final String name;
      private double position;

      private ArmPosition(String name, double position) {
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
    * Move the arm to the specified position and hold it
    * there (using a PID).
    */
   public void moveToPosition(ArmPosition position);

   /**
    * Move the arm to the specified position and hold it
    * there (using a PID). This is meant for a "manual" setting
    * rather than one of the canned pre-sets.
    * 
    * @param target
    */
   public void moveToTarget(double target);

   /**
    * Move the arm out away from the tower.
    */
   public void moveOut();

   /**
    * Move the arm in towards the tower.
    */
   public void moveIn();

   /**
    * Move the arm under 'manual' control.
    *
    * @param speed
    *           speed to move at ("+" is up, "-" is down)
    */
   public void move(double speed);

}