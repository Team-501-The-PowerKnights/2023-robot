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
