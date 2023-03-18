/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import frc.robot.subsystems.ISubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IArmExtenderSubsystem extends ISubsystem {

   /**
    * Stop the arm extender from any motion it may have been running under.
    */
   public void stop();

   /** Default PID settings */
   public static final double default_pid_P = 0.2;
   public static final double default_pid_I = 0;
   public static final double default_pid_D = 1;
   public static final double default_pid_IZone = 0;
   public static final double default_pid_FF = 0;
   public static final double default_pid_minOutput = -1;
   public static final double default_pid_maxOutput = 1;

   /** Default soft limits on extension (motor + encoder) */
   public static final float default_minSoftLimit = 5f;
   public static final float default_maxSoftLimit = 160f;

   /** Default set points for the various positions */
   public static final double default_highPosition = 160;
   public static final double default_midPosition = 85;
   public static final double default_lowPosition = 50;
   public static final double default_inPosition = 5;

   /**
    * DOCS: Add your docs here.
    */
   public static enum ArmExtensionPosition {
      //@formatter:off
      highPosition("High", default_highPosition), 
      midPosition("Mid", default_midPosition), 
      lowPosition("Low", default_lowPosition),
      inPosition("In", default_inPosition);
      //@formatter:on

      private final String name;
      private double position;

      private ArmExtensionPosition(String name, double position) {
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
    * Extend the arm to the specified position and hold it
    * there (using a PID).
    */
   public void extendToPosition(ArmExtensionPosition position);

   /**
    * Extend the arm to the specified position and hold it
    * there (using a PID). This is meant for a "manual" setting
    * rather than one of the canned pre-sets.
    * 
    * @param target
    */
   public void extendToTarget(double target);

   /**
    * Extend the arm under 'manual' control.
    *
    * @param speed
    *           speed to extend at ("+" is out, "-" is in)
    */
   public void extend(double speed);

}