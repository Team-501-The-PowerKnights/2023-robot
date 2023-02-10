/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved. */
/* Open Source Software - may be modified and shared by other FRC teams */
/* under the terms of the Team501 license. The code must be accompanied */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project. */
/*-----------------------------------------------------------------------*/

package frc.robot.modules.pneumatic;

import riolog.PKLogger;
import riolog.RioLogger;

class StubPneumaticModule extends BasePneumaticModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(StubPneumaticModule.class.getName());

   public StubPneumaticModule() {
      logger.info("constructing");

      logger.info("constructed");
   }

   @Override
   public void updateTelemetry() {
      super.updateTelemetry();

      // Stub doesn't implement this
   }

   @Override
   public void updatePreferences() {
      // Stub doesn't implement this
   }

   @Override
   public void enable() {
      // Stub doesn't implement this
   }

   @Override
   public void disable() {
      // Stub doesn't implement this
   }

}
