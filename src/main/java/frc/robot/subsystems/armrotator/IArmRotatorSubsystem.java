/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import frc.robot.subsystems.IPIDSubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IArmRotatorSubsystem extends IPIDSubsystem {

   /**
    * Stop the arm rotator from any motion it may have been running under.
    */
   public void stop();

   /**
    * DOCS: Add your docs here.
    */
   public static enum ArmRotationPosition {
      //@formatter:off
      overPosition("Over", 0),
      highPosition("High", 0), 
      midPosition("Mid", 0), 
      lowPosition("Low", 0),
            //
      autoConePosition("AutoCone", 0);
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
    * Adjust the current set point of the PID. This change will
    * be lost the next time one of the pre-sets is selected.
    *
    * @param offset
    *           amount of offset ("+" is up, "-" is down)
    */
   public void offsetTarget(double offset);

   /**
    * Rotate the arm under 'manual' control.
    *
    * @param speed
    *           speed to rotate at ("+" is up, "-" is down)
    */
   public void rotate(double speed);

   /**
    * 
    * @return
    */
   public double syncEncoders();

}