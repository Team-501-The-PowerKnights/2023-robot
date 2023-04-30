/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.pneumatic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.Solenoid;

import riolog.PKLogger;

class PCMPneumaticModule extends BasePneumaticModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(PCMPneumaticModule.class.getName());

   /** My module */
   private final PneumaticsControlModule module;

   // Indexed 0 - 15
   private final List<Solenoid> solenoids;

   public PCMPneumaticModule() {
      logger.info("constructing");

      module = new PneumaticsControlModule(0);

      enable();

      solenoids = new ArrayList<Solenoid>(16);
      for (int i = 0; i < 16; i++) {
         solenoids.add(null);
      }

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
   public void setSolenoid(int channel, boolean on) {
      if (solenoids.get(channel) == null) {
         logger.info("Creating solenoid for channel = {} with initial state = {}",
               channel, on);
         solenoids.set(channel, module.makeSolenoid(channel));
      }
      solenoids.get(channel).set(on);
   }

}
