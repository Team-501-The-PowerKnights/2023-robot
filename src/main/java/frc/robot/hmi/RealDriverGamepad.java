/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.drive.DriveToggleBrake;
import frc.robot.commands.turret.TurretRotateToFrontPosition;
import frc.robot.commands.turret.TurretRotateToLeftPosition;
import frc.robot.commands.turret.TurretRotateToRearPosition;
import frc.robot.commands.turret.TurretRotateToRightPosition;
import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;

/**
 * This class implements the Driver's gamepad forthe Real-Bot.
 * <p>
 * See <code>control_mode.md</code> for documentation of how configured and
 * used.
 */
public class RealDriverGamepad extends BaseDriverGamepad {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(DriverGamepad.class.getName());

   // private final Button turboButton;
   private final Trigger crawlButton;
   // private final Button driveSwapButton;

   private final Trigger brakeToggleButton;

   private final Trigger turretLeftButton;
   private final Trigger turretFrontButton;
   private final Trigger turretRightButton;
   private final Trigger turretRearButton;

   public RealDriverGamepad() {
      super("RealDriverGamepad", 0);
      logger.info("constructing");

      // turboButton = new JoystickButton(stick, rightBumper);
      crawlButton = cmdStick.button(leftBumper);
      // driveSwapButton = new JoystickButton(stick, backButton);

      brakeToggleButton = cmdStick.button(startButton);

      turretLeftButton = cmdStick.povLeft();
      turretFrontButton = cmdStick.povUp();
      turretRightButton = cmdStick.povRight();
      turretRearButton = cmdStick.povDown();

      logger.info("constructed");
   }

   @Override
   public void updateTelemetry() {
      SmartDashboard.putNumber(TelemetryNames.HMI.rawSpeed, getRawDriveSpeed());
      SmartDashboard.putNumber(TelemetryNames.HMI.rawTurn, getRawDriveTurn());
      // SmartDashboard.putBoolean(TelemetryNames.HMI.turbo, turboButton.get());
      SmartDashboard.putBoolean(TelemetryNames.HMI.crawl, crawlButton.getAsBoolean());
      SmartDashboard.putNumber(TelemetryNames.HMI.oiSpeed, getDriveSpeed());
      SmartDashboard.putNumber(TelemetryNames.HMI.oiTurn, getDriveTurn());
   }

   @Override
   public void autonomousInit() {
      logger.info("initializing auto for {}", this.getClass().getSimpleName());

      // no button or other trigger for autonomous

      logger.info("initialized auto for {}", myName);
   }

   @Override
   public void teleopInit() {
      logger.info("initializing teleop for {}", myName);

      configureTeleopButtonBindings();

      logger.info("initialized teleop for {}", myName);
   }

   private void configureTeleopButtonBindings() {
      logger.info("configure");

      // turboButton - implemented in getting values speed & turn
      // crawlButton - implemented in getting values speed & turn
      // driveSwapButton.whenPressed(new DriveSwap());

      brakeToggleButton.onTrue(new DriveToggleBrake());

      turretLeftButton.onTrue(new TurretRotateToLeftPosition());
      turretFrontButton.onTrue(new TurretRotateToFrontPosition());
      turretRightButton.onTrue(new TurretRotateToRightPosition());
      turretRearButton.onTrue(new TurretRotateToRearPosition());

      // Hook to configure for testing of new stuff
      configureTestBindings();

      logger.info("configured");
   }

   /*********************
    * Drive
    *********************/

   @Override
   public double getDriveSpeed() {
      double hmiSpeed = getRawDriveSpeed();
      double calcSpeed = hmiSpeed;
      if (crawlButton.getAsBoolean()) {
         calcSpeed *= 0.60;
      }
      // if (turboButton.get()) {
      // calcSpeed = hmiSpeed;
      // // } else if (crawlButton.get()) {
      // // calcSpeed = hmiSpeed * 0.30;
      // } else {
      // calcSpeed = hmiSpeed *= 0.50;
      // }
      return calcSpeed;
   }

   private double getRawDriveSpeed() {
      return deadBand(getLeftYAxis(), 0.05);
   }

   @Override
   public double getDriveTurn() {
      // Need to reverse the sign of turn
      final double hmiTurn = -getRawDriveTurn();
      double calcTurn = hmiTurn;
      if (crawlButton.getAsBoolean()) {
         calcTurn *= 0.75;
      }
      // if (turboButton.get()) {
      // calcTurn = hmiTurn * 0.60;
      // // } else if (crawlButton.get()) {
      // // calcTurn = hmiTurn * 0.25;
      // } else {
      // calcTurn = hmiTurn * 0.30;
      // }
      return calcTurn;
   }

   private double getRawDriveTurn() {
      return deadBand(getRightXAxis(), 0.05);
   }

   /*********************
    * Test Stuff
    *********************/

   // TESTCODE: Button binding for commands here
   private void configureTestBindings() {
      // *** Timer Test
      // Button testButton = new JoystickButton(stick, 3);
      // testButton.whenPressed(new frc.robot.commands.test.TimerTestCommand());
   }

}
