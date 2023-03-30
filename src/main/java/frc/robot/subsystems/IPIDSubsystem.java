/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems;

/**
 * DOCS: Insert documentation here
 */
public interface IPIDSubsystem extends ISubsystem {

   /**
    * Sets the setpoint for the PIDController.
    *
    * @param setpoint
    *           The desired setpoint.
    */
   public void setSetpoint(double setpoint);

   /**
    * Returns the current setpoint of the PIDController.
    *
    * @return The current setpoint.
    */
   public double getSetpoint();

   /**
    * Sets the position error which is considered tolerable for use with
    * atSetpoint().
    *
    * @param tolerance
    *           - Position error which is tolerable.
    */
   public void setTolerance(double tolerance);

   /**
    * Returns the position tolerance of this controller.
    *
    * @return the position tolerance of the controller.
    */
   public double getTolerance();

   /**
    * 
    * @param measurement
    */
   public void newMeasurement(double measurement);

   /**
    * Returns true if the error is within the tolerance of the setpoint.
    *
    * This will return false until at least one input value has been computed.
    * 
    * @return Whether the error is within the acceptable bounds.
    */
   public boolean atSetpoint();

   /**
    * Returns true if the error is within the tolerance of the setpoint.
    *
    * This will return false until at least one input value has been computed.
    * 
    * @return Whether the error is within the acceptable bounds.
    */
   public boolean achievedSetPoint();

   /**
    * 
    */
   public void reset();

}
