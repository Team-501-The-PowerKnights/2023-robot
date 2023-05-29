/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.ingester;

import org.slf4j.Logger;

import frc.robot.preferences.BasePreferences;
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
public final class IngesterPreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(IngesterPreferences.class.getName());

   private IngesterPreferences() {
      super(SubsystemNames.ingesterName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static IngesterPreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final IngesterPreferences INSTANCE = new IngesterPreferences();
   }

   /** Max Speeds */
   final String maxInSpeed = name + ".MaxInSpeed";
   final String maxOutSpeed = name + ".MaxOutSpeed";

   private static final double default_maxInSpeed = 1;
   private static final double default_maxOutSpeed = 1;

   /** Idle Speed */
   final String idleSpeed = name + ".IdleSpeed";

   private static final double default_idleSpeed = 0.2;

   // FIXME: Make perferences & NetworkTables right
   @Override
   public void initialize() {
      logger.info("initializing");

      checkAndAddDoublePreference(maxInSpeed, default_maxInSpeed);
      checkAndAddDoublePreference(maxOutSpeed, default_maxOutSpeed);

      checkAndAddDoublePreference(idleSpeed, default_idleSpeed);

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
