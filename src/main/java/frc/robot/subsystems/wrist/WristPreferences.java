/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.wrist;

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
public final class WristPreferences {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(WristPreferences.class.getName());

   static private final String name = SubsystemNames.wristName;

   /** PID settings */
   static final String PID_P = name + PIDValues.pid_P;
   static final String PID_I = name + PIDValues.pid_I;
   static final String PID_D = name + PIDValues.pid_D;
   static final String PID_IZone = name + PIDValues.pid_IZone;
   static final String PID_FF = name + PIDValues.pid_FF;
   static final String PID_minOutput = name + PIDValues.pid_minOutput;
   static final String PID_maxOutput = name + PIDValues.pid_maxOutput;

   private static final double default_pid_P = 0;
   private static final double default_pid_I = 0;
   private static final double default_pid_D = 0;
   private static final double default_pid_IZone = 0;
   private static final double default_pid_FF = 0;
   private static final double default_pid_minOutput = -0;
   private static final double default_pid_maxOutput = 0;

   /** Set points for the various positions */
   static final String cwSetPoint = name + ".CWSetPoint";
   static final String ccwSetPoint = name + ".CCWSetPoint";

   private static final double default_cwPosition = 0;
   private static final double default_ccwPosition = 0;

   private WristPreferences() {
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

      if (!Preferences.containsKey(cwSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", cwSetPoint);
         Preferences.setDouble(cwSetPoint, default_cwPosition);
      }
      if (!Preferences.containsKey(ccwSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", ccwSetPoint);
         Preferences.setDouble(ccwSetPoint, default_ccwPosition);
      }
   }

}
