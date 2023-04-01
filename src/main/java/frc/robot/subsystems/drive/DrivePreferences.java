/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.drive;

import frc.robot.preferences.BasePreferences;
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
public final class DrivePreferences extends BasePreferences {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(DrivePreferences.class.getName());

   private DrivePreferences() {
      super(SubsystemNames.driveName);
      logger.info("constructing");

      logger.info("constructed");
   }

   public static DrivePreferences getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final DrivePreferences INSTANCE = new DrivePreferences();
   }

   // FIXME: Make perferences & NetworkTables right
   public void initialize() {
      logger.info("initializing");

      logger.info("preferences as initialized:");
      logPreferences(logger);

      logger.info("initialized");
   }

}
