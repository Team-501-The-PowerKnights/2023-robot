/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj.Preferences;

import frc.robot.preferences.BasePreferences;
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
public final class ArmPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ArmPreferences.class.getName());

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

      if (!Preferences.containsKey(rotatePID_P)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_P);
         Preferences.setDouble(rotatePID_P, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_I)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_I);
         Preferences.setDouble(rotatePID_I, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_D)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_D);
         Preferences.setDouble(rotatePID_D, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_IZone)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_IZone);
         Preferences.setDouble(rotatePID_IZone, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_FF)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_FF);
         Preferences.setDouble(rotatePID_FF, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_minOutput)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_minOutput);
         Preferences.setDouble(rotatePID_minOutput, 0.0);
      }
      if (!Preferences.containsKey(rotatePID_maxOutput)) {
         logger.warn("{} doesn't exist; creating with default", rotatePID_maxOutput);
         Preferences.setDouble(rotatePID_maxOutput, 0.0);
      }

      if (!Preferences.containsKey(rotate_highSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_highSetPoint);
         Preferences.setDouble(rotate_highSetPoint, 0.0);
      }
      if (!Preferences.containsKey(rotate_midSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_midSetPoint);
         Preferences.setDouble(rotate_midSetPoint, 0.0);
      }
      if (!Preferences.containsKey(rotate_lowSetPoint)) {
         logger.warn("{} doesn't exist; creating with default", rotate_lowSetPoint);
         Preferences.setDouble(rotate_lowSetPoint, 0.0);
      }

      if (!Preferences.containsKey(extendPID_P)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_P);
         Preferences.setDouble(extendPID_P, 0.0);
      }
      if (!Preferences.containsKey(extendPID_I)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_I);
         Preferences.setDouble(extendPID_I, 0.0);
      }
      if (!Preferences.containsKey(extendPID_D)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_D);
         Preferences.setDouble(extendPID_D, 0.0);
      }
      if (!Preferences.containsKey(extendPID_IZone)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_IZone);
         Preferences.setDouble(extendPID_IZone, 0.0);
      }
      if (!Preferences.containsKey(extendPID_FF)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_FF);
         Preferences.setDouble(extendPID_FF, 0.0);
      }
      if (!Preferences.containsKey(extendPID_minOutput)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_minOutput);
         Preferences.setDouble(extendPID_minOutput, 0.0);
      }
      if (!Preferences.containsKey(extendPID_maxOutput)) {
         logger.warn("{} doesn't exist; creating with default", extendPID_maxOutput);
         Preferences.setDouble(extendPID_maxOutput, 0.0);
      }

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
