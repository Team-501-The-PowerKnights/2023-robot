/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

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
   final String PID_minVel = name + PIDValues.pid_minVel;
   final String PID_maxVel = name + PIDValues.pid_maxVel;
   final String PID_maxAcc = name + PIDValues.pid_maxAcc;
   final String PID_maxErr = name + PIDValues.pid_maxErr;

   static final double default_pid_P = 0.0005;
   static final double default_pid_I = 0.00001;
   static final double default_pid_D = 0;
   static final double default_pid_IZone = 0;
   static final double default_pid_FF = 0.000038;
   static final double default_pid_minOutput = -0.5;
   static final double default_pid_maxOutput = 0.5;
   private static final double default_pid_minVel = 0;
   private static final double default_pid_maxVel = 3500;
   private static final double default_pid_maxAcc = 2000;
   private static final double default_pid_maxErr = 0.2;

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

      checkAndAddDoublePreference(PID_P, default_pid_P);
      checkAndAddDoublePreference(PID_I, default_pid_I);
      checkAndAddDoublePreference(PID_D, default_pid_D);
      checkAndAddDoublePreference(PID_IZone, default_pid_IZone);
      checkAndAddDoublePreference(PID_FF, default_pid_FF);
      checkAndAddDoublePreference(PID_minOutput, default_pid_minOutput);
      checkAndAddDoublePreference(PID_maxOutput, default_pid_maxOutput);
      checkAndAddDoublePreference(PID_minVel, default_pid_minVel);
      checkAndAddDoublePreference(PID_maxVel, default_pid_maxVel);
      checkAndAddDoublePreference(PID_maxAcc, default_pid_maxAcc);
      checkAndAddDoublePreference(PID_maxErr, default_pid_maxErr);

      checkAndAddDoublePreference(rampRate, default_rampRate);

      checkAndAddDoublePreference(minSoftLimit, default_minSoftLimit);
      checkAndAddDoublePreference(maxSoftLimit, default_maxSoftLimit);

      checkAndAddDoublePreference(overSetPoint, default_overPosition);
      checkAndAddDoublePreference(highSetPoint, default_highPosition);
      checkAndAddDoublePreference(midSetPoint, default_midPosition);
      checkAndAddDoublePreference(lowSetPoint, default_lowPosition);

      checkAndAddDoublePreference(inSetPoint, default_inPosition);

      checkAndAddDoublePreference(autoConeSetPoint, default_autoConePosition);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
