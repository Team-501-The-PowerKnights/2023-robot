/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.lift;

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
public class LiftPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(LiftPreferences.class.getName());

   private LiftPreferences() {
      super(SubsystemNames.liftName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static LiftPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final LiftPreferences INSTANCE = new LiftPreferences();
   }

   /** PID settings */
   final String PID_Use = name + PIDValues.pid_Use;
   final String PID_P = name + PIDValues.pid_P;
   final String PID_I = name + PIDValues.pid_I;
   final String PID_D = name + PIDValues.pid_D;
   final String PID_IZone = name + PIDValues.pid_IZone;
   final String PID_FF = name + PIDValues.pid_FF;
   final String PID_minOutput = name + PIDValues.pid_minOutput;
   final String PID_maxOutput = name + PIDValues.pid_maxOutput;

   private static final boolean default_pid_Use = false;
   private static final double default_pid_P = 0;
   private static final double default_pid_I = 0;
   private static final double default_pid_D = 0;
   private static final double default_pid_IZone = 0;
   private static final double default_pid_FF = 0;
   private static final double default_pid_minOutput = 0;
   private static final double default_pid_maxOutput = 0;

   /** Smart Motion settings */
   final String PIDSM_minVelocity = name + PIDValues.pidsm_minVelocity;
   final String PIDSM_maxVelocity = name + PIDValues.pidsm_maxVelocity;
   final String PIDSM_maxAccel = name + PIDValues.pidsm_maxAccel;
   final String PIDSM_allowedError = name + PIDValues.pidsm_AllowedError;

   private static final double default_pidsm_minVelocity = 0;
   private static final double default_pidsm_maxVelocity = 0;
   private static final double default_pidsm_maxAccel = 0;
   private static final double default_pidsm_allowedError = 0;

   /** Ramp rate */
   final String rampRate = name + ".RampRate";

   private static final double default_rampRate = 0;

   /** Set points for the various positions */
   final String startSetPoint = name + ".StartSetPoint";
   final String lowSetPoint = name + ".LowSetPoint";
   final String midSetPoint = name + ".MidSetPoint";
   final String highSetPoint = name + ".HighSetPoint";

   private static final double default_startPosition = 0;
   private static final double default_highPosition = 0;
   private static final double default_midPosition = 0;
   private static final double default_lowPosition = 0;

   // FIXME: Make perferences & NetworkTables right
   public void initialize() {
      logger.info("initializing");

      checkAndAddBooleanPreference(PID_Use, default_pid_Use);
      checkAndAddDoublePreference(PID_P, default_pid_P);
      checkAndAddDoublePreference(PID_I, default_pid_I);
      checkAndAddDoublePreference(PID_D, default_pid_D);
      checkAndAddDoublePreference(PID_IZone, default_pid_IZone);
      checkAndAddDoublePreference(PID_FF, default_pid_FF);
      checkAndAddDoublePreference(PID_minOutput, default_pid_minOutput);
      checkAndAddDoublePreference(PID_maxOutput, default_pid_maxOutput);

      checkAndAddDoublePreference(PIDSM_minVelocity, default_pidsm_minVelocity);
      checkAndAddDoublePreference(PIDSM_maxVelocity, default_pidsm_maxVelocity);
      checkAndAddDoublePreference(PIDSM_maxAccel, default_pidsm_maxAccel);
      checkAndAddDoublePreference(PIDSM_allowedError, default_pidsm_allowedError);

      checkAndAddDoublePreference(rampRate, default_rampRate);

      checkAndAddDoublePreference(startSetPoint, default_startPosition);
      checkAndAddDoublePreference(highSetPoint, default_highPosition);
      checkAndAddDoublePreference(midSetPoint, default_midPosition);
      checkAndAddDoublePreference(lowSetPoint, default_lowPosition);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
