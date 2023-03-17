/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

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
public final class ArmRotatorPreferences {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmRotatorPreferences.class.getName());

   static private final String name = SubsystemNames.armRotatorName;

   static final String PID_P = name + PIDPreferences.pid_P;
   static final String PID_I = name + PIDPreferences.pid_I;
   static final String PID_D = name + PIDPreferences.pid_D;
   static final String PID_IZone = name + PIDPreferences.pid_IZone;
   static final String PID_FF = name + PIDPreferences.pid_FF;
   static final String PID_minOutput = name + PIDPreferences.pid_minOutput;
   static final String PID_maxOutput = name + PIDPreferences.pid_maxOutput;

   static final String rampRate = name + ".RampRate";

   static final String overSetPoint = name + ".OverSetPoint";
   static final String highSetPoint = name + ".HighSetPoint";
   static final String midSetPoint = name + ".MidSetPoint";
   static final String lowSetPoint = name + ".LowSetPoint";

   private ArmRotatorPreferences() {
   }

   // FIXME: Make perferences & NetworkTables right
   public static void initialize() {

      if (!Preferences.containsKey(PID_P)) {
         logger.warn("{} doesn't exist; creating with default", PID_P);
         Preferences.setDouble(PID_P, 0.0);
      }
      if (!Preferences.containsKey(PID_I)) {
         logger.warn("{} doesn't exist; creating with default", PID_I);
         Preferences.setDouble(PID_I, 0.0);
      }
      if (!Preferences.containsKey(PID_D)) {
         logger.warn("{} doesn't exist; creating with default", PID_D);
         Preferences.setDouble(PID_D, 0.0);
      }
      if (!Preferences.containsKey(PID_IZone)) {
         logger.warn("{} doesn't exist; creating with default", PID_IZone);
         Preferences.setDouble(PID_IZone, 0.0);
      }
      if (!Preferences.containsKey(PID_FF)) {
         logger.warn("{} doesn't exist; creating with default", PID_FF);
         Preferences.setDouble(PID_FF, 0.0);
      }
      if (!Preferences.containsKey(PID_minOutput)) {
         logger.warn("{} doesn't exist; creating with default", PID_minOutput);
         Preferences.setDouble(PID_minOutput, 0.0);
      }
      if (!Preferences.containsKey(PID_maxOutput)) {
         logger.warn("{} doesn't exist; creating with default", PID_maxOutput);
         Preferences.setDouble(PID_maxOutput, 0.0);
      }

      if (!Preferences.containsKey(rampRate)) {
         logger.warn("{} doesn't exist; creating with default", rampRate);
         Preferences.setDouble(rampRate, 0.0);
      }

      if (!Preferences.containsKey(overSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", overSetPoint);
         Preferences.setDouble(overSetPoint, 0.0);
      }
      if (!Preferences.containsKey(highSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", highSetPoint);
         Preferences.setDouble(highSetPoint, 0.0);
      }
      if (!Preferences.containsKey(midSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", midSetPoint);
         Preferences.setDouble(midSetPoint, 0.0);
      }
      if (!Preferences.containsKey(lowSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", lowSetPoint);
         Preferences.setDouble(lowSetPoint, 0.0);
      }
   }

}
