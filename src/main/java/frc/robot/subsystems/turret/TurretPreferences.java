/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.turret;

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
public class TurretPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(TurretPreferences.class.getName());

   private TurretPreferences() {
      super(SubsystemNames.turretName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static TurretPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final TurretPreferences INSTANCE = new TurretPreferences();
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
   private static final double default_pid_P = 0.00006;
   private static final double default_pid_I = 0.000001;
   private static final double default_pid_D = 0;
   private static final double default_pid_IZone = 0;
   private static final double default_pid_FF = 0.00005;
   private static final double default_pid_minOutput = -1;
   private static final double default_pid_maxOutput = 1;

   /** Smart Motion settings */
   final String PIDSM_minVelocity = name + PIDValues.pidsm_minVelocity;
   final String PIDSM_maxVelocity = name + PIDValues.pidsm_maxVelocity;
   final String PIDSM_maxAccel = name + PIDValues.pidsm_maxAccel;
   final String PIDSM_allowedError = name + PIDValues.pidsm_AllowedError;

   private static final double default_pidsm_minVelocity = 0;
   private static final double default_pidsm_maxVelocity = 6000;
   private static final double default_pidsm_maxAccel = 5000;
   private static final double default_pidsm_allowedError = 0.1;

   /** Ramp rate */
   final String rampRate = name + ".RampRate";

   private static final double default_rampRate = 0.5;

   /** Set points for the various positions */
   final String startSetPoint = name + ".StartSetPoint";
   final String leftSetPoint = name + ".LeftSetPoint";
   final String frontSetPoint = name + ".FrontSetPoint";
   final String rightSetPoint = name + ".RightSetPoint";

   private static final double default_startPosition = 0;
   private static final double default_leftPosition = -150; // negative (CCW)
   private static final double default_frontPosition = 0;
   private static final double default_rightPosition = 150; // positive (CW)

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
      checkAndAddDoublePreference(leftSetPoint, default_leftPosition);
      checkAndAddDoublePreference(frontSetPoint, default_frontPosition);
      checkAndAddDoublePreference(rightSetPoint, default_rightPosition);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
