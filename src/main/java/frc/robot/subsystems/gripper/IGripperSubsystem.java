/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

import frc.robot.subsystems.ISubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IGripperSubsystem extends ISubsystem {

   /**
    * Stop the gripper from any motion it may have been running under.
    */
   public void stop();

   /**
    * Pull the object into the gripper.
    */
   public void pullIn();

   /**
    * Push the object out of the gripper.
    */
   public void pushOut();

   /**
    * Grip the object.
    *
    * @param speed
    *           speed to rotate at ("+" is in, "-" is out)
    */
   public void grip(double speed);

}
