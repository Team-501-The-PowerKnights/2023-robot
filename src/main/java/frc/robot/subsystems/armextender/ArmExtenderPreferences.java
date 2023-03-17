/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.preferences.PIDPreferences;
import frc.robot.subsystems.SubsystemNames;

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

   static final String extendPID_P = name + PIDPreferences.pid_P;
   static final String extendPID_I = name + PIDPreferences.pid_I;
   static final String extendPID_D = name + PIDPreferences.pid_D;
   static final String extendPID_IZone = name + PIDPreferences.pid_IZone;
   static final String extendPID_FF = name + PIDPreferences.pid_FF;
   static final String extendPID_minOutput = name + PIDPreferences.pid_minOutput;
   static final String extendPID_maxOutput = name + PIDPreferences.pid_maxOutput;

   static final String extend_minSoftLimit = name + ".MinSoftLimit";
   static final String extend_maxSoftLimit = name + ".MaxSoftLimit";

   static final String extend_highSetPoint = name + ".HighSetPoint";
   static final String extend_midSetPoint = name + ".MidSetPoint";
   static final String extend_lowSetPoint = name + ".LowSetPoint";
   static final String extend_inSetPoint = name + ".InSetPoint";

   private ArmExtenderPreferences() {
   }

   // FIXME: Make perferences & NetworkTables right
   public static void initialize() {
      if (!Preferences.containsKey(extendPID_P)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_P);
         Preferences.setDouble(extendPID_P, 0.0);
      }
      if (!Preferences.containsKey(extendPID_I)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_I);
         Preferences.setDouble(extendPID_I, 0.0);
      }
      if (!Preferences.containsKey(extendPID_D)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_D);
         Preferences.setDouble(extendPID_D, 0.0);
      }
      if (!Preferences.containsKey(extendPID_IZone)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_IZone);
         Preferences.setDouble(extendPID_IZone, 0.0);
      }
      if (!Preferences.containsKey(extendPID_FF)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_FF);
         Preferences.setDouble(extendPID_FF, 0.0);
      }
      if (!Preferences.containsKey(extendPID_minOutput)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_minOutput);
         Preferences.setDouble(extendPID_minOutput, 0.0);
      }
      if (!Preferences.containsKey(extendPID_maxOutput)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_maxOutput);
         Preferences.setDouble(extendPID_maxOutput, 0.0);
      }

      if (!Preferences.containsKey(extend_minSoftLimit)) {
         logger.warn("{} doesn't exist; creating with default", extend_minSoftLimit);
         Preferences.setDouble(extend_minSoftLimit, 0.0);
      }
      if (!Preferences.containsKey(extend_maxSoftLimit)) {
         logger.warn("{} doesn't exist; creating with default", extend_maxSoftLimit);
         Preferences.setDouble(extend_maxSoftLimit, 0.0);
      }

      if (!Preferences.containsKey(extend_highSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", extend_highSetPoint);
         Preferences.setDouble(extend_highSetPoint, 0.0);
      }
      if (!Preferences.containsKey(extend_midSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", extend_midSetPoint);
         Preferences.setDouble(extend_midSetPoint, 0.0);
      }
      if (!Preferences.containsKey(extend_lowSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", extend_lowSetPoint);
         Preferences.setDouble(extend_lowSetPoint, 0.0);
      }
      if (!Preferences.containsKey(extend_inSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", extend_inSetPoint);
         Preferences.setDouble(extend_inSetPoint, 0.0);
      }
   }

}
