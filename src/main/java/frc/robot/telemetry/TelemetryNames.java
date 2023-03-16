/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.telemetry;

import frc.robot.modules.ModuleNames;
import frc.robot.sensors.SensorNames;
import frc.robot.subsystems.SubsystemNames;

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

      public static final String dsConnected = name + ".dsConnected";
      public static final String fmsConnected = name + ".fmsConnected";

      public static final String realAuto = name + ".realAuto";

      public static final String initStatus = name + ".initStatus";

      public static final String endGameStarted = name + ".endGameStarted";
   }

   /***************
    * Managers
    ***************/

   public final class Telemetry {
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

   public final class Scheduler {
      public static final String name = "Scheduler";

      public static final String status = name + ".status";
      // The current commands running on the robot
      public static final String currentCommands = name + ".currentCommands";
   }

   /***************
    * OI
    ***************/

   public final class OI {
      // FIXME: Need to not be public (but needs higher grouping)
      public static final String name = "OI";

      public static final String status = name + ".status";
   }

   public static class HMI {
      private static final String name = SubsystemNames.hmiName;

      public static final String rawSpeed = name + ".rawSpeed";
      public static final String rawTurn = name + ".rawTurn";
      public static final String turbo = name + ".turbo";
      public static final String crawl = name + ".crawl";
      public static final String reversed = name + ".reversed";
      public static final String oiSpeed = name + ".oiSpeed";
      public static final String oiTurn = name + ".oiTurn";

      public static final String speed = name + ".speed";
      public static final String turn = name + ".turn";
   }

   /***************
    * Modules
    ***************/

   public final class Power {
      private static final String name = ModuleNames.powerName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String defCommand = name + "defCommand";

      public static final String busVoltage = name + ".busVoltage";
      public static final String totalCurrent = name + ".totalCurrent";
      public static final String totalEnergy = name + ".totalEnergy";
   }

   public final class Pneumatic {
      private static final String name = ModuleNames.pneumaticName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String defCommand = name + "defCommand";

      public static final String enabled = name + ".enabled";
      public static final String pressureGood = name + ".pressureGood";
   }

   /***************
    * Sensors
    ***************/

   public final class Gyro {
      private static final String name = SensorNames.gyroName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";

      public static final String roll = name + ".roll";
      public static final String pitch = name + ".pitch";
      public static final String yaw = name + ".yaw";
      public static final String angle = name + ".angle";
      public static final String heading = name + ".heading";
   }

   /***************
    * Subsystems
    ***************/

   public final class Drive {
      private static final String name = SubsystemNames.driveName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String autoCommand = name + ".autoCommand";
      public static final String teleCommand = name + ".teleCommand";

      public static final String encoderClicks = name + ".encoderClicks";
      public static final String distanceClicks = name + ".distanceClicks";
      public static final String targetClicks = name + ".targetClicks";

      public static final String leftSpeed = name + ".leftSpeed";
      public static final String rightSpeed = name + ".rightSpeed";
      public static final String brakeEnabled = name + ".brakeEnabled";
   }

   public final class ArmRotator {
      private static final String name = SubsystemNames.armName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String autoCommand = name + ".autoCommand";
      public static final String teleCommand = name + ".teleCommand";

      public static final String PIDEnabled = name + ".rotate" + PIDTelemetry.pid_Enabled;
      public static final String PIDTarget = name + ".rotate" + PIDTelemetry.pid_Target;
      public static final String PIDCurrent = name + ".rotate" + PIDTelemetry.pid_Current;
   }

   public final class ArmExtender {
      private static final String name = SubsystemNames.armName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String autoCommand = name + ".autoCommand";
      public static final String teleCommand = name + ".teleCommand";

      public static final String PIDEnabled = name + ".extend" + PIDTelemetry.pid_Enabled;
      public static final String PIDTarget = name + ".extend" + PIDTelemetry.pid_Target;
      public static final String PIDCurrent = name + ".extend" + PIDTelemetry.pid_Current;
   }

   public final class Arm {
      private static final String name = SubsystemNames.armName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String autoCommand = name + ".autoCommand";
      public static final String teleCommand = name + ".teleCommand";

      public static final String PIDEnabled = name + PIDTelemetry.pid_Enabled;
      public static final String PIDTarget = name + PIDTelemetry.pid_Target;
      public static final String PIDCurrent = name + PIDTelemetry.pid_Current;
   }

   public final class Gripper {
      private static final String name = SubsystemNames.gripperName;

      public static final String status = name + ".status";
      public static final String implClass = name + ".implClass";
      public static final String autoCommand = name + ".autoCommand";
      public static final String teleCommand = name + ".teleCommand";

      public static final String open = name + ".open";
   }

}
