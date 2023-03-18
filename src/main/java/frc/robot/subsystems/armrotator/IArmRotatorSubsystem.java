/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import frc.robot.subsystems.ISubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IArmRotatorSubsystem extends ISubsystem {

   /**
    * Stop the arm rotator from any motion it may have been running under.
    */
   public void stop();

   /** Default PID settings */
   public static final double default_pid_P = 0.2;
   public static final double default_pid_I = 1e-5;
   public static final double default_pid_D = 1;
   public static final double default_pid_IZone = 0;
   public static final double default_pid_FF = 0;
   public static final double default_pid_minOutput = -0.2;
   public static final double default_pid_maxOutput = 0.5;

   /** Default ramp rate settings (motor) */
   public static final double default_rampRate = 0.5;

   /** Default set points for the various positions */
   public static final double default_overPosition = 60;
   public static final double default_highPosition = 26;
   public static final double default_midPosition = 17;
   public static final double default_lowPosition = 7;

   /**
    * DOCS: Add your docs here.
    */
   public static enum ArmRotationPosition {
      //@formatter:off
      overPosition("Over", default_overPosition),
      highPosition("High", default_highPosition), 
      midPosition("Mid", default_midPosition), 
      lowPosition("Low", default_lowPosition);
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
    * Rotate the arm under 'manual' control.
    *
    * @param speed
    *           speed to rotate at ("+" is up, "-" is down)
    */
   public void rotate(double speed);

}