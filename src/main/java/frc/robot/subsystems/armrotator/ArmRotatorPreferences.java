/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

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
public final class ArmRotatorPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmRotatorPreferences.class.getName());

   private ArmRotatorPreferences() {
      super(SubsystemNames.armRotatorName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static ArmRotatorPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final ArmRotatorPreferences INSTANCE = new ArmRotatorPreferences();
   }

   /** PID settings */
   final String PID_P = name + PIDValues.pid_P;
   final String PID_I = name + PIDValues.pid_I;
   final String PID_D = name + PIDValues.pid_D;
   final String PID_IZone = name + PIDValues.pid_IZone;
   final String PID_FF = name + PIDValues.pid_FF;
   final String PID_minOutput = name + PIDValues.pid_minOutput;
   final String PID_maxOutput = name + PIDValues.pid_maxOutput;
   final String PID_maxVel = name + PIDValues.pid_maxVel;
   final String PID_minVel = name + PIDValues.pid_minVel;
   final String PID_maxAcc = name + PIDValues.pid_maxAcc;
   final String PID_maxErr = name + PIDValues.pid_maxErr;

   private static final double default_pid_P = 0.00005;
   private static final double default_pid_I = 0.000001;
   private static final double default_pid_D = 0;
   private static final double default_pid_IZone = 0;
   private static final double default_pid_FF = 0.000038;
   private static final double default_pid_minOutput = -0.8;
   private static final double default_pid_maxOutput = 0.8;
   private static final double default_pid_minVel = 0.0;
   private static final double default_pid_maxVel = 2000.0;
   private static final double default_pid_maxAcc = 1000;
   private static final double default_pid_maxErr = 0.2;

   /** Ramp rate */
   final String rampRate = name + ".RampRate";

   private static final double default_rampRate = 0.5;

   /** Set points for the various positions */
   final String overSetPoint = name + ".OverSetPoint";
   final String highSetPoint = name + ".HighSetPoint";
   final String midSetPoint = name + ".MidSetPoint";
   final String lowSetPoint = name + ".LowSetPoint";
   //
   final String autoConeSetPoint = name + ".AutoConeSetPoint";

   private static final double default_overPosition = -16;
   private static final double default_highPosition = 25;
   private static final double default_midPosition = 40;
   private static final double default_lowPosition = 70;
   //
   private static final double default_autoConePosition = 0;

   // FIXME: Make perferences & NetworkTables right
   @Override
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

      checkAndAddDoublePreference(overSetPoint, default_overPosition);
      checkAndAddDoublePreference(highSetPoint, default_highPosition);
      checkAndAddDoublePreference(midSetPoint, default_midPosition);
      checkAndAddDoublePreference(lowSetPoint, default_lowPosition);

      checkAndAddDoublePreference(autoConeSetPoint, default_autoConePosition);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
