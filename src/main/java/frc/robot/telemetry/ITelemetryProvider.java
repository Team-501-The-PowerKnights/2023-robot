/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.telemetry;

/**
 * Add your docs here.
 */
public interface ITelemetryProvider {

   public void updateTelemetry();

   /**
    * TODO: Remove default and/or implement method
    */
   public default void logTelemetry() {
   };

}
