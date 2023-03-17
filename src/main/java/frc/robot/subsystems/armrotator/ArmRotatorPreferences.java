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

   static final String rotatePID_P = name + PIDPreferences.pid_P;
   static final String rotatePID_I = name + PIDPreferences.pid_I;
   static final String rotatePID_D = name + PIDPreferences.pid_D;
   static final String rotatePID_IZone = name + PIDPreferences.pid_IZone;
   static final String rotatePID_FF = name + PIDPreferences.pid_FF;
   static final String rotatePID_minOutput = name + PIDPreferences.pid_minOutput;
   static final String rotatePID_maxOutput = name + PIDPreferences.pid_maxOutput;

   static final String rotate_rampRate = name + ".RampRate";

   static final String rotate_overSetPoint = name + ".OverSetPoint";
   static final String rotate_highSetPoint = name + ".HighSetPoint";
   static final String rotate_midSetPoint = name + ".MidSetPoint";
   static final String rotate_lowSetPoint = name + ".LowSetPoint";

   private ArmRotatorPreferences() {
   }

   // FIXME: Make perferences & NetworkTables right
   public static void initialize() {

      if (!Preferences.containsKey(rotatePID_P)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_P);
         Preferences.setDouble(rotatePID_P, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_I)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_I);
         Preferences.setDouble(rotatePID_I, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_D)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_D);
         Preferences.setDouble(rotatePID_D, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_IZone)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_IZone);
         Preferences.setDouble(rotatePID_IZone, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_FF)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_FF);
         Preferences.setDouble(rotatePID_FF, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_minOutput)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_minOutput);
         Preferences.setDouble(rotatePID_minOutput, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_maxOutput)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_maxOutput);
         Preferences.setDouble(rotatePID_maxOutput, 0.0);
      }

      if (!Preferences.containsKey(rotate_rampRate)) {
         logger.warn("{} doesn't exist; creating with default", rotate_rampRate);
         Preferences.setDouble(rotate_rampRate, 0.0);
      }

      if (!Preferences.containsKey(rotate_overSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_overSetPoint);
         Preferences.setDouble(rotate_overSetPoint, 0.0);
      }
      if (!Preferences.containsKey(rotate_highSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_highSetPoint);
         Preferences.setDouble(rotate_highSetPoint, 0.0);
      }
      if (!Preferences.containsKey(rotate_midSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_midSetPoint);
         Preferences.setDouble(rotate_midSetPoint, 0.0);
      }
      if (!Preferences.containsKey(rotate_lowSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_lowSetPoint);
         Preferences.setDouble(rotate_lowSetPoint, 0.0);
      }
   }

}
