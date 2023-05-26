/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.led.LEDModuleFactory;
import frc.robot.modules.pneumatic.PneumaticModuleFactory;
import frc.robot.modules.power.PowerModuleFactory;
import frc.robot.telemetry.TelemetryManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
public class ModulesFactory {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(ModulesFactory.class.getName());

   public static List<IModule> constructModules() {

      ArrayList<IModule> modules = new ArrayList<IModule>();

      TelemetryManager tlmMgr = TelemetryManager.getInstance();

      logger.info("constructing modules ...");

      logger.info("construct Power");
      SmartDashboard.putNumber(TelemetryNames.Power.status, PKStatus.unknown.tlmValue);
      {
         PowerModuleFactory.constructInstance();
         IModule m = PowerModuleFactory.getInstance();
         tlmMgr.addProvider(m);
         modules.add(m);
      }

      logger.info("construct Pneumatic");
      SmartDashboard.putNumber(TelemetryNames.Pneumatic.status, PKStatus.unknown.tlmValue);
      {
         PneumaticModuleFactory.constructInstance();
         IModule m = PneumaticModuleFactory.getInstance();
         tlmMgr.addProvider(m);
         modules.add(m);
      }

      logger.info("construct LED");
      SmartDashboard.putNumber(TelemetryNames.LED.status, PKStatus.unknown.tlmValue);
      {
         LEDModuleFactory.constructInstance();
         IModule m = LEDModuleFactory.getInstance();
         tlmMgr.addProvider(m);
         modules.add(m);
      }

      logger.info("constructed");
      return modules;
   }

}
