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
    * Stop the arm from any motion it may have been running under.
    */
   public void stop();

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