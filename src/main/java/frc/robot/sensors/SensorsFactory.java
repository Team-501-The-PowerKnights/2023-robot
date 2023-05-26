/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.sensors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.sensors.gyro.GyroFactory;
import frc.robot.telemetry.TelemetryManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;

/**
 * Add your docs here.
 */
public class SensorsFactory {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(SensorsFactory.class.getName());

   public static List<ISensor> constructSensors() {
      logger.info("constructing");

      ArrayList<ISensor> sensors = new ArrayList<ISensor>();

      TelemetryManager tlmMgr = TelemetryManager.getInstance();

      logger.info("constructing sensors ...");

      logger.info("construct Gyro");
      SmartDashboard.putNumber(TelemetryNames.Gyro.status, PKStatus.unknown.tlmValue);
      {
         GyroFactory.constructInstance();
         ISensor s = GyroFactory.getInstance();
         tlmMgr.addProvider(s);
         sensors.add(s);
      }

      logger.info("constructed");
      return sensors;
   }

}
