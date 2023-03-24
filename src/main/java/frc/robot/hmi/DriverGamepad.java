/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.PKCommandBase;
import frc.robot.commands.drive.DriveToggleBrake;
import frc.robot.modules.led.LEDModuleFactory;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKColor8Bit;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * This class implements the Driver's gamepad.
 * <p>
 * See <code>control_mode.md</code> for documentation of how configured and
 * used.
 */
public class DriverGamepad extends F310Gamepad {

	/** Our classes' logger **/
	private static final PKLogger logger = RioLogger.getLogger(DriverGamepad.class.getName());

	// private final Button turboButton;
	private final Trigger crawlButton;
	// private final Button driveSwapButton;

	private final Trigger brakeToggleButton;

	private final Trigger gamePieceButton;

	//
	private static final PKColor8Bit yellow = new PKColor8Bit("yellow", 255, 255, 0);
	private static final PKColor8Bit purple = new PKColor8Bit("purple", 160, 32, 240);
	// State of last color selected
	private PKColor8Bit lastGamePieceColor;

	public DriverGamepad() {
		super("DriverGamepad", 0);
		logger.info("constructing");

		// turboButton = new JoystickButton(stick, rightBumper);
		crawlButton = cmdStick.button(leftBumper);
		// driveSwapButton = new JoystickButton(stick, backButton);

		brakeToggleButton = cmdStick.button(startButton);

		gamePieceButton = cmdStick.button(yellowButton);

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

		lastGamePieceColor = purple;
		LEDModuleFactory.getInstance().setColor(lastGamePieceColor);

		logger.info("initialized teleop for {}", myName);
	}

	// FIXME: Make this on-the-fly command?
	// FIXME: Make into one-shot command?
	private class ToggleGamePieceColor extends PKCommandBase {

		/** Our classes' logger **/
		// private static final PKLogger logger =
		// RioLogger.getLogger(ToggleGamePieceColor.class.getName());

		public ToggleGamePieceColor() {
			logger.info("constructing {}", getName());
		}

		@Override
		public void execute() {
			super.execute();
		}

		@Override
		protected void firstExecution() {
			logger.trace("ledModule.setColor() called in firstExecution()");

			lastGamePieceColor = (lastGamePieceColor.equals(yellow)) ? purple : yellow;
			LEDModuleFactory.getInstance().setColor(lastGamePieceColor);
		}

		@Override
		public boolean isFinished() {
			return true;
		}

	}

	private void configureTeleopButtonBindings() {
		logger.info("configure");

		// turboButton - implemented in getting values speed & turn
		// crawlButton - implemented in getting values speed & turn
		// driveSwapButton.whenPressed(new DriveSwap());

		brakeToggleButton.onTrue(new DriveToggleBrake());

		gamePieceButton.onTrue(new ToggleGamePieceColor());

		// Hook to configure for testing of new stuff
		configureTestBindings();

		logger.info("configured");
	}

	/*********************
	 * Drive
	 *********************/

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
