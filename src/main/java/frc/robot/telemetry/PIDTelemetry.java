/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.telemetry;

import riolog.PKLogger;
import riolog.RioLogger;

public class PIDTelemetry {

   /** Our classes' logger **/
   @SuppressWarnings("unused")
   private static final PKLogger logger = RioLogger.getLogger(PIDTelemetry.class.getName());

   public static final String pid_Enabled = ".PIDEnabled";
   public static final String pid_Target = ".PIDTarget";
   public static final String pid_Current = ".PIDCurrent";
   public static final String pid_AtTarget = ".PIDAtTarget";

   public boolean PIDEnabled;
   public double PIDTarget;
   public double PIDCurrent;
   public boolean PIDAtTarget;

   public PIDTelemetry() {
      this(false, 0, 0, false);
   }

   public PIDTelemetry(
   //@formatter:off
      boolean PIDEnabled,
      double PIDTarget,
      double PIDCurrent,
      boolean PIDAtTarget)
    //@formatter:on
   {
      this.PIDEnabled = PIDEnabled;
      this.PIDTarget = PIDTarget;
      this.PIDCurrent = PIDCurrent;
      this.PIDAtTarget = PIDAtTarget;
   }

}
