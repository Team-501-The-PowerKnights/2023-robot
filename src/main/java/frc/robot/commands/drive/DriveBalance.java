/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved. */
/* Open Source Software - may be modified and shared by other FRC teams */
/* under the terms of the Team501 license. The code must be accompanied */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project. */
/*-----------------------------------------------------------------------*/

package frc.robot.commands.drive;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.led.ILEDModule;
import frc.robot.modules.led.LEDModuleFactory;
import frc.robot.sensors.gyro.GyroFactory;
import frc.robot.sensors.gyro.IGyroSensor;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKColor8Bit;

import riolog.PKLogger;

public class DriveBalance extends DriveCommandBase {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(DriveBalance.class.getName());

   // Max speed to drive the motors out; no matter how far out.
   // private static final double maxSpeed = 0.3;

   //
   private final IGyroSensor gyro;
   //
   private final ILEDModule leds;

   /**
    * Creates an instance of a class to try and stay balanced (assuming
    * it starts "close enough").
    */
   public DriveBalance() {
      logger.info("constructing {}", getName());

      gyro = GyroFactory.getInstance();
      leds = LEDModuleFactory.getInstance();

      logger.info("constructed");
   }

   @Override
   public void initialize() {
      super.initialize();

      LEDModuleFactory.getInstance().setColor(PKColor8Bit.blackRGB);
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
         setBalanced();
         speed = 0;
      } else {
         isBalanced = false;
         setUnbalanced();
         speed = (offsetAngle < 0) ? -0.28 : 0.28;
      }
      logger.debug("balance: angle={} speed={}", offsetAngle, speed);
      SmartDashboard.putBoolean(TelemetryNames.Gyro.balanced, isBalanced);
      drive.drive(speed, 0);
   }

   @Override
   public void end(boolean interrupted) {
      super.end(interrupted);

      leds.setColor(PKColor8Bit.greenRGB);

      drive.stop();
   }

   private void setUnbalanced() {
      leds.setColor(PKColor8Bit.blackRGB);
   }

   private void setBalanced() {
      leds.setColor(PKColor8Bit.blueRGB);
   }

}
