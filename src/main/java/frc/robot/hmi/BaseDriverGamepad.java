/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import riolog.PKLogger;

/**
 * This class implements the Driver's gamepad.
 * <p>
 * See <code>control_mode.md</code> for documentation of how configured and
 * used.
 */
abstract class BaseDriverGamepad extends F310Gamepad implements IDriverGamepad {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BaseDriverGamepad.class.getName());

   // Chooser for speed sense from Dashboard
   protected SendableChooser<Boolean> speedSenseChooser;

   // Chooser for turn sense from Dashboard
   protected SendableChooser<Boolean> turnSenseChooser;

   private void createControlChoosers() {
      logger.info("creating");

      speedSenseChooser = new SendableChooser<>();

      speedSenseChooser.setDefaultOption("F-F, R-R (Default)", Boolean.TRUE);
      speedSenseChooser.addOption("F-R, R-F (Sophia)", Boolean.FALSE);

      SmartDashboard.putData("Speed Sense", speedSenseChooser);

      turnSenseChooser = new SendableChooser<>();

      turnSenseChooser.setDefaultOption("L-L, R-R (Default)", Boolean.TRUE);
      turnSenseChooser.addOption("L-R, R-L (Sophia)", Boolean.FALSE);

      SmartDashboard.putData("Turn Sense", turnSenseChooser);

      logger.info("created");
   }

   public BaseDriverGamepad(String name, int port) {
      super(name, port);
      logger.info("constructing");

      createControlChoosers();

      logger.info("constructed");
   }

   @Override
   public void configureButtonBindings() {
      logger.error("no one should be calling this at the moment");
   }

}
