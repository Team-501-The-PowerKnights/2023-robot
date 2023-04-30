/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.commands.wrist;

import org.slf4j.Logger;

import frc.robot.commands.PKCommandBase;
import frc.robot.subsystems.wrist.WristFactory;
import frc.robot.subsystems.wrist.IWristSubsystem;

import riolog.PKLogger;

public class WristCommandBase extends PKCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(WristCommandBase.class.getName());

   // Handle to our subsystem
   protected final IWristSubsystem subsys;

   public WristCommandBase() {
      logger.info("constructing {}", getName());

      subsys = WristFactory.getInstance();
      addRequirements(subsys);

      logger.info("constructed");
   }

}
