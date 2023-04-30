/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

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
public final class ArmPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ArmPreferences.class.getName());

   private ArmPreferences() {
      super(SubsystemNames.armName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static ArmPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final ArmPreferences INSTANCE = new ArmPreferences();
   }

   static private final String rotateKey = ".Rotate";
   final String rotatePID_P = name + rotateKey + PIDValues.pid_P;
   final String rotatePID_I = name + rotateKey + PIDValues.pid_I;
   final String rotatePID_D = name + rotateKey + PIDValues.pid_D;
   final String rotatePID_IZone = name + rotateKey + PIDValues.pid_IZone;
   final String rotatePID_FF = name + rotateKey + PIDValues.pid_FF;
   final String rotatePID_minOutput = name + rotateKey + PIDValues.pid_minOutput;
   final String rotatePID_maxOutput = name + rotateKey + PIDValues.pid_maxOutput;
   final String rotate_highSetPoint = name + rotateKey + ".HighSetPoint";
   final String rotate_midSetPoint = name + rotateKey + ".MidSetPoint";
   final String rotate_lowSetPoint = name + rotateKey + ".LowSetPoint";

   static final private String extendKey = ".Extend";
   final String extendPID_P = name + extendKey + PIDValues.pid_P;
   final String extendPID_I = name + extendKey + PIDValues.pid_I;
   final String extendPID_D = name + extendKey + PIDValues.pid_D;
   final String extendPID_IZone = name + extendKey + PIDValues.pid_IZone;
   final String extendPID_FF = name + extendKey + PIDValues.pid_FF;
   final String extendPID_minOutput = name + extendKey + PIDValues.pid_minOutput;
   final String extendPID_maxOutput = name + extendKey + PIDValues.pid_maxOutput;

   // FIXME: Make perferences & NetworkTables right
   public void initialize() {
      logger.info("initializing");

      checkAndAddDoublePreference(rotatePID_P, 0.0);
      checkAndAddDoublePreference(rotatePID_I, 0.0);
      checkAndAddDoublePreference(rotatePID_D, 0.0);
      checkAndAddDoublePreference(rotatePID_IZone, 0.0);
      checkAndAddDoublePreference(rotatePID_FF, 0.0);
      checkAndAddDoublePreference(rotatePID_minOutput, 0.0);
      checkAndAddDoublePreference(rotatePID_maxOutput, 0.0);

      checkAndAddDoublePreference(rotate_highSetPoint, 0.0);
      checkAndAddDoublePreference(rotate_midSetPoint, 0.0);
      checkAndAddDoublePreference(rotate_lowSetPoint, 0.0);

      checkAndAddDoublePreference(extendPID_P, 0.0);
      checkAndAddDoublePreference(extendPID_I, 0.0);
      checkAndAddDoublePreference(extendPID_D, 0.0);
      checkAndAddDoublePreference(extendPID_IZone, 0.0);
      checkAndAddDoublePreference(extendPID_FF, 0.0);
      checkAndAddDoublePreference(extendPID_minOutput, 0.0);
      checkAndAddDoublePreference(extendPID_maxOutput, 0.0);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
