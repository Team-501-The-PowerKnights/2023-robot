/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armextender;

import org.slf4j.Logger;

import frc.robot.preferences.PIDPreferences;
import frc.robot.subsystems.SubsystemNames;

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
public final class ArmExtenderPreferences extends PIDPreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmExtenderPreferences.class.getName());

   private ArmExtenderPreferences() {
      //@formatter:off
      super(SubsystemNames.armExtenderName,
         default_pid_P,
         default_pid_I,
         default_pid_D,
         default_pid_IZone,
         default_pid_FF,
         default_pid_minOutput,
         default_pid_maxOutput);
      //@formatter:on
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
      super.initialize();

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
