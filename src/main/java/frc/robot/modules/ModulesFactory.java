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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.led.LEDModuleFactory;
import frc.robot.modules.pneumatic.PneumaticModuleFactory;
import frc.robot.modules.power.PowerModuleFactory;
import frc.robot.telemetry.TelemetryManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * Add your docs here.
 */
public class ModulesFactory {

   /** Our classes' logger **/
   @SuppressWarnings("unused")
   private static final PKLogger logger = RioLogger.getLogger(ModulesFactory.class.getName());

   public static List<IModule> constructModules() {

      ArrayList<IModule> modules = new ArrayList<IModule>();

      TelemetryManager tlmMgr = TelemetryManager.getInstance();

      SmartDashboard.putNumber(TelemetryNames.Power.status, PKStatus.unknown.tlmValue);
      {
         PowerModuleFactory.constructInstance();
         IModule m = PowerModuleFactory.getInstance();
         tlmMgr.addProvider(m);
         modules.add(m);
      }

      SmartDashboard.putNumber(TelemetryNames.Pneumatic.status, PKStatus.unknown.tlmValue);
      {
         PneumaticModuleFactory.constructInstance();
         IModule m = PneumaticModuleFactory.getInstance();
         tlmMgr.addProvider(m);
         modules.add(m);
      }

      SmartDashboard.putNumber(TelemetryNames.LED.status, PKStatus.unknown.tlmValue);
      {
         LEDModuleFactory.constructInstance();
         IModule m = LEDModuleFactory.getInstance();
         tlmMgr.addProvider(m);
         modules.add(m);
      }

      return modules;
   }

}
