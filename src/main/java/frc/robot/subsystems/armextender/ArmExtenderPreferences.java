/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.utils.PIDValues;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * Defines the names and values of properties for this package.
 * <p>
 * The name uses dot notation for the hierarchy. The first part is
 * the name of the subsystem and the second is the name of the
 * preference retreivable from the
 * {@link edu.wpi.first.wpilibj.Preferences}.
 *
 * @see edu.wpi.first.networktables.NetworkTable
 */
public final class ArmExtenderPreferences {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmExtenderPreferences.class.getName());

   static private final String name = SubsystemNames.armExtenderName;

   /** PID settings */
   static final String PID_P = name + PIDValues.pid_P;
   static final String PID_I = name + PIDValues.pid_I;
   static final String PID_D = name + PIDValues.pid_D;
   static final String PID_IZone = name + PIDValues.pid_IZone;
   static final String PID_FF = name + PIDValues.pid_FF;
   static final String PID_minOutput = name + PIDValues.pid_minOutput;
   static final String PID_maxOutput = name + PIDValues.pid_maxOutput;

   private static final double default_pid_P = 0.5; // 0.2;
   private static final double default_pid_I = 1e-4; // 0;
   private static final double default_pid_D = 1;
   private static final double default_pid_IZone = 0;
   private static final double default_pid_FF = 0.01; // 0;
   private static final double default_pid_minOutput = -1;
   private static final double default_pid_maxOutput = 1;

   /** Soft limits */
   static final String minSoftLimit = name + ".MinSoftLimit";
   static final String maxSoftLimit = name + ".MaxSoftLimit";

   // FIXME: Added 3:1 gearbox
   private static final float default_minSoftLimit = 3 * 5f;
   private static final float default_maxSoftLimit = 3 * 160f;

   /** Set points for the various positions */
   static final String overSetPoint = name + ".OverSetPoint";
   static final String highSetPoint = name + ".HighSetPoint";
   static final String midSetPoint = name + ".MidSetPoint";
   static final String lowSetPoint = name + ".LowSetPoint";
   static final String inSetPoint = name + ".InSetPoint";
   //
   static final String autoConeSetPoint = name + ".AutoConeSetPoint";

   // FIXME: Added 3:1 gearbox
   private static final double default_overPosition = 3 * 87;
   private static final double default_highPosition = 3 * 160;
   private static final double default_midPosition = 3 * 57; // 85;
   private static final double default_lowPosition = 3 * 5; // 25;
   private static final double default_inPosition = 3 * 5;
   //
   private static final double default_autoConePosition = 3 * 57; // 85;

   private ArmExtenderPreferences() {
   }

   // FIXME: Make perferences & NetworkTables right
   public static void initialize() {
      if (!Preferences.containsKey(PID_P)) {
         logger.warn("{} doesn't exist; creating with default", PID_P);
         Preferences.setDouble(PID_P, default_pid_P);
      }
      if (!Preferences.containsKey(PID_I)) {
         logger.warn("{} doesn't exist; creating with default", PID_I);
         Preferences.setDouble(PID_I, default_pid_I);
      }
      if (!Preferences.containsKey(PID_D)) {
         logger.warn("{} doesn't exist; creating with default", PID_D);
         Preferences.setDouble(PID_D, default_pid_D);
      }
      if (!Preferences.containsKey(PID_IZone)) {
         logger.warn("{} doesn't exist; creating with default", PID_IZone);
         Preferences.setDouble(PID_IZone, default_pid_IZone);
      }
      if (!Preferences.containsKey(PID_FF)) {
         logger.warn("{} doesn't exist; creating with default", PID_FF);
         Preferences.setDouble(PID_FF, default_pid_FF);
      }
      if (!Preferences.containsKey(PID_minOutput)) {
         logger.warn("{} doesn't exist; creating with default", PID_minOutput);
         Preferences.setDouble(PID_minOutput, default_pid_minOutput);
      }
      if (!Preferences.containsKey(PID_maxOutput)) {
         logger.warn("{} doesn't exist; creating with default", PID_maxOutput);
         Preferences.setDouble(PID_maxOutput, default_pid_maxOutput);
      }

      if (!Preferences.containsKey(minSoftLimit)) {
         logger.warn("{} doesn't exist; creating with default", minSoftLimit);
         Preferences.setDouble(minSoftLimit, default_minSoftLimit);
      }
      if (!Preferences.containsKey(maxSoftLimit)) {
         logger.warn("{} doesn't exist; creating with default", maxSoftLimit);
         Preferences.setDouble(maxSoftLimit, default_maxSoftLimit);
      }

      if (!Preferences.containsKey(overSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", overSetPoint);
         Preferences.setDouble(overSetPoint, default_overPosition);
      }
      if (!Preferences.containsKey(highSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", highSetPoint);
         Preferences.setDouble(highSetPoint, default_highPosition);
      }
      if (!Preferences.containsKey(midSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", midSetPoint);
         Preferences.setDouble(midSetPoint, default_midPosition);
      }
      if (!Preferences.containsKey(lowSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", lowSetPoint);
         Preferences.setDouble(lowSetPoint, default_lowPosition);
      }
      if (!Preferences.containsKey(inSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", inSetPoint);
         Preferences.setDouble(inSetPoint, default_inPosition);
      }

      if (!Preferences.containsKey(autoConeSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", autoConeSetPoint);
         Preferences.setDouble(autoConeSetPoint, default_autoConePosition);
      }
   }

}
