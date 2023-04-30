/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.preferences;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.properties.PropertiesManager;
import frc.robot.subsystems.arm.ArmPreferences;
import frc.robot.subsystems.armextender.ArmExtenderPreferences;
import frc.robot.subsystems.armrotator.ArmRotatorPreferences;
import frc.robot.subsystems.drive.DrivePreferences;
import frc.robot.subsystems.gripper.GripperPreferences;
import frc.robot.subsystems.wrist.WristPreferences;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
public final class PreferencesManager {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(PreferencesManager.class.getName());

   private static PreferencesManager ourInstance;

   private static String myName = "Prefs";

   public static void constructInstance() {
      SmartDashboard.putNumber(TelemetryNames.Properties.status, PKStatus.unknown.tlmValue);

      if (ourInstance != null) {
         throw new IllegalStateException(myName + " already constructed");
      }

      SmartDashboard.putNumber(TelemetryNames.Preferences.status, PKStatus.inProgress.tlmValue);

      ourInstance = new PreferencesManager();

      SmartDashboard.putNumber(TelemetryNames.Preferences.status, PKStatus.success.tlmValue);

      logger.info("initialized");
   }

   public static PreferencesManager getInstance() {
      if (ourInstance == null) {
         throw new IllegalStateException(myName + " not constructed yet");
      }
      return ourInstance;
   }

   private PreferencesManager() {
      logger.info("constructing");

      DrivePreferences.getInstance().initialize();

      switch (PropertiesManager.getInstance().getRobotName()) {
         case "Suitcase-Bot":
         case "Swprog-Bot":
         case "Proto-Bot":

            // ** ArmRotator **
            ArmRotatorPreferences.getInstance().initialize();
            // ** Arm Extender **
            ArmExtenderPreferences.getInstance().initialize();
            break;

         case "Real-Bot":

            // ** Arm **
            ArmPreferences.getInstance().initialize();
            break;

         default:
            break;
      }

      GripperPreferences.getInstance().initialize();

      WristPreferences.getInstance().initialize();

      logger.info("constructed");
   }

   public void logPreferences(Logger logger) {
      StringBuilder buf = new StringBuilder();
      buf.append(" preferences:");
      for (String key : Preferences.getKeys().stream().sorted().collect(Collectors.toCollection(ArrayList::new))) {
         buf.append("\n..."); // logger gobbles up leading spaces
         buf.append(key).append(" = ").append(Preferences.getDouble(key, 3171960.));
      }
      logger.info(buf.toString());
   }

}