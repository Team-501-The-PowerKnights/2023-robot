/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.power;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * This class provides an implementation based on the REV Robotics
 * Power Distribution Hub (PDH).
 */
class PDHPowerModule extends BasePowerModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(PDHPowerModule.class.getName());

   private final PowerDistribution pdh;

   public PDHPowerModule() {
      logger.info("constructing");

      pdh = new PowerDistribution(1, ModuleType.kRev);

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
      return pdh.getVoltage();
   }

   @Override
   public double getTotalCurrent() {
      return pdh.getTotalCurrent();
   }

   @Override
   public double getTotalEnergy() {
      return pdh.getTotalEnergy();
   }

   @Override
   public double getCurrent(int deviceID) {
      return pdh.getCurrent(deviceID);
   }

}

