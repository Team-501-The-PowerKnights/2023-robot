/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/* Open Source Software - may be modified and shared by other FRC teams  */
/* under the terms of the Team501 license. The code must be accompanied  */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project.                                                      */
/*-----------------------------------------------------------------------*/

package frc.robot.telemetry;

/**
 * Provides a standard way of defining names for the <i>Telemetry</i> used in
 * the program. No code should define or use a hard-coded string outside of the
 * ones defined in this class.
 **/
public final class TelemetryNames {

   private TelemetryNames() {
   }

   public final class Misc {
      private static final String name = "Misc";

      public static final String programmer = name + ".programmer";
      public static final String codeVersion = name + ".codeVersion";

      public static final String robotName = name + ".robotName";
      public static final String robotImpl = name + ".robotImpl";

      public static final String fmsConnected = name + ".fmsConnected";

      public static final String realAuto = name + ".realAuto";

      public static final String initStatus = name + ".initStatus";

      public static final String endGameStarted = name + ".endGameStarted";
   }

   /***************
    * Managers
    ***************/

   public final class Telemetry {
      // FIXME: Need to not be public (but needs higher grouping)
      public static final String name = "Telemetry";

      public static final String status = name + ".status";
   }

   public final class Preferences {
      private static final String name = "Preferences";

      public static final String status = name + ".status";
   }

   public final class Properties {
      private static final String name = "Properties";

      public static final String status = name + ".status";
   }

}