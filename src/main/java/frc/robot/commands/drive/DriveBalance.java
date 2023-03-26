/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved. */
/* Open Source Software - may be modified and shared by other FRC teams */
/* under the terms of the Team501 license. The code must be accompanied */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project. */
/*-----------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.gyro.GyroFactory;
import frc.robot.sensors.gyro.IGyroSensor;
import frc.robot.telemetry.TelemetryNames;
import riolog.PKLogger;
import riolog.RioLogger;

public class DriveBalance extends DriveCommandBase {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(DriveBalance.class.getName());

   // Max speed to drive the motors out; no matter how far out.
   private static final double maxSpeed = 0.3;

   //
   private final IGyroSensor gyro;

   /**
    * Creates an instance of a class to try and stay balanced (assuming
    * it starts "close enough").
    */
   public DriveBalance() {
      logger.info("constructing {}", getName());

      gyro = GyroFactory.getInstance();

      logger.info("constructed");
   }

   @Override
   public void initialize() {
      super.initialize();
   }

   @Override
   public void execute() {
      super.execute();

      double offsetAngle = gyro.getPitch();

      // if (Math.abs(offsetAngle) < 1) {
      // drive.drive(0, 0);
      // } else {
      // double offsetSpeed = Math.min(maxSpeed, ((5 - Math.abs(offsetAngle)) * 0.3));
      // double speed = (offsetAngle < 0) ? offsetSpeed : -offsetSpeed;
      // logger.debug("balance: angle={} offsetSpeed={} speed={}");

      // drive.drive(speed, 0);
      // }

      boolean isBalanced;
      double speed;
      if (Math.abs(offsetAngle) < 4) {
         isBalanced = true;
         speed = 0;
      } else {
         isBalanced = false;
         speed = (offsetAngle < 0) ? -0.26 : 0.26;
      }
      logger.debug("balance: angle={} speed={}", offsetAngle, speed);
      SmartDashboard.putBoolean(TelemetryNames.Gyro.balanced, isBalanced);
      drive.drive(speed, 0);
   }

   @Override
   public void end(boolean interrupted) {
      super.end(interrupted);

      drive.stop();
   }

}
