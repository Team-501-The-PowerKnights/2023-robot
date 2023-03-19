/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.gripper;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.subsystems.SubsystemNames;

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
public final class GripperPreferences {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(GripperPreferences.class.getName());

   static private final String name = SubsystemNames.gripperName;

   /** Max Speeds */
   static final String maxInSpeed = name + ".MaxInSpeed";
   static final String maxOutSpeed = name + ".MaxOutSpeed";

   private static final double default_maxInSpeed = 1;
   private static final double default_maxOutSpeed = 1;

   /** Idle Speed */
   static final String idleSpeed = name + ".IdleSpeed";

   private static final double default_idleSpeed = 0;

   private GripperPreferences() {
   }

   // FIXME: Make perferences & NetworkTables right
   public static void initialize() {
      if (!Preferences.containsKey(maxInSpeed)) {
         logger.warn("{} doesn't exist; creating with default", maxInSpeed);
         Preferences.setDouble(maxInSpeed, default_maxInSpeed);
      }
      if (!Preferences.containsKey(maxOutSpeed)) {
         logger.warn("{} doesn't exist; creating with default", maxOutSpeed);
         Preferences.setDouble(maxOutSpeed, default_maxOutSpeed);
      }

      if (!Preferences.containsKey(idleSpeed)) {
         logger.warn("{} doesn't exist; creating with default", idleSpeed);
         Preferences.setDouble(idleSpeed, default_idleSpeed);
      }
   }

}
