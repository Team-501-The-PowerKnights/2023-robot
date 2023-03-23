/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.BaseModule;
import frc.robot.modules.ModuleNames;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseLEDModule extends BaseModule implements ILEDModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseLEDModule.class.getName());

   BaseLEDModule() {
      super(ModuleNames.ledName);
      logger.info("constructing");

      logger.info("constructed");
   }

   // Module state
   protected boolean tlmEnabled = false;

   protected void setTlmEnabled(boolean enabled) {
      tlmEnabled = enabled;
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putBoolean(TelemetryNames.LED.enabled, tlmEnabled);
   }

}
