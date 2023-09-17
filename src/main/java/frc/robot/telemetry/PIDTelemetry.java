/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.telemetry;

import org.slf4j.Logger;

import riolog.PKLogger;

public class PIDTelemetry {

   /** Our classes' logger **/
   @SuppressWarnings("unused")
   private static final Logger logger = PKLogger.getLogger(PIDTelemetry.class.getName());

   public static final String pid_Use = ".PIDUse";
   public static final String pid_Enabled = ".PIDEnabled";
   public static final String pid_Target = ".PIDTarget";
   public static final String pid_Current = ".PIDCurrent";
   public static final String pid_AtTarget = ".PIDAtTarget";
   public static final String pid_OnTarget = ".PIDOnTarget";
   public static final String pid_Output = ".PIDOutput";

   public boolean PIDUse;
   public boolean PIDEnabled;
   public double PIDTarget;
   public double PIDCurrent;
   public boolean PIDAtTarget;
   public boolean PIDOnTarget;
   public double PIDOutput;

   public PIDTelemetry() {
      this(false, false, 0, 0, false, false, 0);
   }

   public PIDTelemetry(
   //@formatter:off
      boolean PIDUse,
      boolean PIDEnabled,
      double PIDTarget,
      double PIDCurrent,
      boolean PIDAtTarget,
      boolean PIDOnTarget,
      double PIDOutput)
    //@formatter:on
   {
      this.PIDUse = PIDUse;
      this.PIDEnabled = PIDEnabled;
      this.PIDTarget = PIDTarget;
      this.PIDCurrent = PIDCurrent;
      this.PIDAtTarget = PIDAtTarget;
      this.PIDOnTarget = PIDOnTarget;
      this.PIDOutput = PIDOutput;
   }

}
