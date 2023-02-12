/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.pneumatic;

import edu.wpi.first.wpilibj.PneumaticsControlModule;

import riolog.PKLogger;
import riolog.RioLogger;

class PCMPneumaticModule extends BasePneumaticModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(PCMPneumaticModule.class.getName());

   /** My module */
   private final PneumaticsControlModule module;

   public PCMPneumaticModule() {
      logger.info("constructing");

      module = new PneumaticsControlModule(0);
      enable();

      logger.info("constructed");
   }

   @Override
   public void updateTelemetry() {
      setTlmPressureGood(module.getPressureSwitch());
      super.updateTelemetry();
   }

   @Override
   public void updatePreferences() {
      // Nothing here
   }

   @Override
   public void disable() {
      module.disableCompressor();
      setTlmEnabled(false);
   }

   @Override
   public void enable() {
      module.enableCompressorDigital();
      setTlmEnabled(true);
   }

   @Override
   public void setSolenoid(int index, boolean value) {
      // TODO Auto-generated method stub
   }

}
