/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.power;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;

/**
 * This class provides an implementation based on the Cross the Road
 * Electronics Power Distribution Panel (PDP).
 */
class PDPPowerModule extends BasePowerModule {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(PDPPowerModule.class.getName());

   protected final PowerDistribution module;

   public PDPPowerModule() {
      logger.info("constructing");

      module = new PowerDistribution(1, ModuleType.kCTRE);

      logger.info("constructed");
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.Power.busVoltage, getBusVoltage());
      SmartDashboard.putNumber(TelemetryNames.Power.totalCurrent, getTotalEnergy());
      SmartDashboard.putNumber(TelemetryNames.Power.totalEnergy, getTotalEnergy());
   }

   @Override
   public void updatePreferences() {
      // No preferences for this module
   }

   @Override
   public void disable() {
      // Nothing active so nothing to disable
   }

   @Override
   public double getBusVoltage() {
      return module.getVoltage();
   }

   @Override
   public double getTotalCurrent() {
      return module.getTotalCurrent();
   }

   @Override
   public double getTotalEnergy() {
      return module.getTotalEnergy();
   }

   @Override
   public double getCurrent(int deviceID) {
      return module.getCurrent(deviceID);
   }

}
