/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.preferences;

import org.slf4j.Logger;

import frc.robot.utils.PIDValues;

import riolog.PKLogger;

abstract public class PIDPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(PIDPreferences.class.getName());

   /** PID settings */
   public final String PID_P = name + PIDValues.pid_P;
   public final String PID_I = name + PIDValues.pid_I;
   public final String PID_D = name + PIDValues.pid_D;
   public final String PID_IZone = name + PIDValues.pid_IZone;
   public final String PID_FF = name + PIDValues.pid_FF;
   public final String PID_minOutput = name + PIDValues.pid_minOutput;
   public final String PID_maxOutput = name + PIDValues.pid_maxOutput;

   protected final double default_pid_P;
   protected final double default_pid_I;
   protected final double default_pid_D;
   protected final double default_pid_IZone;
   protected final double default_pid_FF;
   protected final double default_pid_minOutput;
   protected final double default_pid_maxOutput;

   protected PIDPreferences(String name,
         double default_pid_P,
         double default_pid_I,
         double default_pid_D,
         double default_pid_IZone,
         double default_pid_FF,
         double default_pid_minOutput,
         double default_pid_maxOutput) {
      super(name);
      logger.info("constructing for name={}", name);

      this.default_pid_P = default_pid_P;
      this.default_pid_I = default_pid_I;
      this.default_pid_D = default_pid_D;
      this.default_pid_IZone = default_pid_IZone;
      this.default_pid_FF = default_pid_FF;
      this.default_pid_minOutput = default_pid_minOutput;
      this.default_pid_maxOutput = default_pid_maxOutput;

      logger.info("constructed");
   }

   // FIXME: Make preferences & NetworkTables right
   public void initialize() {
      logger.info("initializing");

      checkAndAddDoublePreference(PID_P, default_pid_P);
      checkAndAddDoublePreference(PID_I, default_pid_I);
      checkAndAddDoublePreference(PID_D, default_pid_D);
      checkAndAddDoublePreference(PID_IZone, default_pid_IZone);
      checkAndAddDoublePreference(PID_FF, default_pid_FF);
      checkAndAddDoublePreference(PID_minOutput, default_pid_minOutput);
      checkAndAddDoublePreference(PID_maxOutput, default_pid_maxOutput);
   }

}
