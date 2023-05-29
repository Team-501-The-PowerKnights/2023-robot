/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.slf4j.Logger;

import frc.robot.properties.PropertiesManager;
import riolog.PKLogger;

public final class SubsystemsConfig {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SubsystemsConfig.class.getName());

   // Always have Drive
   private static boolean hasDrive = false;

   // Common Subsystems
   private static boolean hasGripper = false;

   // Proto-Bot Unique
   private static boolean hasArmRotator = false;
   private static boolean hasArmExtener = false;
   private static boolean hasWrist = false;
   private static boolean hasIngester = false;

   // Real-Bot Unique
   private static boolean hasTurret = false;
   private static boolean hasLift = false;
   private static boolean hasArm = false;

   static {
      switch (PropertiesManager.getInstance().getRobotName()) {

         case "Suitcase-Bot":

            hasDrive = true;

            hasGripper = true;

            hasArmRotator = true;
            hasArmExtener = true;
            hasWrist = true;
            hasIngester = true;

            hasTurret = true;
            hasLift = true;
            hasArm = true;

            break;

         case "Swprog-Bot":

            hasDrive = true;

            break;

         case "Proto-Bot":

            hasDrive = true;

            hasGripper = true;

            hasArmRotator = true;
            hasArmExtener = true;
            hasWrist = true;
            hasIngester = true;

            break;

         case "Real-Bot":

            hasDrive = true;

            hasGripper = true;

            hasTurret = true;
            hasLift = true;
            hasArm = true;

            break;

         default:
            break;
      }
   }

   // Prevent instantiation
   private SubsystemsConfig() {
      logger.info("constructing");
      logger.info("constructed");
   }

   public static boolean hasDrive() {
      return hasDrive;
   }

   public static boolean hasGripper() {
      return hasGripper;
   }

   public static boolean hasArmRotator() {
      return hasArmRotator;
   }

   public static boolean hasArmExtener() {
      return hasArmExtener;
   }

   public static boolean hasWrist() {
      return hasWrist;
   }

   public static boolean hasIngester() {
      return hasIngester;
   }

   public static boolean hasTurret() {
      return hasTurret;
   }

   public static boolean hasLift() {
      return hasLift;
   }

   public static boolean hasArm() {
      return hasArm;
   };

}
