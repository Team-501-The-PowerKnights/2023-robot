/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.pneumatic;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.BaseModule;
import frc.robot.modules.ModuleNames;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;

abstract class BasePneumaticModule extends BaseModule implements IPneumaticModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BasePneumaticModule.class.getName());

   BasePneumaticModule() {
      super(ModuleNames.pneumaticName);
      logger.info("constructing");

      logger.info("constructed");
   }

   // Module state
   protected boolean tlmEnabled = false;
   // Enough pressure? Compressor should be running if not ...
   protected boolean tlmPressure = false;

   protected void setTlmEnabled(boolean enabled) {
      tlmEnabled = enabled;
   }

   protected void setTlmPressureGood(boolean pressure) {
      tlmPressure = pressure;
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putBoolean(TelemetryNames.Pneumatic.enabled, tlmEnabled);
      SmartDashboard.putBoolean(TelemetryNames.Pneumatic.pressureGood, tlmPressure);
   }

}
