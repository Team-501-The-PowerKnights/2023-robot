/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.wrist;

import org.slf4j.Logger;

import frc.robot.preferences.BasePreferences;
import frc.robot.subsystems.SubsystemNames;
import frc.robot.utils.PIDValues;

import riolog.PKLogger;

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
public final class WristPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(WristPreferences.class.getName());

   private WristPreferences() {
      super(SubsystemNames.wristName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static WristPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final WristPreferences INSTANCE = new WristPreferences();
   }

   /** PID settings */
   final String PID_P = name + PIDValues.pid_P;
   final String PID_I = name + PIDValues.pid_I;
   final String PID_D = name + PIDValues.pid_D;
   final String PID_IZone = name + PIDValues.pid_IZone;
   final String PID_FF = name + PIDValues.pid_FF;
   final String PID_minOutput = name + PIDValues.pid_minOutput;
   final String PID_maxOutput = name + PIDValues.pid_maxOutput;

   private static final double default_pid_P = 0.4;
   private static final double default_pid_I = 0.0006;
   private static final double default_pid_D = 0;
   private static final double default_pid_IZone = 0;
   private static final double default_pid_FF = 0;
   private static final double default_pid_minOutput = -0.3;
   private static final double default_pid_maxOutput = 0.3;

   /** Set points for the various positions */
   final String upSetPoint = name + ".UpSetPoint";
   final String overSetPoint = name + ".OverSetPoint";

   private static final double default_upPosition = 0;
   private static final double default_overPosition = 10.2;

   // FIXME: Make perferences & NetworkTables right
   public void initialize() {
      logger.info("initializing");

      checkAndAddDoublePreference(PID_P, default_pid_P);
      checkAndAddDoublePreference(PID_I, default_pid_I);
      checkAndAddDoublePreference(PID_D, default_pid_D);
      checkAndAddDoublePreference(PID_IZone, default_pid_IZone);
      checkAndAddDoublePreference(PID_FF, default_pid_FF);
      checkAndAddDoublePreference(PID_minOutput, default_pid_minOutput);
      checkAndAddDoublePreference(PID_maxOutput, default_pid_maxOutput);

      checkAndAddDoublePreference(upSetPoint, default_upPosition);
      checkAndAddDoublePreference(overSetPoint, default_overPosition);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
