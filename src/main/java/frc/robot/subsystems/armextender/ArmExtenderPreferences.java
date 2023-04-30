/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.Preferences;

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
public final class ArmExtenderPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmExtenderPreferences.class.getName());

   private ArmExtenderPreferences() {
      super(SubsystemNames.armExtenderName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static ArmExtenderPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final ArmExtenderPreferences INSTANCE = new ArmExtenderPreferences();
   }

   /** PID settings */
   final String PID_P = name + PIDValues.pid_P;
   final String PID_I = name + PIDValues.pid_I;
   final String PID_D = name + PIDValues.pid_D;
   final String PID_IZone = name + PIDValues.pid_IZone;
   final String PID_FF = name + PIDValues.pid_FF;
   final String PID_minOutput = name + PIDValues.pid_minOutput;
   final String PID_maxOutput = name + PIDValues.pid_maxOutput;

   static final double default_pid_P = 1;
   static final double default_pid_I = 0.0001;
   static final double default_pid_D = 5;
   static final double default_pid_IZone = 10;
   static final double default_pid_FF = 0;
   static final double default_pid_minOutput = -0.5;
   static final double default_pid_maxOutput = 0.5;

   /** Ramp rate */
   final String rampRate = name + ".RampRate";

   private static final double default_rampRate = 0.5;

   /** Soft limits */
   final String minSoftLimit = name + ".MinSoftLimit";
   final String maxSoftLimit = name + ".MaxSoftLimit";

   static final float default_minSoftLimit = -150f;
   static final float default_maxSoftLimit = 0f;

   /** Set points for the various positions */
   final String overSetPoint = name + ".OverSetPoint";
   final String highSetPoint = name + ".HighSetPoint";
   final String midSetPoint = name + ".MidSetPoint";
   final String lowSetPoint = name + ".LowSetPoint";
   final String inSetPoint = name + ".InSetPoint";
   //
   final String autoConeSetPoint = name + ".AutoConeSetPoint";

   static final double default_overPosition = -31.2;
   static final double default_highPosition = 0;
   static final double default_midPosition = -26;
   static final double default_lowPosition = 0;
   static final double default_inPosition = 0;
   //
   static final double default_autoConePosition = 66;

   // FIXME: Make perferences & NetworkTables right
   public void initialize() {
      logger.info("initializing");

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

      if (!Preferences.containsKey(rampRate)) {
         logger.warn("{} doesn't exist; creating with default", rampRate);
         Preferences.setDouble(rampRate, default_rampRate);
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

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
