/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.power;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

public class ProtoPowerModule extends PDHPowerModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ProtoPowerModule.class.getName());

   public ProtoPowerModule() {
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.Power.armExtenderCurrent, getCurrent(11));

      super.updateTelemetry();
   }

}
